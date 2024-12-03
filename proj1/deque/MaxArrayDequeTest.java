package deque;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void maxValueTest() {
        MaxArrayDeque<Integer> maxDeque = new MaxArrayDeque<>(maxComparator);
        int [] values = {3,1,4,1,5,9,2,6,5,3};
        for(int value: values) {
            maxDeque.addLast(value);
        }
        int maxValue = maxDeque.max();
        assertEquals(9, maxValue);
        int minValue = maxDeque.max(minComparator);
        assertEquals(1, minValue);
    }

    //这里是利用lambda表达式定义一个functor
    Comparator<Integer> maxComparator = (o1, o2) -> {
        if (o1 == null || o2 == null) return 0;
        return Integer.compare(o1,o2);
    };

    Comparator<Integer> minComparator = (o1, o2) -> {
        if(o1 == null || o2 == null) return 0;
        return Integer.compare(o2,o1);
    };

}


