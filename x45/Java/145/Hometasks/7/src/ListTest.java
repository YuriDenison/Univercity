import org.junit.Test;
import static org.junit.Assert.*;

/**
 * User: Volkman
 * Date: 31.03.2010
 * Time: 22:10:37
 */
public class ListTest {
    List<Integer> list = new List<Integer>();

    @Test
    public void testIterator() throws Exception {
        list.add(10);
        list.add(11);
        list.add(12);
        String str = "";
        for(Integer a: list){
            str += a + " ";
        }
        assertEquals("10 11 12 ", str);
    }
}
