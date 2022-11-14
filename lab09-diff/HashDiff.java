
/**
* Class to calculate the diff between two files
* */

import structure5.*;
import java.util.Scanner;

/**
 * Class to calculate the diff between two files
 */
public class HashDiff extends Diff {

	/* table is the hashtable that stores intPairs and returned association */
	protected Hashtable<IntPair, Association<Integer, String>> table;

	/**
	 * Constructor for diff
	 * @param file1Name is the path to the original file
	 * @param file2Name is the path to the new version of the file
	 */
	public HashDiff(String file1Name, String file2Name) {
		super(file1Name, file2Name);
		table = new Hashtable<>();
	}

	/**
	 * The recursive helper method for calculating the diff
	 * @pre remainingFile1, remainingFile2, and table are not null
	 * @param remainingFile1Index the first line of file 1 not yet diffed
	 * @param remainingFile2Index the first line of file 2 not yet diffed
	 * @return An association corresponding to the diff between remainingFile1 and
	 *         remainingFile2. The key is the cost of the diff (number of changes
	 *         necessary). The value is the diff output.
	 */
	public Association<Integer, String> diffHelper(int remainingFile1Index, int remainingFile2Index) {
		IntPair pair = new IntPair(remainingFile1Index, remainingFile2Index); // pair to check
		if (table.containsKey(pair)) { // if it exists return that value
			return table.get(pair);
		}
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
			IntPair equalPair = new IntPair(remainingFile1Index, remainingFile2Index);
			table.put(equalPair, equalAssoc);
			return equalAssoc;
		} else if (file2Cost < file1Cost) { // return file 2
			return diffFormatHelper(file2Cost, remainingFile1Index, remainingFile2Index, remainingFile2Index, 2,
					file2Assoc);
		} else { // return file 1
			return diffFormatHelper(file1Cost, remainingFile1Index, remainingFile2Index, remainingFile1Index, 1,
					file1Assoc);
		}
	}

	/**
	 * Function to format and add pair and association to the hash table.
	 * @param cost  cost of removing from a file
	 * @param idx1  remaining file 1 index
	 * @param idx2  remaining file 2 index
	 * @param idx   the file index to generate string for
	 * @param which the file currently working on
	 * @param assoc association of file currently working on
	 * @return association of cost and string to print
	 * @pre assoc is not null
	 * @post returns an association of cost and string to print
	 */
	protected Association<Integer, String> diffFormatHelper(int cost, int idx1, int idx2, int idx,
			int which, Association<Integer, String> assoc) {
		IntPair pair = new IntPair(idx1, idx2); // pair to add as key
		Association<Integer, String> returnedFile = new Association<Integer, String>(cost,
				differences(assoc, idx, which)); // create an association to return
		table.put(pair, returnedFile); // add to the table
		return returnedFile; // return that
	}

	/**
	 * main method: two command line arguments; the first is the original file,
	 * the second is the new version to be compared to.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java HashDiff <file1> <file2>");
			System.exit(1);
		}
		Diff diff = new HashDiff(args[0], args[1]);
		diff.findDiff();
	}
}
