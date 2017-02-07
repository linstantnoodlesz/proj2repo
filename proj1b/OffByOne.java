/**
 * Created by Joseph on 2/4/2017.
 *
 * Defines two characters equal if they are off by one
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
