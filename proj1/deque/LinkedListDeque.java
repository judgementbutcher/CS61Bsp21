package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> , Iterable<T>{
    private int size;
    private DeNode sentinel;

    public class DeNode {
        public DeNode prev;
        public T item;
        public DeNode next;

        public DeNode(T item) {
             this.item = item;
             prev = next = null;
        }
        public DeNode() {
            prev = next = null;
        }
    }

    private class LinkedListIterator implements Iterator<T>{
        private int wizPos;

        public LinkedListIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
           T returnItem = get(wizPos);
           wizPos += 1;
           return returnItem;
        }
    }


    public LinkedListDeque() {
         sentinel = new DeNode();
         sentinel.prev = sentinel;
         sentinel.next = sentinel;
         size = 0;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return get(sentinel.next, index);
    }

    /** 在callee中包含了越界的判断，所以这里只需要关注index的变化 */
    private T get(DeNode t, int index) {
       if (index == 0) {
           return t.item;
       }
       return get(t.next, index);
    }


    public void addFirst(T item) {
        DeNode newNode = new DeNode(item);
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next = newNode;
        //这里说明是加入第一个节点
        if (sentinel.prev == sentinel) {
            sentinel.prev = newNode;
        }
        size++;
    }

    public void addLast(T item) {
        DeNode newNode = new DeNode(item);
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        newNode.next = sentinel;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        DeNode current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        //是否需要清空删除项的指针
        T item = sentinel.next.item;
        DeNode toRemove = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        //清空被删除节点的指针，便于gc回收
        toRemove.prev = null;
        toRemove.next = null;
        size--;
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.prev.item;
        DeNode toRemove = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        //清空被删除节点的指针，便于gc回收
        toRemove.prev = null;
        toRemove.next = null;
        size--;
        return item;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        DeNode current = sentinel.next;
        while (current != sentinel && index > 0) {
           current = current.next;
           index--;
        }
        return current.item;
    }


    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) {
            return false;
        }
        //判断o是否是一个Deque
        if(!(o instanceof Deque)) {
            return false;
        }
        //下面开始比较内容
        Deque<T> other = (Deque<T>) o;
        if (other.size() != this.size()) {
            return false;
        }
        for(int i = 0;i < this.size();i++) {
            if(this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }
}
