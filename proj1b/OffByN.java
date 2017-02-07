/**
 * Created by Joseph on 2/4/2017.
 *
 * This character comparator defines two characters to be equal if they are off by N
 */
public class OffByN implements CharacterComparator {

    private int N;

    public OffByN(int n) {
        N = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == N;
    }
}
