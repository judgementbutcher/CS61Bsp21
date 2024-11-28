package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
       size = 0;
       items = (T[]) new Object[8];
       nextFirst = 3;
       nextLast = 4;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int current;

        public ArrayDequeIterator() {
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
        T [] newItems = (T[]) new Object[capacity];
        for(int i = increment(nextFirst), j = 0; i != nextLast;
            i = increment(i),j++) {
           newItems[j] = items[i];
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
       if(size == items.length) {
            resize(size * 2);
       }
       items[nextFirst] = item;
       nextFirst = increment(nextFirst);
    }

    public void addLast(T item) {
        if(size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = increment(nextLast);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for(int i = decrement(nextFirst); i != nextLast;i = increment(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if((size < items.length / 4) && (size > 15)) {
            resize(items.length / 2);
        }
        T value = items[increment(nextFirst)];
        nextFirst = increment(nextFirst);
        size--;
        return value;
    }

    public T removeLast() {
        if((size < items.length / 4) && (size > 15)) {
            resize(items.length / 2);
        }
        T value = items[decrement(nextLast)];
        nextLast = decrement(nextLast);
        size--;
        return value;
    }

    public T get(int index) {
        int t_index = increment(nextFirst);
        while(t_index != nextLast && index > 0) {
            t_index = increment(t_index);
            index--;
        }
        //如果该index不合法，返回null
        if(t_index == nextLast) {
            return null;
        }
        return items[t_index];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Deque)) return false;
        Deque<T> other = (Deque<T>) o;
        if(other.size() != size) return false;
        for(int i = 0; i < this.size();i++) {
            if(this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }

}
