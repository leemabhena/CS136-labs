/**

We are the sole authors of the work in this repository.

* Implementation of lists, using doubly linked elements, and dummy nodes.
* Starter class for "9.10 Laboratory: Lists with Dummy Nodes"
* Please modify this code by following the directions in  on page 216 of
* Java Structures sqrt(7) edition by Duane Bailey.
*/

import structure5.*;
import java.util.Iterator;

public class LinkedList<E> extends DoublyLinkedList<E> {

	// Use these variables inherited from DoublyLinkedList
	// Do not uncomment this!  Just use the variables as if they were uncommented
	/**
	* Number of elements within the list.
	*	protected int count;
	*/

	/**
	* Reference to head of the list.
	*
	protected DoublyLinkedNode<E> head;
	*/

	/**
	* Reference to tail of the list.
	*
	protected DoublyLinkedNode<E> tail;
	*/


	/**
	* Constructs an empty list.
	* @post constructs an empty list
	* constant time, not looping over anything
	*/
	public LinkedList() {
		// Head and tail are initialized with null values
		// Each point to each other; head has no previous, tail has no next

		head = new DoublyLinkedNode<E>(null, tail, null);
		tail = new DoublyLinkedNode<E>(null, null, head);

		count = 0; // the list is empty
	}

	/**
	* Determine the number of elements in the list.
	*
	* @post returns the number of elements in list
	*
	* @return The number of elements found in the list.
	*/


	/**
	A helper function to find the index of a particular node
	* @param index is the position of the node to select
	* @return returns the node at specified index.
	* @pre the index isn't out of range
	* @post returns node at specified index.
	* O(N), might have to iterate through the whole list
	*/
	protected DoublyLinkedNode<E> getNodeAtIndex(int index) {
		Assert.pre((0 <= index) && (index <= size()), "Index out of range."); // index >=0 but less than size

		DoublyLinkedNode<E> next = head.next(); // start at the first non-head element
		for (int i = 0; i < index; i++) { // iterate through the list until index is reached
			next = next.next();
		}
		return next; // return the node that is arrived at
	}

	/**
	* just returns the size. All other functions that change list size increment count
	* @return returns the length of the list.
	* constant time, literally just returns a single value.
	*/
	public int size() {
		return count;
	}

	/**
	* Determine if the list is empty.
	*
	* @post returns true iff the list has no elements.
	*
	* @return True iff list has no values.
	* constant time, just evaluates and returns a single boolean statement
	*/
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	* Remove all values from list.
	*
	* @post removes all the elements from the list
	* constant time, just sets a couple of values.
	*/
	public void clear() {
		// head to point to tail and tail to head
		head.setNext(tail);
		tail.setPrevious(head);
		count = 0; // reset count back to 0
	}

	/**
	* A private routine to add an element after a node.
	* @param value the value to be added
	* @param previous the node the come before the one holding value
	* @pre previous is non null
	* @post list contains a node following previous that contains value
	* constant time, just changing where a couple of object attributes point
	*/
	protected void insertAfter(E value, DoublyLinkedNode<E> previous) {
		Assert.pre(previous != null, "Previous is null"); // previous != null
		// call NEXT the one that previous used to point to
		// now, PREV points to NEW
		// NEW points to next
		// NEXT points back to NEW
		// and NEW points back to PREV

		DoublyLinkedNode<E> next = previous.next();

		DoublyLinkedNode<E> newNode = new DoublyLinkedNode<E>(value, next, previous);
		previous.setNext(newNode);
		next.setPrevious(newNode);

		count++;

	}

	/**
	* A private routine to remove a node.
	* @param node the node to be removed
	* @pre node is non null
	* @post node node is removed from the list
	* @post returns the value of the node removed
	* @return the value of the node removed
	* constant time, just changes where a few things point
	*/
	protected E remove(DoublyLinkedNode<E> node) {
		Assert.pre(node != null, "Node is null");
		node.previous().setNext(node.next()); // previous skips removed node, pointing to the node after it
		node.next().setPrevious(node.previous()); // next skips removed node, pointing to the node before it
		// decrement count
		count--;
		return node.value();
	}


	/**
	* Add a value to the head of the list.
	*
	* @param value The value to be added.
	* @pre value is not null
	* @post adds element to head of list
	* constant time, just like insertAfter
	*/
	public void addFirst(E value) {
		Assert.pre(value != null, "Value is null");
		insertAfter(value, head); // Since we know where the head is, just a special case of insertAfter
	}

	/**
	* Add a value to the tail of the list.
	*
	* @param value The value to be added.
	* @pre value is not null
	* @post adds new value to tail of list
	* constant time, just like insertAfter
	*/
	public void addLast(E value) {
		Assert.pre(value != null, "Value is null");
		insertAfter(value, tail.previous()); // Since we know where the tail is, just a special case of insertAfter
	}

	/**
	* Remove a value from the head of the list.
	* Value is returned.
	*
	* @pre list is not empty
	* @post removes first value from list
	*
	* @post Returns the value removed from the list.
	* @return The value removed from the list.
	* contant time, just like remove
	*/
	public E removeFirst() {
		Assert.pre(!isEmpty(), "List is empty.");
		return remove(head.next()); // Since we know where the head is, just a special case of remove

	}

	/**
	* Remove a value from the tail of the list.
	*
	* @pre list is not empty
	* @post removes value from tail of list
	* @post Returns the value removed from the list.
	*
	* @return The value removed from the list.
	* contant time, just like remove
	*/
	public E removeLast() {
		Assert.pre(!isEmpty(), "List is empty.");
		return remove(tail.previous()); // Since we know where the tail is, just a special case of remove

	}

	/**
	* Get a copy of the first value found in the list.
	*
	* @pre list is not empty
	* @post returns first value in list.
	*
	* @return A reference to first value in list.
	* constant time, just asking where a couple of things point
	*/
	public E getFirst() {
		Assert.pre(!isEmpty(), "List is empty.");
		return head.next().value(); // We always know where the head is, the first element is right after that
	}

	/**
	* Get a copy of the last value found in the list.
	*
	* @pre list is not empty
	* @post returns last value in list.
	*
	* @return A reference to the last value in the list.
	* constant time, just asking where a couple of things point
	*/
	public E getLast() {
		Assert.pre(!isEmpty(), "List is empty.");
		return tail.previous().value(); // We always know where the tail is, the last element i the one right before it
	}

	/**
	* Insert the value at location.
	*
	* @pre 0 <= i <= size()
	* @post adds the ith entry of the list to value o
	* @param i the index of this new value
	* @param o the the value to be stored
	* O(n), because of getNodeAtIndex
	*/
	public void add(int i, E o) {
		Assert.pre((0 <= i) && (i <= size()), "Index out of range.");
		DoublyLinkedNode<E> next = getNodeAtIndex(i); // finds what's currently at the index
		insertAfter(o, next.previous()); // slots that new node into that spot

	}

	/**
	* Remove and return the value at location i.
	*
	* @pre 0 <= i < size()
	* @post removes and returns the object found at that location.
	*
	* @param i the position of the value to be retrieved.
	* @return the value retrieved from location i (returns null if i invalid)
	* O(n), like getNodeAtIndex
	*/
	public E remove(int i) {
		Assert.pre((0 <= i) && (i < size()), "Index out of range.");
		if ((0 <= i) && (i < size())) {
			DoublyLinkedNode<E> next = getNodeAtIndex(i); // finds and removes the node
			return remove(next); // the real work is done in helper getNodeAtindex
		}
		return null;

	}

	/**
	* Get the value at location i.
	*
	* @pre 0 <= i < size()
	* @post returns the object found at that location.
	*
	* @param i the position of the value to be retrieved.
	* @return the value retrieved from location i (returns null if i invalid)
	* O(n), like getNodeAtIndex
	*/
	public E get(int i) {
		Assert.pre((0 <= i) && (i < size()), "Index out of range.");
		if ((0 <= i) && (i < size())) {
			DoublyLinkedNode<E> next = getNodeAtIndex(i);
			return next.value(); // basically does what getNodeAtIndex does, but returns value instead of a node
		}

		return null;

	}

	/**
	* Set the value stored at location i to object o, returning the old value.
	*
	* @pre 0 <= i < size()
	* @post sets the ith entry of the list to value o, returns the old value.
	* @param i the location of the entry to be changed.
	* @param o the new value
	* @return the former value of the ith entry of the list.
	* O(n) like getNodeAtIndex
	*/
	public E set(int i, E o) {
		Assert.pre((0 <= i) && (i < size()), "Index out of range.");
		DoublyLinkedNode<E> next = getNodeAtIndex(i); // finds the current node
		DoublyLinkedNode<E> old = next; // saves the value of the current node, to return later

		next.setValue(o); // swaps out the value for the new one

		return old.value();

	}

	/**
	* Determine the first location of a value in the list.
	*
	* @pre value is not null
	* @post returns the (0-origin) index of the value,
	*   or -1 if the value is not found
	*
	* @param value The value sought.
	* @return the index (0 is the first element) of the value, or -1
	* O(n), worst case has to iterate through the entire list
	*/
	public int indexOf(E value) {
		Assert.pre(value != null, "Value is null");
		DoublyLinkedNode<E> next = head.next(); // starts at the first entry

		for (int i = 0; i < size(); i++) { // iteratures through, checks values
			if (next.value().equals(value)) { // if value matches, returns the index
				return i;
			}
			next = next.next(); // goes to the next node
		}
		return -1;

	}

	/**
	* Determine the last location of a value in the list.
	*
	* @pre value is not null
	* @post returns the (0-origin) index of the value,
	*   or -1 if the value is not found
	*
	* @param value the value sought.
	* @return the index (0 is the first element) of the value, or -1
	* O(n), iterates through the whole thing in the worst case
	*/
	public int lastIndexOf(E value) {
		Assert.pre(value != null, "Value is null");
		DoublyLinkedNode<E> previous = tail.previous(); // starts at the end

		for (int i = size() - 1; i >= 0; i--) { // iterates backwards. This whole thing is just indexOf but backwards.
			if (previous.value().equals(value)) {
				return i;
			}
			previous = previous.previous();
		}
		return -1;

	}

	/**
	* Check to see if a value is within the list.
	*
	* @pre value not null
	* @post returns true iff value is in the list
	*
	* @param value A value to be found in the list.
	* @return True if value is in list.
	* worst case O(n) like indexOf, just evaluates a boolean on top of that
	*/
	public boolean contains(E value) {
		Assert.pre(value != null, "Value is null");
		return (indexOf(value) != -1); // indexOf does the real work here

	}

	/**
	* Remove a value from the list.  At most one value is removed.
	* Any duplicates remain.  Because comparison is done with "equals,"
	* the actual value removed is returned for inspection.
	*
	* @pre value is not null.  List can be empty.
	* @post first element matching value is removed from list
	*
	* @param value The value to be removed.
	* @return The value actually removed.
	* worst case O(n), like indexOf
	*/
	public E remove(E value) {
		Assert.pre(value != null, "Value is null");
		int index = indexOf(value); // goes looking for an entry with that value. Starts at the front of the list
		if (index != -1) { //  if it finds a node with matching value, removes it
			return remove(index);
		}
		return null;

	}

	/**
	* Construct an iterator to traverse the list.
	*
	* @post returns iterator that allows the traversal of list.
	*
	* @return An iterator that traverses the list from head to tail.
	*/
	public Iterator<E> iterator() {
		/**
		* into your list implementation, please toggle the
		* comments below. To understand why the lines below
		* must be swapped, please consult the structure5
		* source code for DoublyLinkedListIterator class.
		*/

		return new DoublyLinkedListIterator<E>(head, tail);
		// return new DoublyLinkedListIterator<E>(head);
	}

	/**
	* Construct a string representation of the list.
	*
	* @post returns a string representing list
	*
	* @return A string representing the elements of the list.
	*/
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("<LinkedList (" + count + "):");

		Iterator<E> li = iterator();
		while (li.hasNext()) {
			s.append(" " + li.next());
		}
		s.append(">");

		return s.toString();
	}
}
