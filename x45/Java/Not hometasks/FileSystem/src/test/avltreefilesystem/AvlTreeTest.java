package test.avltreefilesystem;

import filesystem.avltreefilesystem.AvlTree;
import filesystem.avltreefilesystem.AvlTreeSerializer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * AvlTree test class
 *
 * @author Yuri Denison
 */
public class AvlTreeTest {
    private AvlTree tree = new AvlTree(new AvlTreeSerializer("test.fs", true));
    private static final int ITERATIONS = 2000;

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            tree.insert(i, 0, new int[0]);

            assertEquals(tree.size(), i + 1);
        }
    }

    @Test
    public void testDoubleInsert() throws Exception {
        tree.insert(1, 0, new int[1]);
        assertEquals(tree.find(1).getPositions().length, 1);
        tree.insert(1, 0, new int[2]);
        assertEquals(tree.size(), 1);
        assertEquals(tree.find(1).getPositions().length, 2);
    }


    @Test
    public void testDeleteObj() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            tree.insert(i, 0, new int[0]);
            assertEquals(tree.size(), i + 1);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            tree.remove(i);
            assertEquals(tree.size(), ITERATIONS - i - 1);
        }

        assertEquals(tree.remove(-1), null);
        tree.insert(-1, 0, new int[0]);
        assertEquals(tree.remove(-1).getKey(), -1);
    }
}
