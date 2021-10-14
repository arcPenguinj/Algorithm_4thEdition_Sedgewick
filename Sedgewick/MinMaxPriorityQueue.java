public class MinMaxPriorityQueue <Key extends Comparable<Key>> {
  private int n; //number of elements on PQ
  private Key[] minPQ;
  private Key[] maxPQ;
  private int max;

  //constructor for the class, client need to input the size of the pq
  public MinMaxPriorityQueue(int maxSize) {
    // initialize the max size for minPQ and maxPQ
    minPQ = (Key[]) new Comparable[maxSize + 1];
    maxPQ = (Key[]) new Comparable[maxSize + 1];
    max = maxSize;
  }

  //main function for testing
  public static void main(String[] args) {
    int[] a = {8, 9, 10, 4, 5, 3, 2, 15};
    MinMaxPriorityQueue<Integer> mmpq = new MinMaxPriorityQueue<>(999);

    System.out.println("==== First, insert the numbers ====");
    for (int i = 0; i < a.length; i++) {
      mmpq.insert(a[i]);
      System.out.print("add: " + a[i] + " max :" + mmpq.findMax() + ", min: " + mmpq.findMin() + " size: " + mmpq.size() + "\n");
    }

    System.out.println("==== Now, test delete ====");
    System.out.println("Deleted max: " + mmpq.deleteMax());
    System.out.println("Deleted min: " + mmpq.deleteMin());
    System.out.println("Size: " + mmpq.size());
    System.out.println("Deleted max: " + mmpq.deleteMax());
    System.out.println("Deleted min: " + mmpq.deleteMin());
    System.out.println("Size: " + mmpq.size());
  }

  //check the size of pq if it is empty
  public boolean isEmpty() {
    return this.size() == 0;
  }

  //the size of the PQ
  public int size() {
    return n;
  }

  //search for the max from maxHeap, time complexity should be O1
  //always return the root of maxHeap.
  public Key findMax() {
    return maxPQ[1];
  }

  //search for the min from minHeap, time complexity should be O1
  //always return the root of minHeap.
  public Key findMin() {
    return minPQ[1];
  }

  //insert function, when need to add a key to the pq
  public void insert(Key key) {
    //if the elements size are not exceed the maximum size of the pq,
    //then n increment by 1 for everytime a new key is inserted.
    if (this.size() < max) {
      n++;
    }
    this.maxPQ[this.size()] = key; // add the key to the end of the maxPQ
    swim(this.size(), true); //then swim the key to the right position.
    this.minPQ[this.size()] = key; // add the key to the end of the minPQ
    swim(this.size(), false); //then swim the key to the right position.
  }

  //remove the min key from both minHeap and maxHeap
  //delete root from minHeap
  //loop to search the same key in maxheap and then delete.
  public Key deleteMin() {
    Key min = findMin(); //get the min key in minPQ, this is the element need to be deleted

    //delete min from maxPQ
    deleteFirst(false);
    findAndDelete(min, true);
    n--;
    return min;
  }

  //remove the max key from both minHeap and maxHeap
  //delete root from maxHeap
  //loop to search the same key in minheap and then delete.
  public Key deleteMax() {
    Key max = findMax(); //get the max key in maxPQ, this is the element need to be deleted

    //delete max from minPQ
    deleteFirst(true);
    findAndDelete(max, false);
    n--;
    return max;
  }

  //delete root from pq, like delete max in maxPQ, delete min in minPQ
  private void deleteFirst(boolean isMaxPQ) {
    if (isMaxPQ) {
      exch(maxPQ, 1, this.size());//exchange the element with the last element in the pq
      maxPQ[this.size()] = null; //assign null to this element
    } else {
      exch(minPQ, 1, this.size());//exchange the element with the last element in the pq
      minPQ[this.size()] = null; //assign null to this element
    }
    sink(1, isMaxPQ); //sink the exchanged element to the right position
  }


  //search min in maxPQ and then delete, search max in minPQ then delete.
  private void findAndDelete(Key key, boolean isMaxPQ){
    for (int i = 1; i <= this.size(); i++) {
      if (isMaxPQ) {
        if (maxPQ[i] == key) {
          if (i == this.size()) {
            maxPQ[i] = null;
          } else {
            exch(maxPQ, i, this.size());
            maxPQ[this.size()] = null;
            sink(i, true);
          }
          break;
        }
      } else {
        if (minPQ[i] == key) {
          if (i == this.size()) {
            minPQ[i] = null;
          } else {
            exch(minPQ, i, this.size());
            minPQ[this.size()] = null;
            sink(i, false);
          }
          break;
        }
      }
    }
  }

  //same as the code in book, add a boolean to check it is maxPQ or minPQ
  //helper function for insert, to swim up the target node into the right position
  private void swim(int k, boolean isMaxPQ) {
    if (isMaxPQ) {
      while (k > 1 && less(this.maxPQ, k/2, k)) {
        exch(this.maxPQ, k/2, k);
        k = k/2;
      }
    } else {
      while (k > 1 && less(this.minPQ, k, k/2)) {
        exch(this.minPQ,k/2, k);
        k = k/2;
      }
    }
  }

  //same as the code in book, add a boolean to check it is maxPQ or minPQ
  //helper function for delete, to sink down the target node to the right position
  private void sink(int k, boolean isMaxPQ) {
    while (2 * k <= this.size() - 1) {
      int j = 2 * k;
      if (isMaxPQ) {
        if (j < this.size() - 1 && less(maxPQ, j, j + 1)) j++;
        if (!less(maxPQ, k, j)) break;
        exch(maxPQ, k, j);
      } else {
        if (j < this.size() - 1 && less(minPQ, j + 1, j)) j++;
        if (less(minPQ, k, j)) break;
        exch(minPQ, k, j);
      }
      k = j;
    }
  }
  //helper function, if i < j, return true; false otherwise.
  private boolean less(Key[] pq, int i, int j) {
    return pq[i].compareTo(pq[j]) < 0;
  }

  //helper function, exchange two element, assign the first element to a temporary variable;
  //assign the second element to the first
  //assign the temp to the second.
  private void exch(Key[] pq, int i, int j){
    Key temp = pq[i];
    pq[i] = pq[j];
    pq[j] = temp;
  }
}
