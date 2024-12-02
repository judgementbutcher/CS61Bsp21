package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void testThreeAddThreeRemove() {
        LinkedListDeque<Integer> list_known = new LinkedListDeque<>();
        ArrayDeque<Integer> list = new ArrayDeque<>();
        for(int i = 1; i <= 3; i++) {
            list.addLast(i);
            list_known.addLast(i);
            assertEquals(list.get(i), list_known.get(i));
        }
        for(int i = 1; i <= 3; i++) {
            assertEquals(list.removeLast(), list_known.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> tList = new ArrayDeque<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                tList.addLast(randVal);
                assertEquals(L.size(), tList.size());
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size_t = tList.size();
                assertEquals(size, size_t);
                System.out.println("size: " + size);
            } else if(operationNumber == 2) {
                //removeLast
                int size = L.size();
                if (size != 0) {
                    int val = L.removeLast();
                    int val_t = tList.removeLast();
                    assertEquals(val, val_t);
                    System.out.println("removeLast(): " + val);
                }

            }
        }
    }
}
