package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private final int LITTLE_SIZE = 15;
    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int current;

        ArrayDequeIterator() {
            current = increment(nextFirst);
        }

        public boolean hasNext() {
            return current != nextLast;
        }

        public T next() {
            T value = items[current];
            current = increment(current);
            return value;
        }
    }

    /** helper function for array index increment */
    private int increment(int index) {
        int len = items.length;
        return (index + 1) % len;
    }

    /** helper function for array index decrement */
    private int decrement(int index) {
        int len = items.length;
        if (index == 0) {
            return len - 1;
        }
        return index - 1;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = increment(nextFirst), j = 0; j < size;
                i = increment(i), j++) {
            newItems[j] = items[i];
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = decrement(nextFirst);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = increment(nextLast);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = increment(nextFirst), j = 0; j < size; i = increment(i), j++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size < items.length / 4 && items.length > LITTLE_SIZE) {
            resize(items.length / 4);
        }
        if (size == 0) {
            return null;
        }
        nextFirst = increment(nextFirst);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (size < items.length / 4 && items.length > LITTLE_SIZE) {
            resize(items.length / 4);
        }
        if (size == 0) {
            return null;
        }
        nextLast = decrement(nextLast);
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int tIndex = increment(nextFirst);
        //既然是下标，只要在size范围内就肯定能返回一个值
        while (index > 0) {
            index--;
            tIndex = increment(tIndex);
        }
        return items[tIndex];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (other.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!(get(i).equals(other.get(i)))) {
                return false;
            }
        }
        return true;
    }

}
