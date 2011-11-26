import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Volkman
 * Date: Sep 9, 2010 5:44:46 PM
 */
public class BSTTest {
    BinarySearchTree<String> tree = new BinarySearchTree<String>();

    @Test
    public void testInsertAndFind() throws Exception {
        tree.insert("truth", 24);
        tree.insert("lie", 7);
        System.out.println("xaxa " + tree.find(7));
        assertEquals(tree.find(7), "lie");
        assertEquals(tree.find(8), null);
    }

    @Test
    public void testFind1() throws Exception {
        assertEquals(tree.find(8), null);
    }

    @Test
    public void testRemove() throws Exception {
        tree.insert("red", 24);
        tree.insert("blue", 7);
        tree.remove(4);
        tree.remove(7);
        assertEquals(tree.find(7), null);
        tree.insert("white", 7);
        assertEquals(tree.find(7), "white");
    }

    @Test
    public void testRemoveRoot() throws Exception {
        tree.remove(4);
        tree.remove(7);
        assertEquals(tree.find(7), null);
        tree.insert("white", 7);
        assertEquals(tree.find(7), "white");
    }

    @Test
    public void testIterator() throws Exception {
        tree.insert("red", 1);
        tree.insert("white", 2);
        tree.insert("blue", 3);
        String str = "";
        for (String i : tree)
            str += i;
        System.out.println(str);
        assertEquals("redwhiteblue", str);
    }
}
