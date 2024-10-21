import java.util.Locale;

public class Class_10 {

    public static final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);

    public static final void main(String[] tryCount_inParamIdx0) {
        int testCount;
        // Get try-count from command-line parameter
        // try {
        testCount = Integer.parseInt(tryCount_inParamIdx0[0]);
        // } catch (ArrayIndexOutOfBoundsException | NumberFormatException x) {
        // throw new IllegalArgumentException(
        // "Missing or invalid command line parameter: The number of testCount for each test. " + x);
        // }
        // Test proper...START
        int[] intArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100 };
        long lStart = System.nanoTime();
        for (int i = 0; i < testCount; i++) {
            testIterator(intArray);
        }
        long lADuration = outputGetNanoDuration("A", lStart);
        lStart = System.nanoTime();
        for (int i = 0; i < testCount; i++) {
            testFor(intArray);
        }
        long lBDuration = outputGetNanoDuration("B", lStart);
        outputGetABTestNanoDifference(lADuration, lBDuration, "A", "B");
    }

    private static final void testIterator(int[] int_array) {
        int total = 0;
        for (int i = 0; i < int_array.length; i++) {
            total += int_array[i];
        }
    }

    private static final void testFor(int[] int_array) {
        int total = 0;
        for (int i : int_array) {
            total += i;
        }
    }

    // Test proper...END
    // Timer testing utilities...START
    public static final long outputGetNanoDuration(String s_testName, long l_nanoStart) {
        long lDuration = System.nanoTime() - l_nanoStart;
        System.out.println("Test " + s_testName + ": " + nf.format(lDuration) + " nanoseconds");
        return lDuration;
    }

    public static final long outputGetABTestNanoDifference(long l_aDuration, long l_bDuration, String s_aTestName, String s_bTestName) {
        long lDiff = -1;
        double dPct = -1.0;
        String sFaster = null;
        if (l_aDuration > l_bDuration) {
            lDiff = l_aDuration - l_bDuration;
            dPct = 100.00 - (l_bDuration * 100.0 / l_aDuration + 0.5);
            sFaster = "B";
        } else {
            lDiff = l_bDuration - l_aDuration;
            dPct = 100.00 - (l_aDuration * 100.0 / l_bDuration + 0.5);
            sFaster = "A";
        }
        System.out.println(sFaster + " faster by " + nf.format(lDiff) + " nanoseconds (" + dPct + "% faster)");
        return lDiff;
    }
    // Timer testing utilities...END
}
