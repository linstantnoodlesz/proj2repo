import synthesizer.GuitarString;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

/**
 * This code does some stuff. Run it (with sound on!) to find out what stuff it does!
 * Requires completion of CS 61B Homework 1.
 *
 * For educational use only.
 *
 * @author Eli Lipsitz
 */
public class TTFAF {
    public static void main(String[] args) {
        try {
        	InputStream source = new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(TTFAF)));
            Sequence sequence = MidiSystem.getSequence(source);
            // Sequence sequence = MidiSystem.getSequence(new File("fun.mid"));
            go(sequence, 200);
        } catch (Exception e) {
            //   ^^^^^^^^^^^  -->  do not do this
            // I'm doing this because I'm lazy
            // you shouldn't do this because it's bad practice
            e.printStackTrace();
        }
    }

    private static void make() {
        strings = new GuitarString[128];
        vol = new double[128];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = new GuitarString(440.0 * Math.pow(2.0, (i - 69.0) / 12.0));
            vol[i] = 0.0;
        }
    }

    private static void tic() {
        for (int i = 0; i < strings.length; i++) {
            if (vol[i] > 0.0) {
                strings[i].tic();
            }
        }
    }

    private static double sample() {
        double sum = 0.0f;
        for (int i = 0; i < strings.length; i++) {
            sum += vol[i] * strings[i].sample();
        }
        return sum;
    }

	private static GuitarString[] strings;
	private static double[] vol;
    private static void go(Sequence sequence, double bpm) {
        System.out.println("starting performance...");

        make();

        double samplesPerTick = StdAudio.SAMPLE_RATE * ((1.0 / sequence.getResolution()) / (bpm / 60.0f));

        Track[] tracks = sequence.getTracks();
        int maxSize = 0;
        int lead = 0;
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i].size() > maxSize) {
                maxSize = tracks[i].size();
                lead = i;
            }
        }

        long tick = 0;
        Track track = tracks[lead];
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage msg = event.getMessage();
            byte[] data = msg.getMessage();

            if (msg instanceof MetaMessage) {
                continue;
            }

            if (event.getTick() > tick) {
                int samplesToSkip = (int) ((event.getTick() - tick) * samplesPerTick);
                for (int j = 0; j < samplesToSkip; j++) {
                    tic();
                    StdAudio.play(sample());
                }
                tick = event.getTick();
            }

            int j = 0;
            while (j < data.length - 2) {
                int s = data[j++] & 0xFF;

                if (s >= 0x80 && s <= 0x8F) {
                    // note off
                    int note = data[j++] & 0xFF;
                    int vel = data[j++] & 0xFF;
                    vol[note] = 0.0;
                } else if (s >= 0x90 && s <= 0x9F) {
                    // note on?
                    int note = data[j++] & 0xFF;
                    int vel = data[j++] & 0xFF;
                    vol[note] = vel / 127.0;
                    strings[note].pluck();
                } else {
                    // status
                    int d = data[j++] & 0xFF;
                    int d2 = data[j++] & 0xFF;
                }
            }
        }

        System.out.println("please clap");
        for (int c = 0; c < 6; c++) {
            try {
                if (c == 0) {
                    Thread.sleep(900);
                }
                Thread.sleep(176 + (int) (250.0 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.print("*clap* ");
        }
    }

	private static final String TTFAF = "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";
}
