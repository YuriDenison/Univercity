import org.junit.Test;
import static org.junit.Assert.*;
/**
 * User: Volkman
 * Date: 24.03.2010
 * Time: 15:01:12
 */
public class ParseTreeTest {

    @Test
    public void testGetResult() throws Exception {
        ParseTree pt1 = new ParseTree("1");
        assertEquals(1, pt1.getResult());

        ParseTree pt2 = new ParseTree("( / ( + 1 1 ) 2 )");
        assertEquals(1, pt2.getResult());

        ParseTree pt3 = new ParseTree("( / 4 ( + 1 1 ) )");
        assertEquals(2, pt3.getResult());

        ParseTree pt4_1 = new ParseTree("( + 1 2 )");
        ParseTree pt4_2 = new ParseTree("( + 2 1 )");
        assertEquals(pt4_1.getResult(), pt4_2.getResult());
    }
}
