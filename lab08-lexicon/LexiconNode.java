
// lam the sole author of work in this repository.
import structure5.*;
import java.util.Iterator;

class LexiconNode implements Comparable<LexiconNode> {

    /* single letter stored in this node */
    protected char letter;

    /* true if this node ends some path that defines a valid word */
    protected boolean isWord;

    /*
     * Vector of lexicon nodes which keeps track of the number of children of parent
     * node..
     */
    protected Vector<LexiconNode> children;

    /**
     * Creates a new lexicon object, with a given character and boolean value.
     */
    LexiconNode(char value, boolean isWordValue) {
        letter = value;
        isWord = isWordValue;
        children = new Vector<>();
    }

    /**
     * Function to compare to lexicon nodes.
     * @param o, lexicon node to compare to.
     * @return integer < 0 if current less than compared node
     *         integer > 0 if this node is greater than compared node or 0 if equal.
     * @pre o should be a valid node.
     * @post returns an integer based on which object is bigger.
     */
    public int compareTo(LexiconNode o) {
        return letter - o.letter;
    }

    /**
     * Function to locate where to add the lexicon node.
     * @param value, the lexicon node to be added
     * @return position of where the lexicon node is to be added.
     * @post position of lexicon node, ordered one.
     */
    protected int locate(LexiconNode value) {
        LexiconNode midVal;
        int lo = 0;
        int hi = children.size(); // why do you think I use v.size instead of v.size - 1?
        int mid = (lo + hi) / 2;
        // we know that we are done searching when
        // the lo and hi points cross.
        while (lo < hi) {
            // get middle value
            midVal = children.get(mid); // improve here
            // on which side of mid does value fall?
            if (midVal.compareTo(value) < 0) {
                // value is bigger than midpoint
                lo = mid + 1;
            } else {
                // value is less than or equal to midpoint
                hi = mid;
            }
            // recompute midpoint
            mid = (lo + hi) / 2;
        }
        return lo;
    }

    /**
     * Function to add LexiconNode child to correct position in child data
     * structure.
     * @param ln the lexicon node to be added as child.
     * @post Child node is added to child data structure.
     */
    public void addChild(LexiconNode ln) {
        int position = locate(ln); // compute position to add the child node.
        // check if child already exist to avoid duplicates
        boolean exists = false;
        for (LexiconNode child : getChildren()) { // avoid duplicates
            if (child.getLetter() == ln.getLetter()) {
                exists = true;
                break;
            }
        }
        // if the child does not exist add it to the children vector
        if (!exists) {
            children.add(position, ln);
        }
    }

    /**
     * Function to get LexiconNode child for 'ch' out of child data
     * structure
     * @param ch character node to get from the vector of children
     * @return returns the node if it exists otherwise null.
     * @post return the node if the child exists or null otherwise.
     */
    public LexiconNode getChild(char ch) {
        LexiconNode child = null; // if child is not there return null
        for (int i = 0; i < children.size(); i++) {
            LexiconNode currChild = children.get(i);
            if (currChild.letter == ch) { // compare the letters of the nodes
                child = currChild;
            }
        }
        return child;
    }

    /**
     * Remove LexiconNode child for 'ch' from child data structure.
     * @param ch the child node to remove from the vector of children.
     * @post ch is removed from vector of children.
     */
    public void removeChild(char ch) {
        LexiconNode child = getChild(ch);
        if (child != null) { // check if child exist, to avoid removing null values
            children.remove(child);
        }
    }

    /**
     * Iterator that iterates over children in
     * alphabetical order
     * @return returns an alphabetical iterator of all children in the parent node.
     * @post returns an alphabetical iterator of all children in the parent node.
     */
    public Iterator<LexiconNode> iterator() {
        return children.iterator(); // return the iterator of the vector
    }

    /**
     * Function to get the letter of the node.
     * @return the letter of the node.
     * @post returns the letter of the node.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Function that returns a vector of children.
     * @return returns a vector of students.
     * @post returns a vector of students.
     */
    public Vector<LexiconNode> getChildren() {
        return children;
    }

    /**
     * Function that returns a value of is word.
     * @return returns value of is word.
     * @post returns a value of is word.
     */
    public boolean isWord() {
        return isWord;
    }

    /**
     * Sets the value of isWord.
     * @param bool the new value of isWord.
     * @post isWord is updated to new value.
     */
    public void isWord(boolean bool) {
        isWord = bool;
    }

}
