// lam the sole author of work in this repository

import structure5.*;
import java.util.Random;

/**
 * A FrequencyList stores a set of characters each of which has
 * an associated integer frequency
 */

public class FrequencyList {

  private Vector<Association<String, Integer>> list; // hold the vector of associations

  /**
   * Construct an empty FrequencyList
   */
  public FrequencyList() {
    list = new Vector<Association<String, Integer>>();
  }

  /**
   * add(String ch)
   * If ch is in the FrequencyList, increment it's associated frequency
   * Otherwise add ch to FrequencyList with frequency 1
   * @param ch is the String to add to the FrequencyList
   */
  public void add(String ch) {
    // initialise a new association with ch, with initial count of 1
    Association<String, Integer> association = new Association<String, Integer>(ch, 1);
    // check if the ch is in list if not add it.
    int index = list.indexOf(association);
    if (index != -1) {
      // ch is in list.
      association = list.get(index);
      // update the value in the association
      association.setValue(association.getValue() + 1);
    } else {
      // if not found add it to the list.
      list.add(association);
    }
  }

  /**
   * Selects a character form this FrequencyList
   * @return a character from the FrequencyList with probability equal to its
   *         relative frequency
   */
  public String choose() {
    int sum = 0;
    // find sum all the values in the list
    for (int i = 0; i < list.size(); i++) {
      sum += list.get(i).getValue();
    }
    // select one at random based on probability
    Random random = new Random();
    int next = random.nextInt(sum) + 1;
    int currentSum = 0;
    for (int j = 0; j < list.size(); j++) {
      currentSum += list.get(j).getValue();
      if (next <= currentSum) {
        return list.get(j).getKey();
      }
    }
    return " ";
  }

  /**
   * Produce a string representation of the FrequencyList
   * @return a String representing the FrequencyList
   */
  public String toString() {
    // initialise a new string variable to store the string representation of class
    String frequencyListString = "";
    // for each association in vector, grab the key and value and add them to
    // frequencyListString
    for (int i = 0; i < list.size(); i++) {
      frequencyListString += list.get(i).getKey() + " | " + list.get(i).getValue() + ", ";
    }
    return frequencyListString;
  }

  // Use main to test your FrequencyList class
  public static void main(String[] args) {
    FrequencyList fl = new FrequencyList();
    FrequencyList newFl = new FrequencyList();
    fl.add("hello");
    fl.add("world");
    fl.add("hello");
    fl.add("b");
    fl.add("c");
    System.out.println(fl.toString());
    System.out.println(fl.choose());
    // System.out.println(newFl.choose());
  }

}
