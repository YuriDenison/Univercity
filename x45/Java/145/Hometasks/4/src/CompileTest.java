import org.junit.Test;
import static org.junit.Assert.*;
/**
 * User: Volkman
 * Date: 10.03.2010
 * Time: 11:47:19
 */
public class CompileTest {
    Compile c = new Compile();
    @Test
    public void testIsNumber() throws Exception {
        assertEquals(true, c.isNumber("10"));
        assertEquals(false, c.isNumber("10a"));
    }

    @Test
    public void testCompileString() throws Exception {
        if(!"8 5 + ".equals(c.compileString("8 + 5")) ){
            fail(c.compileString("8 + 5"));
        }
        if(("8 2 2 * 4 8 4 / + 8 2 1 * - / + ".equals(c.compileString("8 + 2 * 2 * ( 4 + 8 / 4 ) / ( 8 - 2 * 1 )")))){
            fail(c.compileString("8 + 2 * 2 * ( 4 + 8 / 4 ) / ( 8 - 2 * 1 )"));
        }
    }
}
