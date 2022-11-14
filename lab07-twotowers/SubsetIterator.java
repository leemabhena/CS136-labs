
// Iam the sole author of code in this repository
import structure5.*;

public class SubsetIterator<E> extends AbstractIterator<Vector<E>> {
    /**
     * Vector to iterate over
     */
    protected Vector<E> data;

    /**
     * Points to the current value, starts from 0 to 2^n - 1
     */
    protected long current;

    /**
     * Construct a new Subset iterator
     * @param vector is the vector to iterate over.
     */
    public SubsetIterator(Vector<E> vector) {
        data = vector;
        reset(); // initialize current to 0
    }

    /**
     * Function to reset current pointer.
     * @post Current pointer is set to zero.
     */
    public void reset() {
        // set current to start of list, ie 0.
        current = 0;
    }

    /**
     * Function that returns the next subset in iteration.
     * @return Returns the next subset.
     * @pre hasNext is true
     * @post returns the next subset and increments current pointer.
     */
    public Vector<E> next() {
        Assert.pre(hasNext(), "There is a next subset");
        // get & return the current subset, increment count
        Vector<E> currentSubset = get();
        current++;
        return currentSubset;
    }

    /**
     * Function to check if there are subsets left in each iteration.
     * @return Returns true iff there is subset left, otherwise false.
     * @post returns true if there is still a subset left and false otherwise.
     */
    public boolean hasNext() {
        long total = (1L << data.size()) - 1; // total = 2^n - 1
        // as long as current is < TOTAL it is a reasonable set
        return current <= total;
    }

    /**
     * Function to get the current subset.
     * @return Returns the current subset.
     * @post Returns the current subset.
     */
    public Vector<E> get() {
        Vector<E> subset = new Vector<>(); // subset to return
        long whichBit; // the bit to compare to.
        // for every element in data,
        for (int i = 0; i < data.size(); i++) {
            whichBit = 1L << i; // update the bit to compare to on each iteration
            if ((current & whichBit) == whichBit) {
                // if bit is 1, include the element at that position in the subset
                subset.add(data.get(i));
            }
        }
        return subset;
    }

}
