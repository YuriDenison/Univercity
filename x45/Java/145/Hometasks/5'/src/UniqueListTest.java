import org.junit.Test;
import static org.junit.Assert.*;

/**
 * User: Volkman
 * Date: 21.03.2010
 * Time: 18:58:09
 */
public class UniqueListTest {
    UniqueList ulist = new UniqueList();

    @Test
    public void testAdd() throws Exception {
        ulist.add(1, 1);
        ulist.add(2, 2);
        ulist.add(3, 3);

    }

    @Test
    public void testRemove() throws Exception {
        ulist.add(1, 1);
        ulist.add(3, 3);
        ulist.remove(3);
    }

    @Test(expected = UniqueList.TwiceAddingException.class)
    public void testAdd1() throws Exception {
        ulist.add(1, 1);
        ulist.add(4, 1);
    }

    @Test(expected = UniqueList.NotInitedElementException.class)
    public void testRemove1() throws Exception {
        ulist.add(1, 1);
        ulist.remove(5);
    }

    @Test
    public void testContains() throws Exception {
        ulist.add(1, 1);
        ulist.add(2, 2);
        ulist.add(3, 3);
        assertTrue(ulist.contains(2));
        assertFalse(ulist.contains(10));
    }

    @Test
    public void testClear() throws Exception {
        ulist.add(1, 1);
        ulist.clear();
        assertFalse(ulist.contains(1));
    }

    @Test
    public void testGetValue() throws Exception {
        ulist.add(1, -100);
        assertEquals(-100, ulist.getValue(1));
    }

    @Test(expected = UniqueList.SizeListException.class)
    public void testGetValue1() throws Exception {
        ulist.add(1, 1);
        ulist.getValue(505);
    }

    @Test
    public void testFind1() throws Exception {
        ulist.add(1, 1);
        ulist.find(1);
    }
    @Test(expected = UniqueList.NotInitedElementException.class)
    public void testFind() throws Exception {
        ulist.add(1, 1);
        ulist.find(6);
    }
}
