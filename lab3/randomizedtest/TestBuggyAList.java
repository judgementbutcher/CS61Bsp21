package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> list_known = new AListNoResizing<>();
        BuggyAList<Integer> list = new BuggyAList<>();
        for(int i = 1; i <= 3; i++) {
           list.addLast(i);
           list_known.addLast(i);
           assertEquals(list.getLast(), list_known.getLast());
        }
        for(int i = 1; i <= 3; i++) {
           assertEquals(list.removeLast(), list_known.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> tList = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
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
                //getLast
                int size = L.size();
                if(size != 0) {
                   int val = L.getLast();
                   int val_t = tList.getLast();
                   assertEquals(val, val_t);
                   System.out.println("getLast(): " + val);
                }
            } else if(operationNumber == 3) {
                //removeLast
                int size = L.size();
                if(size != 0) {
                    int val = L.removeLast();
                    int val_t = tList.removeLast();
                    assertEquals(val, val_t);
                    System.out.println("removeLast(): " + val);
                }
            }
        }
    }
}
