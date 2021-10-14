package Q3;

import java.util.Arrays;
import java.util.HashMap;

public class Alphabet {
    private char[] indices; // index -> char
    private HashMap<Character, Integer> chars; // char -> index
    private int R; // total size of the alphabet

    public Alphabet(String s) {
        // constructor
        // note - we don't allow duplicates, if there are duplicate char in s, will throw exception
        indices = s.toCharArray(); // convert s to char array
        Arrays.sort(indices); // sort the char array

        this.chars = new HashMap<>(); // init hashmap

        // go through the array to check dupes: if there are same char in consecutive position, throw exception
        char prev = Character.MAX_VALUE;
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] == prev) {
                throw new IllegalArgumentException("Found duplicate character " + indices[i] + " in input string " + s);
            }
            prev = indices[i];

            // add char -> index map
            chars.put(indices[i], i);
        }

        R = indices.length; // size of the Alphabet
    }

    public char toChar(int index) {
        if (index < 0 || index >= R) {
            // validation
            throw new IllegalArgumentException("index is out of bound");
        }
        return indices[index]; // return index->char
    }

    public int toIndex(char c) {
        if (!this.contains(c)) {
            // validation
            throw new IllegalArgumentException(c + " is not in Alphabet");
        }
        return chars.get(c); // return char -> index
    }

    public boolean contains(char c) {
        return chars.containsKey(c); // check if c exists
    }

    public int radix() {
        return R; // radix (the number of chars in Alphabet)
    }

    public int lgR() {
        return (int) (Math.log(R) / Math.log(2)); // number of bits to represent an index
    }

    public int[] toIndices(String s) { // convert string to int array
        int[] ret = new int[s.length()]; // create new int array to return
        char[] s_c = s.toCharArray(); // convert s to char array
        for(int i = 0; i < s_c.length; i++) {
            ret[i] = chars.get(s_c[i]); // loop through char array to convert to its index
        }
        return ret;
    }

    public String toChars(int[] indices) { // convert int array to a string
        char[] ret = new char[indices.length]; // create new char array for return string
        for (int i = 0; i < indices.length; i++) {
            ret[i] = this.toChar(indices[i]); // loop through indices and convert to its char value
        }
        return new String(ret); // convert char array to String
    }

}
