
// lam the sole author of the work in this repository.
import structure5.*;
import java.util.Comparator;

public class MyVector<E> extends Vector<E> {

    /**
     * This constructor initializes the protected fields of the parent class.
     */
    public MyVector() {
        // initialize protected fields of parent class.
        super();
    }

    /**
     * This function uses insertion sort to sort the vector,
     * as specified by the comparator.
     * @param c comparator object to use for sorting.
     * @pre c is a valid comparator
     * @post vector is sorted using inplace insertion sort in order determined by c.
     */
    public void sort(Comparator<E> c) {
        int numSorted = 1; // keeps size of sorted vector
        while (numSorted < size()) {
            // this is the next unsorted value; copy it
            E temp = get(numSorted);
            // find where to insert it, shifting other values
            // over if necessary
            int i; // count variable to keep track of where to insert next sorted value
            for (i = numSorted; i > 0; i--) {
                if (c.compare(temp, get(i - 1)) < 0) {
                    set(i, get(i - 1)); // insert next to sorted vector
                } else {
                    break; // if already sorted break,
                }
            }
            // insert unsorted value into correct place
            set(i, temp);
            numSorted++;
        }
    }
}
