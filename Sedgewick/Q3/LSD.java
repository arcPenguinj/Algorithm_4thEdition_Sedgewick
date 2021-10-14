package Q3;

public class LSD {
    // here we assume the strings to sort are same length
    public static void sort(Alphabet a, String[] s, int w) {
        int n = s.length; // length of the strings
        int R = a.radix() + 1; // length of the radix, +1 because of null char due to invariant string length
        String[] aux = new String[n]; // aux array for temporary storage

        for (int d = w-1; d >= 0; d--) {
            // loop from least significant position to 0
            int[] count = new int[R+1]; // count array
            for (int i = 0; i < n; i++) {
                count[LSD.charAt(a, s[i], d) + 1]++; // compute count
            }
            for (int r = 0; r < R; r++) {
                count[r+1] += count[r]; // convert to aggregates
            }
            for (int i = 0; i < n; i++) {
                aux[count[LSD.charAt(a, s[i], d)]++] = s[i]; // put string to its sorted place according to radix
            }
            for (int i = 0; i < n; i++) {
                s[i] = aux[i]; // copy back to s
            }
        }
    }

    private static int charAt(Alphabet a, String s, int d) {
        // using this helper function to return index of char at d in string s
        // if d is greater than s' length, then return 0
        // this way we can deal with invariant length of strings
        if (d >= s.length()) {
            return 0;
        } else {
            return a.toIndex(s.charAt(d)) + 1; // +1 because 0 for empty
        }
    }
}
