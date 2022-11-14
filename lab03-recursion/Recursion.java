// l am the sole authors of the work in this repository.
/*
 * Recursion.java
 *
 * Starter code for the recursion lab.
 *
 */
import structure5.*;
import java.util.Arrays;

public class Recursion {

  // Note: Warmup questions are not graded, but you may wish to
  // complete & test them since later, graded questions build
  // on them.

  /***** Warmup 0.1 ********************************************/

  /**
   * Takes a non-negative integer and returns the sum of its digits.
   * @param n a non-negative integer
   * @return the sum of n's digits
   * @pre n is non negative.
   * @post returns the sum of n's digits.
   */
  public static int digitSum(int n) {
    Assert.pre(n >= 0, "n should be a non negative number");
    // for values greater than zero and less than 10 return that number.
    if (n < 10) {
      return n;
    }
    // add the last digit, and reduce n.
    return n % 10 + digitSum(n / 10);
  }

  /***** Warmup 0.2 ********************************************/

  /**
   * Given a set of integers and a target number, determines
   * where there is a subset of those numbers that sum to the
   * target number.
   * @param setOfNums a set of numbers that may appear in the subset
   * @param targetSum the target value for the subset
   * @return true if some subset of numbers in setOfNums sums to targetSum
   * @pre The array setOfNums should not equal null
   * @post true if there is a subset that adds to target sum.
   */
  public static boolean canMakeSum(int[] setOfNums, int targetSum) {
    Assert.pre(setOfNums != null, "The array setOfNums should not equal null");
    // base case, checks if nums length is 0
    // returns true if targetSum == 0
    if (setOfNums.length == 0) {
      return targetSum == 0;
    }

    // targetSum minus the current number
    int newTarget = targetSum - setOfNums[0];

    // included in solution
    boolean include = canMakeSum(Arrays.copyOfRange(setOfNums, 1, setOfNums.length), newTarget);

    // excluded in solution
    boolean exclude = canMakeSum(Arrays.copyOfRange(setOfNums, 1, setOfNums.length), targetSum);

    return include || exclude;
  }

  /***** 1 ***************************************************/

  /**
   * Return number of cannoballs in pyramid with the given `height`.
   * @param height the height of the cannonball tower
   * @return the number of cannonballs in the entire tower
   * @pre height should be greater than zero.
   * @post returns the number of cannon balls given height.
   */
  public static int countCannonballs(int height) {
    Assert.pre(height > 0, " Height should be greater than zero");
    if (height == 1) {
      return 1;
    }
    // square height, and reduce height
    int numOfCannonBalls = (height * height) + countCannonballs(height - 1);
    Assert.post(numOfCannonBalls >= height, "Number of cannon balls should be > height");
    return numOfCannonBalls;
  }

  /***** 2 ***************************************************/

  /**
   * A method that determines if a string reads the same forwards
   * and backwards.
   * @param str the string to check
   * @return true if `str` is a palindrome.
   * @pre str should be a string.
   * @post returns true if str is palindrome and otherwise false.
   */
  public static boolean isPalindrome(String str) {
    // check if str is of length 1 or is empty or is a space.
    Assert.pre(str instanceof String, "Str should be a string");
    if (str.length() == 1 || str.equals("") || str.equals(" ")) {
      return true;
    }
    // check if letter at beginning and end are the same
    if (str.charAt(0) != str.charAt(str.length() - 1)) {
      return false;
    }

    return isPalindrome(str.substring(1, str.length() - 1));
  }

  /***** 3 ***************************************************/

  /**
   * Checks whether `str` is a string of properly nested and matched
   * parens, brackets, and braces.
   * @param str a string of parens, brackets, and braces
   * @return true if str is properly nested and matched
   * @pre str should be brackets, parens and braces without spaces.
   * @post returns true if the string is balanced.
   */
  public static boolean isBalanced(String str) {
    Assert.pre(str instanceof String, "Str should be a string");
    // base case, check if string is empty.
    if (str.equals("")) {
      return true;
    }

    // find the first end bracket
    int endBracket = firstEndBracket(str); // stores the index of first end bracket or -1.
    // if there is no end bracket, statement is false
    if (endBracket == -1 || endBracket == 0) {
      return false;
    }

    // using ascii check if end bracket is of same type as the preceding bracket
    // if they are the same continue checking, else return
    if ((int) str.charAt(endBracket) - (int) str.charAt(endBracket - 1) == 1
        || (int) str.charAt(endBracket) - (int) str.charAt(endBracket - 1) == 2) {
      return isBalanced(str.substring(0, endBracket - 1) + str.substring(endBracket + 1, str.length()));
    } else {
      return false;
    }

  }

  /**
   * Finds the index of the first occurance of an end
   * bracket in String str.
   * @param str a string of parens, brackets, and braces
   * @return int of smallest end bracket index
   * @pre str should be a string of brackets, parens and braces
   * @post return first occurrence of an end bracket.
   */
  protected static int firstEndBracket(String str) {
    Assert.pre(str instanceof String, "Str should be a string");
    int square = str.indexOf("]"); // brackets index
    int curly = str.indexOf("}"); // brace index
    int reg = str.indexOf(")"); // parens index
    // add the indexes in an array and sort them
    int[] sortedIndex = {square, curly, reg};
    Arrays.sort(sortedIndex);
    // check the smallest end bracket which is not -1 and return it.
    if (sortedIndex[0] != -1) {
      return sortedIndex[0];
    } else if (sortedIndex[1] != -1) {
      return sortedIndex[1];
    } else {
      return sortedIndex[2];
    }

  }

  /***** 4 ***************************************************/

  /**
   * A method to print all subsequences of str (order does not matter).
   * @param str string to print all subsequences of
   * @pre str should be a string
   * @post prints all subsequences of str.
   */
  public static void subsequences(String str) {
    Assert.pre(str instanceof String, "Str should be a string");
    // subsequence for helper should start as an empty string
    subsequenceHelper(str, "");
  }

  /**
   * Helper method for subsequences method
   * `soFar` keeps track of the characters currently in the substring
   * being built
   * @param str   string to print out subsequences of
   * @param soFar subsequences created along function call
   * @pre str should be a string
   * @post prints all subsequences of str.
   */
  protected static void subsequenceHelper(String str, String soFar) {
    // check if string is empty and print out the subsequences
    if (str.equals("")) {
      System.out.print(soFar + ", ");
    } else {
      // include first char
      subsequenceHelper(str.substring(1), soFar + str.charAt(0));
      // exclude first char
      subsequenceHelper(str.substring(1), soFar);
    }
  }

  /***** 5 ***************************************************/

  /**
   * A method to print the binary digits of a number.
   * @param number the number to print in binary
   * @pre number should be a number
   * @post prints out the binary version of given number.
   */
  public static void printInBinary(int number) {
    Assert.pre(number >= 0, "number cannot be negative");
    // base case, n is 1 0r 0
    if (number == 1 || number == 0) {
      System.out.print(number);
    } else {
      // gets the last binary digit
      int lastDig = number % 2;
      // gets the remaining part of number
      int rem = number / 2;

      printInBinary(rem);
      // prints after recursive call to print backwards
      System.out.print(lastDig);
    }
  }

  /***** 6a ***************************************************/

  /***** 6a ***************************************************/

  /**
   * Return whether a subset of the numbers in nums add up to sum,
   * and print them out.
   * @param nums      Array of integers
   * @param targetSum a single integer
   * @return returns true if there is a subset that adds to targetSum
   * @pre The array nums should not equal null
   * @post returns true if there is a subset that adds to targetSum
   *       and prints one of the subsets of nums
   */
  public static boolean printSubsetSum(int[] nums, int targetSum) {
    Assert.pre(nums != null, "The array nums should not equal null");
    // base case, checks if nums length is 0
    // returns true if targetSum == 0
    if (nums.length == 0) {
      return targetSum == 0;
    }

    // targetSum minus the current number
    int newTarget = targetSum - nums[0];

    // included in solution
    boolean include = printSubsetSum(Arrays.copyOfRange(nums, 1, nums.length), newTarget);
    if (include) {
      System.out.print(nums[0] + " ");
      return include;
    } else {
      // excluded in solution
      boolean exclude = printSubsetSum(Arrays.copyOfRange(nums, 1, nums.length), targetSum);
      return exclude;
    }
  }

  /***** 6b ***************************************************/

  /**
   * Return the number of different ways elements in nums can be
   * added together to equal sum (you do not need to print them all).
   * @param nums      array of integers
   * @param targetSum a single target integer.
   * @return returns number of possible subset solutions.
   * @pre target sum should be an integer and nums an array of integers
   * @post returns the number of possible subset solutions.
   */
  public static int countSubsetSumSolutions(int[] nums, int targetSum) {
    Assert.pre(nums != null, "Nums cannot be null");
    // base case, check if nums is of length 1
    if (nums.length == 1) {
      // if current num is equal to target sum return 1 else 0
      if (nums[0] == targetSum) {
        return 1;
      }
      return 0;
    }
    // base case, check if nums is of length 0
    if (nums.length == 0) {
      return 0;
    }
    int newTarget = targetSum - nums[0]; // diff of target sum and current num.
    if (newTarget == 0) {
      return 1;
    }
    return countSubsetSumSolutions(Arrays.copyOfRange(nums, 1, nums.length), newTarget)
        + countSubsetSumSolutions(Arrays.copyOfRange(nums, 1, nums.length), targetSum);
  }

  /***********************************************************/

  /**
   * Add testing code to main to demonstrate that each of your
   * recursive methods works properly.
   *
   * Think about the so-called corner cases!
   *
   * Remember the informal contract we are making: as long as all
   * predconditions are met, a method should return with all
   * postconditions met.
   */

  protected static void testCannonballs() {
    System.out.println("Testing cannonballs: ....");
    System.out.println(countCannonballs(3));
    System.out.println(countCannonballs(10));
  }

  protected static void testPalindrome() {
    System.out.println("Testing isPalindrome: ....");
    System.out.println(isPalindrome("mom"));
    System.out.println(isPalindrome("deeded"));
    System.out.println(isPalindrome("ablewasIereIsawelba"));
  }

  protected static void testBalanced() {
    System.out.println("Testing isBalanced: ....");
    System.out.println(isBalanced("[{[()()]}]")); // true
    System.out.println(isBalanced("[{[()()]}][{[()()]}]")); // true
    System.out.println(isBalanced("[{[()()]}{]{[()()]}]")); // false
  }

  protected static void testSubsequence() {
    System.out.println("Testing subsequences: ....");
    subsequences("abc");
    System.out.println();
    subsequences("CSCI136");
    System.out.println();
    subsequences("a");
    System.out.println();
    subsequences("");
    System.out.println();
    subsequences("abcd");
    System.out.println();
  }

  protected static void testBinary() {
    System.out.println("Testing printInBinary: ....");
    printInBinary(0);
    System.out.println();
    printInBinary(30);
    System.out.println();
    printInBinary(1);
    System.out.println();
    printInBinary(110);
    System.out.println();
    printInBinary(2048);
    System.out.println();
    printInBinary(43);
    System.out.println();
    printInBinary(3);
    System.out.println();
    printInBinary(4);
    System.out.println();
    printInBinary(6);
    System.out.println();
  }

  protected static void testCanMakeSum() {
    System.out.println("Testing canMakeSum: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    System.out.println(canMakeSum(numSet, 21));
    System.out.println(canMakeSum(numSet, 22));
    System.out.println(canMakeSum(numSet, 3));
    System.out.println(canMakeSum(numSet, 30));
  }

  protected static void testPrintSubsetSum() {
    System.out.println("Testing printSubsetSum: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    /*
     * System.out.println(printSubsetSum(numSet, 21));
     * System.out.println(printSubsetSum(numSet, 22));
     * System.out.println(printSubsetSum(numSet, 3));
     */
    System.out.println(printSubsetSum(numSet, 30));
  }

  protected static void testCountSubsetSum() {
    System.out.println("Testing countSubsetSumSolutions: ....");
    int[] numSet = {2, 5, 7, 12, 16, 21, 30};
    System.out.println(countSubsetSumSolutions(numSet, 21));
    System.out.println(countSubsetSumSolutions(numSet, 22));
    System.out.println(countSubsetSumSolutions(numSet, 3));
    System.out.println(countSubsetSumSolutions(numSet, 30));
  }

  /**
   * Main method that calls testing methods to verify
   * the functionality of each lab exercise.
   *
   * Please supplement the testing code with additional
   * correctness tests as needed.
   */
  public static void main(String[] args) {

    System.out.println(digitSum(1234));

    int[] arr = {3, 7, 1, 8, -3};
    System.out.println(canMakeSum(arr, 4));

    int[] arr2 = {3, 7, 5, 8};
    System.out.println(canMakeSum(arr2, 4));

    System.out.println(countCannonballs(4));
    System.out.println(countCannonballs(2));
    testCannonballs();

    testPalindrome();
    isPalindrome("123");
    testBalanced();

    testPrintSubsetSum();
    testSubsequence();
    testBinary();
    testCanMakeSum();
    testPrintSubsetSum();
    testCountSubsetSum();
  }
}
