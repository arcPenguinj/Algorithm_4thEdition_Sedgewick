package Q4;

public class TrieST <Value> {
    // a modified TrieST from p737 with size in each node
    private final static int R = 256; // radix
    private Node root = new Node();

    private static class Node {
        private Object val; // value to the string
        private int size; // size of the sub trie
        private Node[] next = new Node[R]; // children nodes (with R-ways)
    }

    public Value get(String key) {
        if (key == null) { // check if null
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (key.length() == 0) { // check emtpy string
            throw new IllegalArgumentException("Key must have a positive length");
        }

        Node x = get(root, key, 0); // find the correct node
        if (x == null) return null; // if can't find, return null
        return (Value) x.val; // otherwise return the val
    }

    public void put(String key, Value val) {
        // the only difference to the code in book here is to keep track of size to new node
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        boolean isNewKey = !contains(key); // check if key already exists in Trie, if not, all nodes in the path needs to update size
        root = put(root, key, val, 0, isNewKey);
    }

    public boolean contains(String key) {
        if (key == null) { // check if input is null
            throw new IllegalArgumentException("Key cannot be null");
        }
        return get(key) != null; // just call this.get with input key, if there's node return true
    }

    public int size() {
        return size(root); // use helper function to return the size
    }

    public void delete(String key) {
        // delete key in trie, the difference between code in book is to update the size in each node
        if ( key == null) throw new IllegalArgumentException("trying to delete a null key");
        if (!contains(key)) return; // if key does not exist in current trie, do nothing
        root = delete(root, key, 0);
    }

    private int size(Node x) {
        // return the size of subtrie of current node x
        if (x == null) return 0; // if node x is null, size is 0
        return x.size; // since we maintained size of subtrie in each node, just return the value
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null; // if parent node is null, return null
        if (d == key.length()) return x; // if we've reached the end of searching string, return current node
        char c = key.charAt(d); // find the char at current searching position in key
        return get(x.next[c], key, d+1); // recursively searching next level in trie
    }

    private Node put(Node x, String key, Value val, int d, boolean isNewKey) {
        if (x == null) x = new Node(); // if input node is null, create a new Node
        if (isNewKey) {
            x.size += 1; // when adding a new string, update the size in Trie
        }
        if (d == key.length()) { // we've reached the end of string key in Trie
            x.val = val; // assign the value
            return x; // return the node
        }
        char c = key.charAt(d); // use next char to locate subtrie
        x.next[c] = put(x.next[c], key, val, d+1, isNewKey); // recursively add subtrie
        return x; // return current node
    }

    private Node delete(Node node, String key, int d) {
        // recursively delete the node for key, and update size during the subtrie
        if (node == null) return null; // if current node is null, do nothing
        node.size -= 1; // reduce size by 1
        if (d == key.length()) { // we've reached the end of subtrie
            node.val = null; // wipe the value of node
        } else {
            char c = key.charAt(d); // find next char to locate subtrie
            node.next[c] = delete(node.next[c], key, d+1); // continue with subtrie
        }

        if (node.val != null) {
            return node; // if current node has value, means current node is a string in trie, don't delete
        }
        for (char c = 0; c < R; c++) {
            if (node.next[c] != null) return node; // if current node has any sub trie remaining after deleting 'key', don't delete current node
        }

        return null; // now we've checked current node is no longer needed anymore, return null
    }
}
