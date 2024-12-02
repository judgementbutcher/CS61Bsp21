//package deque;
//
//import java.util.Comparator;
//import java.util.Iterator;
//
//public class MaxArrayDeque<T> extends ArrayDeque<T> implements Iterable<T> {
//    Comparator<T> comparator;
//
//
//    /** creates a MaxArrayDeque with the given Comparator*/
//    public MaxArrayDeque(Comparator<T> c) {
//        super();
//        comparator = c;
//    }
//
//    /** returns the maximum element in the deque as governed by the previously
//     * given Comparator. If the MaxArrayDeque is empty, simply return null.*/
//    public T max() {
//        int len = size();
//        int maxDex = 0;
//        for(int i = 0;i < len;i++) {
//           if(comparator.compare(get(maxDex), get(i)) < 0) {
//                maxDex = i;
//           }
//        }
//        return get(maxDex);
//    }
//
//    /** returns the maximum element in the deque as governed by the para
//     * meter Comparator c. If the MaxArrayDeque is empty, simply return null.*/
//    public T max(Comparator<T> c) {
//        int len = size();
//        int maxDex = 0;
//        for(int i = 0;i < len;i++) {
//            if(c.compare(get(maxDex), get(i)) < 0) {
//                maxDex = i;
//            }
//        }
//        return get(maxDex);
//    }
//
//}
