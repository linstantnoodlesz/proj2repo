import static org.junit.Assert.*;

import org.junit.Test;

/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }

    @Test
    public void testIsSameNumber() {
        int x = 4;
        int y = 5;
        assertEquals(isSameNumber(x,y), false);
        x = 5;
        assertEquals(isSameNumber(x,y), true);
    }
}
