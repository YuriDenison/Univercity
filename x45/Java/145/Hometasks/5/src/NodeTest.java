import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * User: Volkman
 * Date: 23.03.2010
 * Time: 0:50:49
 */
public class NodeTest {
    @Test
    public void testIsNumber() throws Exception {
        assertEquals(true, Node.isNumber("10"));
        assertEquals(false, Node.isNumber("10a"));
    }

    @Test
    public void testcount() throws Exception {
        Node root = new Node("+", null);
        root.setLeft(new Node("10", root));
        root.setRight(new Node("15", root));
        assertEquals(25, root.count());
    }
    @Test(expected = BadOperationException.class)
    public void testcount1() throws Exception {
        Node root = new Node("p", null);
        root.setLeft(new Node("10", root));
        root.setRight(new Node("15", root));
        assertEquals(25, root.count());
    }

    @Test
    public void setUp() throws Exception {
        Node root = new Node("bratva", null);
        Node n1 = new Node("bratva", root);
        Node n2 = new Node("asda", null);
        n2 = n1;
        System.out.println(" val = " + n2.getVal());
        if(n1.getUp().equals(n2.getUp()))
            System.out.println("true");
    }
}
