public class BinaryTreeNode<T> {
    protected T value;
    protected BinaryTreeNode<T> left;
    protected BinaryTreeNode<T> right;

    /**
     * Constructs a BinaryTreeNode with the given value.
     * @param value  A value.
     */
    public BinaryTreeNode(T value) {
	this.value = value;
    }

    /**
     * Get the value.
     */
    public T get() {
	return value;
    }

    /**
     * Gets the left subtree.
     */
    public BinaryTreeNode<T> getLeft() {
	return left;
    }

    /**
     * Gets the right subtree.
     */
    public BinaryTreeNode<T> getRight() {
	return right;
    }

    /**
     * Sets the left subtree.
     * @param t  A tree.
     */
    public void setLeft(BinaryTreeNode<T> t) {
	left = t;
    }

    /**
     * Sets the right subtree.
     * @param t  A tree.
     */
    public void setRight(BinaryTreeNode<T> t) {
	right = t;
    }


    /**
     * Computes the number of nodes in the tree.
     */
    public int size() {
	int l = left != null ? left.size() : 0;
	int r = right != null ? right.size() : 0;
	return 1 + l + r;
    }

    /**
     * Returns a string representation of the tree.
     */
    public String toString() {
	// (v l r)
	if (left == null && right == null) {
	    return value.toString();
	}
	String l = left != null ? left.toString() : "<e>";
	String r = right != null ? right.toString() : "<e>";
	return "(" + value.toString() + " " + l + " " + r + ")";
    }

    /**
     * Returns the edge height of the given tree.
     * @param t  A tree or null for the empty tree.
     */
    public static <E> int getHeight(BinaryTreeNode<E> t) {
	if (t == null) {
	    return -1;
	}
	int lh = getHeight(t.getLeft());
	int rh = getHeight(t.getRight());
	return lh > rh ? lh + 1 : rh + 1;
    }
}