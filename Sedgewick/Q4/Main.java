package Q4;

public class Main {
    public static void main(String[] args) {
        System.out.println("===Testing TrieST===");
        TrieST<Integer> trie = new TrieST<>();
        System.out.println("Initial size: " + trie.size());
        trie.put("abcd", 123);
        System.out.println("After putting (abcd, " + trie.get("abcd") + "), size is " + trie.size());
        trie.put("abab", 34);
        System.out.println("After putting (abab, " + trie.get("abab") + "), size is " + trie.size());
        trie.put("aba", 22);
        System.out.println("After putting (aba, " + trie.get("aba") + "), size is " + trie.size());
        trie.put("aba", 33);
        System.out.println("After updating (aba, " + trie.get("aba") + "), size is " + trie.size());
        trie.delete("xxx"); // do nothing
        System.out.println("After deleting xxx, size is " + trie.size());
        trie.delete("abab");
        System.out.println("After deleting abab, size is " + trie.size());

        System.out.println("===Testing TST===");
        TST<Integer> tst = new TST<>();
        System.out.println("Initial size: " + tst.size());
        tst.put("abcd", 123);
        System.out.println("After putting (abcd, " + tst.get("abcd") + "), size is " + tst.size());
        tst.put("abab", 34);
        System.out.println("After putting (abab, " + tst.get("abab") + "), size is " + tst.size());
        tst.put("aba", 22);
        System.out.println("After putting (aba, " + tst.get("aba") + "), size is " + tst.size());
        tst.put("aba", 33);
        System.out.println("After updating (aba, " + tst.get("aba") + "), size is " + tst.size());
        tst.delete("xxx"); // do nothing
        System.out.println("After deleting xxx, size is " + tst.size());
        tst.delete("abab");
        System.out.println("After deleting abab, size is " + tst.size());
    }
}
