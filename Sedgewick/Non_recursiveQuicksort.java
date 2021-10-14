import java.util.Stack;

public class Non_recursiveQuicksort {

  //subclass, that each node allows to store both low and high index.
  private static class Node implements Comparable {
    int low;
    int high;
    public Node(int low, int high) {
      this.low = low;
      this.high = high;
    }

    //helper function, get the range between each node
    public int range() {
      return this.high - this.low;
    }

    // able to compare two nodes since we need to push larger one into stack first
    @Override
    public int compareTo(Object o) {
      if (o == null) return 0; // invalid case
      Node other = (Node) o; //cast
      int rangeSelf = high - low; //get range of the node
      int otherRange = other.high - other.low; //get range of the node
      return rangeSelf - otherRange; // if greater than 0, push rangeSelf first.
    }
  }

  // code from book, partition the array into a[lo, j-1], a[j],a[j+1,hi] then return j
  private int partition(Comparable[] a, int lo, int hi){
    int i = lo; //assign lo to i
    int j = hi + 1; //assign hi + 1 to j
    Comparable pivot = a[lo]; //set the first element in the array to be pivot
    while (true) {
      //scan i to right, scan j to left; check for scan complete, and exchange
      while(less(a[++i], pivot)) {
        if (i == hi) break;
      }
      while (less(pivot, a[--j])) {
        if (j == lo) break;
      }
      if (i >= j) break;
      exch(a, i, j);
    }
    exch(a,lo,j);
    return j;
  }

  //helper function, if i < j, return true; false otherwise.
  private boolean less(Comparable i, Comparable j) {
    return i.compareTo(j) < 0;
  }

  //helper function, exchange two element, assign the first element to a temporary variable;
  //assign the second element to the first
  //assign the temp to the second.
  private void exch(Comparable[] a, int i, int j){
    Comparable temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  //quicksort function
  public void nonRecursiveQuicksort(Comparable[] a, int lo, int hi) {
    Stack<Node> stack = new Stack<>(); //since it's non-recursive, need a new stack can contain node
    Node node = new Node(lo, hi); //new a Node, contains lo and hi
    stack.push(node); // push such node into the stack
    while(!stack.empty()) { //keep looping if the stack is not empty yet
      Node currentNode = stack.pop(); //get the node we just pushed into the stack
      //check whether the range is <= 0, if it is, don't push the node into stack
      if (currentNode.range() <= 0) continue;
      int pivot = partition(a, currentNode.low, currentNode.high); //partition the array into two part
      Node left = new Node(currentNode.low, pivot - 1); //assign the left range to node left
      Node right = new Node(pivot + 1, currentNode.high); // assign the right range to node right
      //compare left range and right range, push the larger one first, then push the smaller one
      if (left.compareTo(right) > 0) {
        stack.push(left); //when left is larger, push left first
        stack.push(right);// then push right
      } else {
        stack.push(right);// if right is larger, push right first
        stack.push(left); // then push left
      }
    }
  }

  //main function for testing
  public static void main(String[] args) {
    Integer[] a = {9, 14, 8, 6, 17, 19, 4, 2, 20, 7};
    Non_recursiveQuicksort sort = new Non_recursiveQuicksort();
    sort.nonRecursiveQuicksort(a, 0, a.length - 1);
    for (int i = 0; i < a.length; i++) {
      System.out.println(a[i]);
    }
  }
}
