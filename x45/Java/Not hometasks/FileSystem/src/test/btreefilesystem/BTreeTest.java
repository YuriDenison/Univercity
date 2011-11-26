package test.btreefilesystem;

import filesystem.btreefilesystem.BTree;
import filesystem.btreefilesystem.BTreeSerializer;
import filesystem.btreefilesystem.KeyNode;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * BTree test class
 *
 * @author Yuri Denison
 */
public class BTreeTest {
    private BTree tree = new BTreeSerializer("test.fs", true).getBTree();
    private static final int ITERATIONS = 1000;

    public BTreeTest() {
        PropertyConfigurator.configure("res/log4j.properties");
    }

    @Test
    public void testDoubleInsert() throws Exception {
        tree.insert(new KeyNode(1, 1, 0));
        assertEquals(tree.getKeyNode(1).getContentLength(), 1);
        tree.insert(new KeyNode(1, 2, 0));
        assertEquals(tree.size(), 1);
        assertEquals(tree.getKeyNode(1).getContentLength(), 2);
    }


    @Test
    public void testInsertAndDelete() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            tree.insert(new KeyNode(i, 1, 0));
            assertEquals(tree.size(), i + 1);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            tree.deleteKeyNode(i);
            assertEquals(tree.size(), ITERATIONS - i - 1);
        }

        assertEquals(tree.size(), 0);

        assertEquals(tree.deleteKeyNode(-1), null);
        tree.insert(new KeyNode(-1, 1, 1));
        assertEquals(tree.deleteKeyNode(-1).getKey(), -1);
    }
}
