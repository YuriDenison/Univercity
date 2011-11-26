import org.junit.Test;
import static org.junit.Assert.*;
/**
 * User: Volkman
 * Date: 10.03.2010
 * Time: 16:11:00
 */
public class HashTableTest {

    HashTable ht = new HashTable(100, new Hash2());

    @Test
    public void testAddAndContains() throws Exception {
        ht.add("=)");
        ht.add(":)");
        ht.add("%)");
        if(!ht.contains("=)")) fail("contains error");
    }

    @Test
    public void testRemove() throws Exception {
        ht.add("=)");
        if(!ht.contains("=)"))
            fail("contains error");
        ht.remove("=)");
        if(ht.contains("=)"))
            fail("remove error");

    }

    @Test
    public void testClear() throws Exception {
        ht.add("=)");
        ht.add(":)");
        ht.add("%)");
        ht.clear();
        if(ht.contains("=)") || ht.contains(":)") || ht.contains("%))"))
            fail("clear error");
    }
}
