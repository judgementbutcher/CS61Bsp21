package deque;

import java.util.Iterator;

public interface Deque<T> {
    /** Adds an item of type T to the front of the deque*/
    public void addFirst(T item);
    /** Adds an item of type T to the back of the deque*/
    public void addLast(T item);
    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty();
    /** Returns the number of items in the deque. */
    public int size();
    /** Prints the items in the deque from first to last,
     * seperated by a space. Once all the items have been
     * printed, print out a new line
     * */
    public void printDeque();
    /** Removes and returns the item at the front of the deque.
     * If no such item exits, returns null
     * */
    public T removeFirst();
    /** Removes and returns the item at the back of the deque.
     * If no such item exits, returns null*/
    public T removeLast();
    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null, Must not alter the deque
     * */
    public T get(int index);
    public Iterator<T> iterator();
    /** Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the
     * same contens(as goverened by the T's equals method) in the
     * same order.
     * */
    public boolean equals(Object o);
}
