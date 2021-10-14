//reference: https://stackoverflow.com/questions/37619532/how-to-use-lsd-string-sort-without-having-to-enter-a-fixed-length

public class LSDFlexibleLengthStringSort {

  private LSDFlexibleLengthStringSort() {
  }

  //went through all strings in a,
  //find the max length string in the string array
  private static int maxString(String[] a){
    int max = 0;
    for (String s: a){
      int len = s.length();
      if (len > max) {
        max = len;
      }
    }
    return max;
  }

  //i represents index of string in string array
  //d represents index of char in string
  //the helper function will return char at d, if d greater than or equals to a[i].length or
  //smaller than 0, return 0; otherwise, return char at d as int.
  private static int findCharAt(int i, int d, String[] a){
    if (d < 0 || d >= a[i].length()){
      return 0;
    }
    return a[i].charAt(d);
  }

  //regular LSD implementation in book pg707
  public static void sort(String[] a) {
    int n = a.length;
    int R = 256; // ASCII size;
    String[] aux = new String[n]; //initialize an auxiliary string with size n
    int w = maxString(a); //length of the longest string in a

    for (int d = w -1; d >=0; d--){
      //Sort by key-indexed counting on dth char
      int[] count = new int[R+1]; //count start from 1, so R+1;
      for (int i = 0; i < n; i++){
        int c = findCharAt(i,d,a); //get the char value at d, 0 if outside of the range
        count[c + 1]++; //count for each char
      }
      for (int r = 0; r < R; r++){
        count[r + 1] += count[r]; //cumulates the count
      }

      for (int i = 0; i < n; i++){
        int c = findCharAt(i,d,a);
        aux[count[c]++] = a[i]; //based on count, copy a[i] to the correct sorted place in aux
      }

      //copy aux back to a
      for (int i = 0; i < n; i++){
        a[i] = aux[i]; //assign aux[i] to a[i]
      }
    }
  }

  public static void main(String[] args) {
    String[] a = {"123a", "abc1", "12", "345", "45", "1ABC", "1abc", "01234"};
    sort(a);

    int n = a.length;
    for(int i = 0; i < n; i++){
      System.out.println(a[i]);
    }
  }
}