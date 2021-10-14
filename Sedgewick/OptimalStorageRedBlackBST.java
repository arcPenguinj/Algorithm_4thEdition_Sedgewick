public class OptimalStorageRedBlackBST<Key extends Comparable<Key>, Value> {

  //main function for testing
  public static void main(String[] args) {
    OptimalStorageRedBlackBST<Integer, Integer> bst = new OptimalStorageRedBlackBST<>();
    bst.put(1,1);
    bst.put(5, 5);
    bst.put(-2,-2);
    System.out.println("1st test:");
    System.out.println(bst.toString());
    System.out.println("size: " + bst.size());
    System.out.println("Contains 9? " + bst.contains(9));
    bst.put(10,10);
    bst.put(7,7);
    bst.put(11,11);
    bst.put(4,4);
    bst.put(9,9);
    bst.put(14,14);
    bst.put(16,16);
    bst.put(3,3);
    System.out.println("2nd test:");
    System.out.println(bst.toString());
    System.out.println("size: " + bst.size());
    System.out.println("Contains 9? " + bst.contains(9));
    bst.put(9,100); // this should trigger a replace
    bst.put(4, 200); // another replace
    System.out.println("Testing replace:");
    System.out.println(bst.toString());
    System.out.println("size: " + bst.size());
    System.out.println("Contains 9? " + bst.contains(9));
  }

  //subclass, each node has a key, value, left & right child and the size of the subtree.
  private class Node {
    Key key;  // key
    Value value; // data
    Node left, right; // left right child
    int n;  // # nodes in this subtree

    //constructor of the Node class
    Node(Key key, Value val, int n) {
      this.key = key;
      this.value = val;
      this.n = n;
    }

    public String toString() {
      // for testing, print out whole tree as a string
      return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
    }

    public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
      // this part is from https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java/8948691#8948691
      if(right!=null) {
        right.toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
      }
      sb.append(prefix).append(isTail ? "└── " : "┌── ").append("(" + key.toString() + ", " + value.toString() + ")").append("\n");
      if(left!=null) {
        left.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
      }
      return sb;
    }
  }

  private Node root; // root of the tree

  public int size() {
    return this.size(root);
  }

  public void put(Key key, Value val) {
    // Search for key, update value if found; grow tree if new
    root = put(root, key, val);
    if (isRed(root)) {
      swapChildren(root); // should always mark root as black
    }
  }

  public String toString() {
    return this.root.toString();
  }

  public boolean isEmpty() {
    return size(root) == 0;
  }

  public Value get(Key key) {
    if (key == null) {
      return null;
    }
    return get(root, key);
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  private boolean isRed(Node node) {
    // test if a node is red, time complexity O(lg n)
    if (node == null) return false; // null node has no color

    if (node == root && size(root) == 1) return false; // if there's only 1 node, treat as black

    // if the node has no sibling, it is red
    Node current = root; // save current node
    while (current != null) { // loop whole tree
      if (current.left == node && current.right == null) return true; // no siblings, return red
      if (current.right == node && current.left == null) return true; // no siblings, return red
      if (current.key.compareTo(node.key) == 0) {
        break; // found the node
      }
      if (current.left == null) {
        current = current.right; // left child is null, searching right child
      } else if (current.right == null) {
        current = current.left; // right child is null, searching left child
      } else {
        // since red node will have their children inverted, need to find the right path
        int comp1 = current.left.key.compareTo(current.right.key);
        int comp2 = current.key.compareTo(node.key);
        if (comp1 < 0) {
          // a black node
          if (comp2 < 0) {
            current = current.right; // right has larger keys
          } else {
            current = current.left; // left has smaller keys
          }
        } else {
          // a red node
          if (comp2 < 0) {
            current = current.left; // left has larger keys
          } else {
            current = current.right; // right has smaller keys
          }
        }
      }
    }

    if (node.left == null || node.right == null) {
      return false; // at this point, node has sibling. Then if it only has 0 or 1 child, it means its
                    // sub-tree hasn't filled in, so this node is black.
    }

    // at last, check if left child is greater than right child, if so, this is red
    return node.left.key.compareTo(node.right.key) > 0;
  }

  private Value get(Node node, Key key) {
    if (node == null) {
      return null;
    }

    int compare = key.compareTo(node.key);
    if (compare < 0) {
      if (!isRed(node)) {
        return get(node.left, key);
      } else {
        return get(node.right, key);
      }
    } else if (compare > 0) {
      if (!isRed(node)) {
        return get(node.right, key);
      } else {
        return get(node.left, key);
      }
    } else {
      return node.value;
    }
  }

  private void swapChildren(Node n) {
    // use the trick to mark a node red: swap it's left/right child
    if (n == null) return; // if n is null, do nothing
    Node temp = n.left; // save left to temp
    n.left = n.right; // assign right to left
    n.right = temp; // assign temp to right
  }

  private Node rotateLeft(Node node) {
    // same logic as in https://github.com/LeonChen1024/algorithm-4th-exercise/blob/bbe26baf820241979bbae8ae7a899ee83c533a01/alg4/src/edu/princeton/cs/exercise/chapter3_3/E3_3_29.java
    if (node == null) {
      return null; // special case
    }

    if (!isRed(node)) {
      // check if there's only one child, if so, don't rotate
      if (node.right == null) {
        return node;
      }
    } else {
      if (node.left == null) {
        return node;
      }
    }

    if (isRed(node)) {
      // if node is red, swap its children before rotate
      swapChildren(node);
    }

    boolean isNewRootRed = isRed(node.right);
    if (isNewRootRed) {
      // if new root is red, swap its children before rotate
      swapChildren(node.right);
    }

    // below rotate logic is the same as text book p434
    Node newRoot = node.right;
    node.right = newRoot.left;
    newRoot.left = node;

    if (isRed(node)) {
      // if original node is red, since we swapped before rotate, now we swap the new node back
      // to mark it back to red
      swapChildren(newRoot);
    }

    if (!isRed(node)) {
      // after rotation, mark the old node to red
      swapChildren(node);
    }

    newRoot.n = node.n; // update size of new root
    node.n = size(node.left) + 1 + size(node.right); // update size of root

    return newRoot;
  }

  private Node rotateRight(Node node) {
    // same as https://github.com/LeonChen1024/algorithm-4th-exercise/blob/bbe26baf820241979bbae8ae7a899ee83c533a01/alg4/src/edu/princeton/cs/exercise/chapter3_3/E3_3_29.java
    if (node == null) {
      return null; // special case
    }

    // first check if only one child, if so, do not rotate
    if (!isRed(node)) {
      if (node.left == null) {
        return node;
      }
    } else {
      if (node.right == null) {
        return node;
      }
    }

    if (isRed(node)) {
      // if original node is red, swap the children before rotate
      swapChildren(node);
    }

    boolean isNewRootRed = isRed(node.left);
    if (isNewRootRed) {
      // same for its left child, if it's red (most likely), swap before rotate
      swapChildren(node.left);
    }

    // same rotate as in text book p434
    Node newRoot = node.left;
    node.left = newRoot.right;
    newRoot.right = node;

    // after rotation, paint the node red
    if (!isRed(node)) {
      swapChildren(node);
    }
    //Since in this configuration nodes without children are by default black,
    // the new root becomes red and its children become black
    if (!isRed(newRoot) && newRoot.left != null && newRoot.right != null) {
      swapChildren(newRoot);
      swapChildren(newRoot.left);
      swapChildren(newRoot.right);
    }

    newRoot.n = node.n; // update size of new root
    node.n = size(node.left) + 1 + size(node.right); // update size of root

    return newRoot;
  }

  private void flipColors(Node h) {
    // special case, do nothing
    if (h == null || h.left == null || h.right == null) return;

    //The root must have opposite color of its two children
    if ((isRed(h) && !isRed(h.left) && !isRed(h.right))
        || (!isRed(h) && isRed(h.left) && isRed(h.right))) {
      swapChildren(h);
      swapChildren(h.left);
      swapChildren(h.right);
    }
  }

  private Node put(Node h, Key key, Value val) {
    if (h == null) {
      return new Node(key, val, 1);
    }

    if (h.key.compareTo(key) == 0) {
      // find key, replace value
      h.value = val;
    } else if (h.key.compareTo(key) > 0) {
      // value is less than current node
      if (isRed(h)) h.right = put(h.right, key, val); // if red, go right
      else h.left = put(h.left, key, val); // if black, go left
    } else {
      // value is greater than current node
      if (isRed(h)) h.left = put(h.left, key, val); // if red, go left
      else h.right = put(h.right, key, val); // if black, go right
    }

    // first rule, rotate left
    // same logic as in https://github.com/LeonChen1024/algorithm-4th-exercise/blob/bbe26baf820241979bbae8ae7a899ee83c533a01/alg4/src/edu/princeton/cs/exercise/chapter3_3/E3_3_29.java
    if (!isRed(h)) {
      // black tree, same as in text book
      if (isRed(h.right) && !isRed(h.left)) {
        h = rotateLeft(h);
      }
    } else {
      // red tree, switch left and right
      if (isRed(h.left) && !isRed(h.right)) {
        h = rotateLeft(h);
      }
    }

    // second rule, rotate right
    if (!isRed(h)) {
      if (!isRed(h.left)) {
        // both h and h.left are black, same as in text book
        if (isRed(h.left) && isRed(h.left.left)) {
          h = rotateRight(h);
        }
      } else {
        // h.left is right, test against h.left.right as it's inverted
        if (isRed(h.left) && isRed(h.left.right)) {
          h = rotateRight(h);
        }
      }
    } else {
      if (!isRed(h.right)) {
        // h is red, h.right is black, test against h.right and h.right.left
        if (isRed(h.right) && isRed(h.right.left)) {
          h = rotateRight(h);
        }
      } else {
        // both h and h.right are red, test against h.right and h.right.right
        if (isRed(h.right) && isRed(h.right.right)) {
          h = rotateRight(h);
        }
      }
    }

    // 3rd rule, no matter h is red or not, if both its children are red, flip the color
    if (isRed(h.left) && isRed(h.right)) {
      flipColors(h);
    }

    h.n = size(h.left) + size(h.right) + 1; // update size
    return h;
  }

  private int size(Node n) {
    if (n == null) return 0;
    else return n.n;
  }

}
