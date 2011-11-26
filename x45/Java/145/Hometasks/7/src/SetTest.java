import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: Volkman
 * Date: 30.03.2010
 * Time: 22:58:18
 */
public class SetTest {
    Set<Integer> set = new Set<Integer>();
    Set<Integer> set1 = new Set<Integer>();

    @Test
    public void testAddAndContains() throws Exception {
        set.add(10);
        assertTrue(set.contains(10));
        assertFalse(set.contains(1));
    }

    @Test
    public void testRemoveAndContains() throws Exception {
        set.add(10);
        set.add(1);
        set.remove(1);
        assertTrue(set.contains(10));
        assertFalse(set.contains(1));
    }

    @Test
    public void testIntersection() throws Exception {
        set.add(2);
        set.add(4);
        set.add(5);
        set.add(7);
        set1.add(2);
        set1.add(4);
        set1.add(6);
        assertEquals("2 4 ", set.intersection(set1).print());
    }

    @Test
    public void testUnion() throws Exception {
        set.add(2);
        set.add(4);
        set.add(5);
        set.add(7);
        set1.add(2);
        set1.add(4);
        set1.add(6);
        assertEquals("2 4 5 7 6 ", set.union(set1).print());
    }
}
