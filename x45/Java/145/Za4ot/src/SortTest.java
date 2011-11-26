import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 21.05.2010
 * Time: 14:04:22
 */
public class SortTest {

    @Test
    public void testBubbleSortInteger1() throws Exception {
        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                if (a < b)
                    return -1;
                if (a == b)
                    return 0;
                return 1;
            }
        };

        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(0, 5);
        arr.add(1, 4);
        arr.add(2, 3);
        arr.add(3, 2);
        arr.add(4, 1);

        Sort<Integer> s = new Sort<Integer>();
        s.BubbleSort(arr, comp);
        assertEquals(s.result(arr), "1 2 3 4 5 ");
    }

    @Test
    public void testBubbleSortInteger() throws Exception {
        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                if (a < b)
                    return -1;
                if (a == b)
                    return 0;
                return 1;
            }
        };

        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(0, 2);
        arr.add(1, 1);
        arr.add(2, 5);
        arr.add(3, 4);
        arr.add(4, 10);
        Sort<Integer> s = new Sort<Integer>();
        s.BubbleSort(arr, comp);
        assertEquals(s.result(arr), "1 2 4 5 10 ");
    }

    @Test
    public void testBubbleSortString() throws Exception {
        Comparator<String> comp = new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.charAt(0) < b.charAt(0))
                    return -1;
                if (a.charAt(0) == b.charAt(0))
                    return 0;
                return 1;
            }
        };

        Sort<String> s = new Sort<String>();
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(0, "dima");
        arr.add(1, "yura");
        arr.add(2, "vova");
        arr.add(3, "pa6a");
        arr.add(4, "vit9");
        s.BubbleSort(arr, comp);
        assertEquals(s.result(arr), "dima pa6a vova vit9 yura ");
    }

    @Test
    public void testBubbleSortString1() throws Exception {
        Comparator<String> comp = new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.charAt(0) < b.charAt(0))
                    return -1;
                if (a.charAt(0) == b.charAt(0))
                    return 0;
                return 1;
            }
        };

        Sort<String> s = new Sort<String>();
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(0, "dima");
        s.BubbleSort(arr, comp);
        assertEquals(s.result(arr), "dima ");
    }

    @Test
    public void testEmptyList() throws Exception {
        Comparator<String> comp = new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.charAt(0) < b.charAt(0))
                    return -1;
                if (a.charAt(0) == b.charAt(0))
                    return 0;
                return 1;
            }
        };

        Sort<String> s = new Sort<String>();
        ArrayList<String> arr = new ArrayList<String>();
        s.BubbleSort(arr, comp);
        assertEquals(s.result(arr), "");
    }


}
