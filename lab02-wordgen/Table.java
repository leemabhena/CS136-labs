
// lam the sole author of the work in this repository
import structure5.*;
import java.util.Random;

/**
 * A Table holds a collection of strings, each of which has an
 * associated FrequencyList
 */
public class Table {

  private Vector<Association<String, FrequencyList>> table; // holds the table data structure

  /** Construct an empty table */
  public Table() {
    table = new Vector<Association<String, FrequencyList>>();
  }

  /**
   * Updates the table as follows
   * If key already exists in the table, update its FrequencyList
   * by adding value to it
   * Otherwise, create a FrequencyList for key and add value to it
   * @param key   is the desired k-letter sequence
   * @param value is the character to add to the FrequencyList of key
   */
  public void add(String key, String value) {
    // create FrequencyList object and add value
    FrequencyList list = new FrequencyList();
    list.add(value);
    // initialise new dict entry of Association
    Association<String, FrequencyList> dict = new Association<String, FrequencyList>(key, list);
    // check if key already in table
    int index = table.indexOf(dict);
    if (index != -1) {
      // already in table add, the value to FrequencyList
      dict = table.get(index);
      list = dict.getValue();
      list.add(value);
      dict.setValue(list);
    } else {
      // if not in table add it
      table.add(dict);
    }
  }

  /**
   * gets the key at the associated index position
   * @param index the location where you want the key
   * @return a key of type string
   */
  public String getKey(int index) {
    // get element at position index
    Association<String, FrequencyList> dict = table.get(index);
    return dict.getKey();
  }

  /**
   * Function to get the size of the table
   * @return the length of the table
   */
  public int length() {
    return table.size();
  }

  /**
   * If key is in the table, return one of the characters from
   * its FrequencyList with probability equal to its relative frequency
   * Otherwise, return null
   * @param key is the k-letter sequence whose frequencies we want to use
   * @return a character selected from the corresponding FrequencyList
   */
  public String choose(String key) {
    // initialise new dict entry
    // create FrequencyList object and add value
    FrequencyList list = new FrequencyList();
    Association<String, FrequencyList> dict = new Association<String, FrequencyList>(key, list);
    // check if key already in table
    int index = table.indexOf(dict);
    if (index != -1) {
      // already in the table grab it
      dict = table.get(index);
      return dict.getValue().choose();
    } else {
      // if not in table return null
      return null;
    }
  }

  /**
   * Produce a string representation of the Table
   * @return a String representing this Table
   */
  public String toString() {
    String sequence = ""; // to hold the object
    // get each element and convert its key and value to Strings
    for (int i = 0; i < table.size(); i++) {
      sequence += table.get(i).getKey() + " -> " + table.get(i).getValue().toString() + "\n";
    }
    return sequence;
  }

  // Use main to test your Table class
  public static void main(String[] args) {
    Table tbl = new Table();
    tbl.add("abc", "e");
    tbl.add("abc", "e");
    tbl.add("abc", "e");
    tbl.add("abc", "f");
    tbl.add("abc", "e");
    tbl.add("abd", "e");
    tbl.add("bcd", "b");

    System.out.println(tbl.toString());
    System.out.println(tbl.getKey(1));
    System.out.println(tbl.length());
  }

}
