package Q3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("===Testing Alphabet===");
        // duplicates should result in error
        // Alphabet a1 = new Alphabet("aabbcc");
        String test = "dcba";
        Alphabet a2 = new Alphabet(test);
        System.out.println("Alphabet: dcba");
        for (char c : test.toCharArray()) {
            System.out.println(c + ": " + a2.toIndex(c));
        }
        System.out.println("R: " + a2.radix());
        System.out.println("LgR: " + a2.lgR());
        System.out.println("toString: " + a2.toChars(new int[]{1,0,1,2,3}));
        System.out.println("toIndices: " + Arrays.toString(a2.toIndices("ccbbaadd")));

        System.out.println("===Testing LSD sort===");
        Alphabet a = new Alphabet("abc1234def");
        String[] s = new String[] {
                "db2",
                "abb123",
                "baa",
                "c123",
                "12abcd",
                "bbaacc",
                "de",
                "1abe",
                "",
                "aabc234",
                "2defabc",
                "dfabc123",
                "c12aba",
                "aaa",
                "b",
                "a",
                "cca",
                "d",
                "1a2b3c",
                "2def",
                "234",
                "bad",
                "12aaaa",
                "def123",
                "da",
                "baba",
                "de",
        };
        String[] lsd = s.clone();
        System.out.println("Before LSD sort: " + Arrays.toString(lsd));
        LSD.sort(a, lsd, 8); // 8 is max length
        System.out.println("After LSD sort: " + Arrays.toString(lsd));

        System.out.println("===Testing MSD sort===");
        String[] msd = s.clone();
        System.out.println("Before MSD sort: " + Arrays.toString(msd));
        MSD.sort(a, msd);
        System.out.println("After MSD sort: " + Arrays.toString(msd));
    }
}
