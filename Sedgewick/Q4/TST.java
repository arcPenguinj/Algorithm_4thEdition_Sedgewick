package Q4;

public class TST <Value>{
    private Node root; // root of the trie
    private class Node {
        char c; // current character
        Node left, mid, right; // three subtries
        Value val; // value associated to the string
        int size; // size of subtrie
    }

    public Value get(String key) {
        // same as in TrieT
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

    public int size() {
        // same as TrieST
        return size(root); // use helper function to return the size
    }

    public boolean contains(String key) {
        // same as TrieST
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        return get(key) != null; // if get(key) is not null, return true
    }

    public void put(String key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        // the only difference to the code in book here is to keep track of size to new node
        boolean isNewKey = !contains(key); // check if key already exists in Trie, if not, all nodes in the path needs to update size
        root = put(root, key, val, 0, isNewKey);
    }

    public void delete(String key) { // delete key. Use BST to delete the node
        if (key == null) { // validation
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (!contains(key)) { // if key doesn't exist in TST, do nothing
            return;
        }

        root = delete(root, key, 0); // use helper function to do deletion
    }

    private int size(Node x) {
        // return the size of subtrie of current node x
        if (x == null) return 0; // if node x is null, size is 0
        return x.size; // since we maintained size of subtrie in each node, just return the value
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null; // if parent node is null, return null
        char c = key.charAt(d); // get next character
        if (c < x.c) {
            return get(x.left, key, d); // if next c < current c, go to left subtrie
        } else if (c > x.c) {
            return get(x.right, key, d); // if next c > current c, go to right subtrie
        } else if (d < key.length() - 1) { // if we haven't reached to the end of string
            return get(x.mid, key, d + 1); // continue with mid subtrie
        } else {
            // otherwise, we've reached the end of string, and current c is matching key's character, return current node
            return x;
        }
    }

    private Node put(Node x, String key, Value val, int d, boolean isNewKey) {
        char c = key.charAt(d); // current character in string
        if (x == null) {
            x = new Node(); // if input node is null, create a new Node
            x.c = c; // assign current char to node's c
        }

        if (c < x.c) { // if current c is less than current node's c, move left
            x.left = put(x.left, key, val, d, isNewKey);
        } else if (c > x.c) { // if current c is greater than current node's c, move right
            x.right = put(x.right, key, val, d, isNewKey);
        } else if (d < key.length() - 1) { // if current c matches, but haven't reached end of string, continue with mid subtrie
            x.mid = put(x.mid, key, val, d + 1, isNewKey);

            if (isNewKey) { // if we are addning new string, update size by 1
                x.size = x.size + 1;
            }
        } else { // this is when we reached the end of string
            x.val = val; // update value to the node

            if (isNewKey) {
                x.size = x.size + 1; // update size
            }
        }

        return x; // at the end, return node x
    }

    private Node delete(Node node, String key, int d) { // helper function for delete
        if (node == null) { // when current node is null, do nothing
            return null;
        }

        if (d == key.length() - 1) {  // if we reached end of String
            node.size = node.size - 1; // update size
            node.val = null; // wipe off value
        } else {
            char c = key.charAt(d); // find the character we are looking for at position d

            if (c < node.c) { // if c less than current node's c, move left
                node.left = delete(node.left, key, d);
            } else if (c > node.c) { // if c greater than current node's c, move right
                node.right = delete(node.right, key, d);
            } else { // c matches current node's c
                node.size = node.size - 1; // update size
                node.mid = delete(node.mid, key, d + 1); // continue with mid subtrie
            }
        }

        if (node.size == 0) { // after deletion, if current node's subtrie is 0, need to clean up current subtrie using BST delete
            if (node.left == null && node.right == null) { // current node has no children, just delete itself
                return null;
            } else if (node.left == null) { // left subtrie is empty, just return right subtrie
                return node.right;
            } else if (node.right == null) { // right subtrie is empty, just return left subtrie
                return node.left;
            } else { // current node has both left and right subtrie
                Node aux = node; // temporary copy of current node
                node = min(aux.right); // move minimum of right subtrie to current node
                node.right = deleteMin(aux.right); // remove the minimum of right subtrie
                node.left = aux.left; // connect back left subtrie
            }
        }

        return node;
    }

    private Node min(Node node) {
        // find min node of current node's sub trie
        if (node.left == null) { // if current left is null, current node is min
            return node;
        }

        return min(node.left); // go left subtrie
    }

    private Node deleteMin(Node node) {
        // delete the min node of current node's subtrie
        if (node.left == null) { // if left child is null, just delete current node
            return node.right;
        }

        node.left = deleteMin(node.left); // delete the min in left subtrie
        return node; // return current node after min node is deleted from left subtrie
    }
}
