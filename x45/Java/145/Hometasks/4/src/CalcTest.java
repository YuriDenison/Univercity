import org.junit.Test;
import static org.junit.Assert.*;
/**
 * User: Volkman
 * Date: 10.03.2010
 * Time: 12:57:54
 */
public class CalcTest {
    @Test
    public void testGetResult() throws Exception {
        StackInt stack = new StackInt();
        StackList stack1 = new StackList();
        TrueStack stack2 = new TrueStack();
        Calc c = new Calc(stack, "8 + 2 * 2 * ( 4 + 8 / 4 ) / ( 3 + 9 / 3 )");
        System.out.println(c.getResult());
        Calc c1 = new Calc(stack1, "8 + 2 * 2 * ( 4 + 8 / 4 ) / ( 3 + 9 / 3 )");
        System.out.println(c1.getResult());
        Calc c2 = new Calc(stack2, "8 + 2 * 2 * ( 4 + 8 / 4 ) / ( 3 + 9 / 3 )");
        System.out.println(c2.getResult());
        assertEquals(c.getResult(), c1.getResult());
        assertEquals(c1.getResult(), c2.getResult());
    }
}
