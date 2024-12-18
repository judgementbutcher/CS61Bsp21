package deque;

import java.util.Iterator;

public interface Deque<T> {
    /** Adds an item of type T to the front of the deque*/
    void addFirst(T item);
    /** Adds an item of type T to the back of the deque*/
    void addLast(T item);
    /** Returns true if deque is empty, false otherwise */
    default boolean isEmpty() {
        return size() == 0;
    }
    /** Returns the number of items in the deque. */
    int size();
    /** Prints the items in the deque from first to last,
     * seperated by a space. Once all the items have been
     * printed, print out a new line
     * */
    void printDeque();
    /** Removes and returns the item at the front of the deque.
     * If no such item exits, returns null
     * */
    T removeFirst();
    /** Removes and returns the item at the back of the deque.
     * If no such item exits, returns null*/
    T removeLast();
    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null, Must not alter the deque
     * */
    T get(int index);
    Iterator<T> iterator();
    /** Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the
     * same contens(as goverened by the T's equals method) in the
     * same order.
     * */
    boolean equals(Object o);
}
