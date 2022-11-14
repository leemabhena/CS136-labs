
/**
 * Class to calculate the diff between two files
 * */

import structure5.*;
import java.util.Scanner;

/**
 * Class to calculate the diff between two files
 */
public class Diff {

	// original file
	protected Vector<String> file1;
	// new version of the file
	protected Vector<String> file2;

	/**
	 * Constructor for diff
	 * @param file1Name is the path to the original file
	 * @param file2Name is the path to the new version of the file
	 */
	public Diff(String file1Name, String file2Name) {
		file1 = readInFile(file1Name);
		file2 = readInFile(file2Name);
	}

	/**
	 * Reads in the fine given by fileName.
	 * Note that this method requires Java 11.
	 * @param fileName name of the file
	 * @return each line of the file as elements of a Vector
	 */
	protected Vector<String> readInFile(String fileName) {
		Vector<String> ret = new Vector<String>();
		Scanner sc = new Scanner(new FileStream(fileName));
		while (sc.hasNext()) {
			ret.add(sc.nextLine());
		}
		return ret;
	}

	/**
	 * toString method
	 * @return the concatenation of the two files
	 */
	public String toString() {
		String ret = "-----\nFile 1:\n-----\n";
		for (String line : file1) {
			ret += line + "\n";
		}
		ret += "-----\nFile2:\n-----\n";
		for (String line : file2) {
			ret += line + "\n";
		}
		return ret;
	}

	/**
	 * Finds the diff of two files
	 * @pre file1 and file2 are strings holding the
	 *      files we want to compare
	 * @post the diff is printed to the terminal
	 */
	public void findDiff() {
		System.out.println(diffHelper(0, 0).getValue());
	}

	/**
	 * The recursive helper method for calculating the diff
	 * @pre remainingFile1, remainingFile2, and table are not null
	 * @param remainingFile1Index the first line of file 1 not yet diffed
	 * @param remainingFile2Index the first line of file 2 not yet diffed
	 * @return An association corresponding to the diff between remainingFile1
	 *         and remainingFile2. The key is the cost of the diff (number of
	 *         changes
	 *         necessary). The value is the diff output.
	 */
	public Association<Integer, String> diffHelper(int remainingFile1Index, int remainingFile2Index) {
		// base case: one file has no remaining lines
		if (file1.size() == remainingFile1Index || file2.size() == remainingFile2Index) {
			return diffBaseCase(remainingFile1Index, remainingFile2Index);
		}

		// check if first lines match
		Association<Integer, String> equalAssoc = null;
		if (file1.get(remainingFile1Index).equals(file2.get(remainingFile2Index))) {
			equalAssoc = diffHelper(remainingFile1Index + 1, remainingFile2Index + 1);
		}
		// calculate the cost of removing a line from file1, store solution in an assoc
		Association<Integer, String> file1Assoc = diffHelper(remainingFile1Index + 1, remainingFile2Index);
		int file1Cost = file1Assoc.getKey() + 1; // add 1 to the cost

		// calculate the cost of removing a line from file2, store solution in an assoc
		Association<Integer, String> file2Assoc = diffHelper(remainingFile1Index, remainingFile2Index + 1);
		int file2Cost = file2Assoc.getKey() + 1; // add 1 to the cost

		// calculate the minimum between the three associations
		if (equalAssoc != null && equalAssoc.getKey() < file1Cost && equalAssoc.getKey() < file2Assoc.getKey()) {
			return equalAssoc;
		} else if (file2Cost < file1Cost) { // return file 2
			return new Association<Integer, String>(file2Cost, differences(file2Assoc, remainingFile2Index, 2));
		} else { // return file 1
			return new Association<Integer, String>(file1Cost, differences(file1Assoc, remainingFile1Index, 1));
		}
	}

	/**
	 * Function to check if one of the files is empty and calculates cost for the
	 * remaining file
	 * @pre remainingFile1, remainingFile2, and table are not null
	 * @param remainingFile1Index the first line of file 1 not yet diffed
	 * @param remainingFile2Index the first line of file 2 not yet diffed
	 * @return returns an association of cost and a string of actual differences.
	 * @post returns an association of cost and a string of actual differences.
	 */
	protected Association<Integer, String> diffBaseCase(int remainingFile1Index, int remainingFile2Index) {
		// base case: one file has no remaining lines
		if (file1.size() == remainingFile1Index) { // print everything in file 2
			String str = ""; // the string of differences
			int cost = 0; // cost of adding remaining items
			// add remaining items in file 1 from the remaining index to end
			for (int i = remainingFile2Index; i < file2.size(); i++) {
				str += " > " + String.valueOf(i + 1) + ": " + file2.get(i) + "\n";
				cost += 1;
			}
			return new Association<Integer, String>(cost, str); // return assoc of cost and str

		} else { // print everything in file 1
			String str = ""; // the string of differences
			int cost = 0; // cost of adding remaining items
			// add remaining items in file 1 from the remaining index to end
			for (int i = remainingFile1Index; i < file1.size(); i++) {
				str += " < " + String.valueOf(i + 1) + ": " + file1.get(i) + "\n";
				cost += 1;
			}
			return new Association<Integer, String>(cost, str); // return assoc of cost and str
		}
	}

	/**
	 * Function that generate a string of differences between files
	 * @param assoc The assoc to add the current removed value to
	 * @param index The line about to be removed in the files
	 * @param which the file to update
	 * @return returns a string of differences between files
	 * @pre assoc is not null
	 * @post returns a string of differences between files
	 */
	protected String differences(Association<Integer, String> assoc, int index, int which) {
		Assert.pre(assoc != null, "Assoc is not equal null");
		if (which == 2) { // if updating file 2
			// current file text to add, add it first before the base case results
			String fileText = " > " + String.valueOf(index + 1) + ": " + file2.get(index);
			String txt = fileText + "\n" + assoc.getValue();
			// return the txt
			return txt;
		} else { // updating the first file
			// current file text to add, add it first before the base case results
			String fileText = " < " + String.valueOf(index + 1) + ": " + file1.get(index);
			String txt = fileText + "\n" + assoc.getValue();
			// return the txt
			return txt;
		}

	}

	/**
	 * main method: two command line arguments; the first is the original file,
	 * the second is the new version to be compared to.
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: java Diff <file1> <file2>");
			System.exit(1);
		}
		Diff diff = new Diff(args[0], args[1]);
		diff.findDiff();
	}
}
