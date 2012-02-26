import org.junit.Assert;
import org.junit.Test;

public class StringCharTest {

    // Times:
    // 1. Initialization of "s" outside the loop
    // 2. Init of "s" inside the loop
    // 3. newFunction() actually checks the string length,
    // so the function will not be optimized away by the hotstop compiler

    @Test
    // Fastest: 237ms / 562ms / 2434ms
    public void testCacheStrings() throws Exception {
        // Cache all possible Char strings
        String[] char2string = new String[Character.MAX_VALUE];
        for (char i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            char2string[i] = Character.toString(i);
        }

        for (int x = 0; x < 10000000; x++) {
            char[] s = "abcdefg".toCharArray();
            for (int i = 0; i < s.length; i++) {
                newFunction(char2string[s[i]]);
            }
        }
    }

    @Test
    // Fast: 1687ms / 1725ms / 3382ms
    public void testCharToString() throws Exception {
        for (int x = 0; x < 10000000; x++) {
            String s = "abcdefg";
            for (int i = 0; i < s.length(); i++) {
                // Fast: Creates new String objects, but does not copy an array
                newFunction(Character.toString(s.charAt(i)));
            }
        }
    }

    @Test
    // Very fast: 1331 ms/ 1414ms / 3190ms
    public void testSubstring() throws Exception {
        for (int x = 0; x < 10000000; x++) {
            String s = "abcdefg";
            for (int i = 0; i < s.length(); i++) {
                // The fastest! Reuses the internal char array
                newFunction(s.substring(i, i + 1));
            }
        }
    }

    @Test
    // Slowest: 2525ms / 2961ms / 4703ms
    public void testNewString() throws Exception {
        char[] value = new char[1];
        for (int x = 0; x < 10000000; x++) {
            char[] s = "abcdefg".toCharArray();
            for (int i = 0; i < s.length; i++) {
                value[0] = s[i];
                // Slow! Copies the array
                newFunction(new String(value));
            }
        }
    }

    private void newFunction(String string) {
        // Do something with the one-character string
        Assert.assertEquals(1, string.length());
    }

}