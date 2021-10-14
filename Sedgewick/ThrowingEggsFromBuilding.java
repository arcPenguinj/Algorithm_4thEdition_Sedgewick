public class ThrowingEggsFromBuilding {
  private static int Target_Floor = 1; //global variable, number can be changed during testing
  private static int TOTAL_FLOOR_NUMBER = 100; //global variable, number can be changed during testing

  //main function for testing
  public static void main(String[] args) {
    ThrowingEggsFromBuilding tefb = new ThrowingEggsFromBuilding();
    int F = tefb.find_F1(TOTAL_FLOOR_NUMBER);
    System.out.println("minimal floor to broken egg for solution 1 is:" + F);
    int F2 = tefb.find_F2(TOTAL_FLOOR_NUMBER);
    System.out.println("minimal floor to broken egg for solution 2 is:" + F2);
  }

  //function for lg n solution
  public int find_F1(int n){
    return find_minimal_floor(n, 0); //call the binary search function to find F
  }

  //function for 2 lg F solution
  public int find_F2(int n){
    int hi = find_broken_egg_floor(1); // assume the first floor to throw egg is 1
    int lo = hi / 2; // since we recursively use 2 * key to find hi, then lo must be hi / 2
    //same step as first question, after the range is found between hi and lo point, we need to do a binary search
    int result = find_minimal_floor(hi, lo);
    return result > n ? -1 : result; // for special case, if target floor > total floor, then return -1, otherwise, return the result
  }

  //helper function for 2 lg F solution
  private int find_broken_egg_floor(int key){
    //if egg broken at the first time, then return key directly
    if (broken_egg(key)) {
      return key;
    }
    else {
      return find_broken_egg_floor(key * 2); // if egg not broken, recursive call, everytime key = key * 2
    }
  }


  //helper function, binary search to find the F floor
  private int find_minimal_floor(int hi, int lo) {
    // if lo > hi, reach special case
    if (lo <= hi) {
       int mid = (lo + hi) / 2; //mid point always be (hi + lo) / 2
      //if egg broken,
       if (broken_egg(mid)) {
         //check one floor lower to see whether this is the first "broken egg" floor
         //if the lower floor not broken the egg, then return the floor(the mid)
         if (!broken_egg(mid - 1)) {
           return mid;
         }
         //hi changed to mid - 1;
         hi = mid - 1;
         return  find_minimal_floor(hi, lo); //recursive call
       }
       //if egg not broken, lo changed to mid + 1;
       else if (!broken_egg(mid)) {
         lo = mid + 1;
         return find_minimal_floor(hi, lo); //recursive call
       }
    }
    return -1;// special case, when target floor is not in range
  }

  //helper function, return true if egg broken
  private Boolean broken_egg(int f){
    //if f greater than Target_FLOOR, egg broken, return true
    if (f >= Target_Floor) {
      return true;
    }
    //if egg not broken, return false
    else {
      return false;
    }
  }
}
