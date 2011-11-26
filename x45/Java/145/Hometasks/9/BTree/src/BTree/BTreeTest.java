package BTree;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 28.05.2010
 * Time: 16:23:21
 */
public class BTreeTest {
    BTree btree = new BTree(2);


    @Test
    public void testInsert() throws Exception {
        btree.insert(new KeyNode<String>(1, "Yura"));
        btree.insert(new KeyNode<String>(22, "Mama"));
        btree.insert(new KeyNode<String>(3, "Papa"));
        btree.insert(new KeyNode<String>(14, "Van9"));
        btree.insert(new KeyNode<String>(46, "Vova"));
        btree.insert(new KeyNode<String>(27, "Dima"));

        assertEquals(null, btree.getKeyObj(10));
        assertEquals("Van9", btree.getKeyObj(14));
    }
    
    @Test
    public void testDeleteObj() throws Exception {
        btree.insert(new KeyNode<String>(1, "Yura"));
        btree.insert(new KeyNode<String>(22, "Mama"));
        btree.insert(new KeyNode<String>(3, "Papa"));
        btree.insert(new KeyNode<String>(14, "Van9"));
        btree.insert(new KeyNode<String>(46, "Vova"));
        btree.insert(new KeyNode<String>(27, "Dima"));

        btree.deleteObj(22);
        assertEquals(null, btree.getKeyObj(22));
        assertEquals("Yura", btree.getKeyObj(1));
        btree.deleteObj(1);
        assertEquals(null, btree.getKeyObj(1));
    }
}
