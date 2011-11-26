import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * User: Volkman
 * Date: 10.03.2010
 * Time: 10:20:25
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StackIntTest {

    private StackInt stack = new StackInt();

    @Before
    public void setUp() {
        stack = new StackInt();
    }

    @Test
    public void testPush() {
        stack.push(0);
        // Проверили, что не упали
    }

    @Test
    public void testPop() {
        stack.push(10);
        stack.push(110);
        int result = stack.pop();
        assertEquals(110, result);
    }

    @Test
    public void testTop() {
        stack.push(10);
        stack.push(110);
        assertEquals(110, stack.top());
    }

    @Test
    public void testisEmpty() {
        StackList s1 = new StackList();
        s1.push(0);
        s1.pop();
        assertEquals(true, s1.isEmpty());
    }

    @Test
    public void testPushAndPop() {
        stack.push(10);
        int result = stack.pop();
        assertEquals(10, result);
    }

}