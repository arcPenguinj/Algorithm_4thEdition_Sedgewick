package Q3;

public class MSD {
    private static int CUTOFF = 5; // threshold of when to use insertion sort for smaller sub arrays, not super important for this problem
    private static String[] aux; // temporary storage for string array

    public static void sort(Alphabet a, String[] s) {
        // MSD sorting algorithm
        int n = s.length;
        aux = new String[n]; // initialize aux
        sort(a, s, 0, n-1, 0); // actual sort
    }

    private static int charAt(Alphabet a, String s, int d) {
        // helper function to return char's index at d position of s
        // if d is greater than s' length, then return 0
        if (d >= s.length()) {
            return 0;
        } else {
            return a.toIndex(s.charAt(d)) + 1; // + 1 to avoid 0
        }
    }

    private static void sort(Alphabet a, String[] s, int lo, int hi, int d) {
        // the meat of MSD sort, sorting string array s using its sub-array from lo to hi
        // starting at d-th character

        if (hi <= lo + CUTOFF) {
            // for small sub arrays that's under CUTOFF, use insertion sort to speed up
            insertionSort(a, s, lo, hi, d);
            return;
        }

        int R = a.radix(); // get radix
        int[] count = new int[R+2]; // initialize count array
        for (int i = lo; i <= hi; i++) {
            count[charAt(a, s[i], d) + 1]++; // take count of each character
        }
        for (int r = 0; r < R + 1; r++) {
            count[r+1] += count[r]; // calculate aggregates
        }
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a, s[i], d)]++] = s[i]; // copy string to its correct position in aux according to count[]
        }
        for (int i = lo; i <= hi; i++) {
            s[i] = aux[i - lo]; // copy back to original string []
        }

        for (int r = 0; r < R; r++) {
            sort(a, s, lo + count[r], lo + count[r+1] - 1, d + 1); // recursively sort the subarrays that has same character at position d
        }

    }

    private static void insertionSort(Alphabet a, String[] s, int lo, int hi, int d) {
        // insertion sort when hi-lo <= CUTOFF
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a, s[j], s[j-1], d); j--) {
                // switch the position of s[j] and s[j-1]
                String tmp = s[j];
                s[j] = s[j - 1];
                s[j-1] = tmp;
            }
        }
    }

    private static boolean less(Alphabet a, String s1, String s2, int d) {
        // helper function to compare 2 strings, code from book p715
        for (int i = d; i < Math.min(s1.length(), s2.length()); i++) {
            if (a.toIndex(s1.charAt(i))<a.toIndex(s2.charAt(i))) return true;
            else if (a.toIndex(s1.charAt(i))>a.toIndex(s2.charAt(i))) return false;
        }
        return s1.length() < s2.length();
    }
}
