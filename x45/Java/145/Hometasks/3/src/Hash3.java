import java.util.zip.Adler32;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.03.2010
 * Time: 20:52:34
 * To change this template use File | Settings | File Templates.
 */
public class Hash3 implements IHash{
    public int hash(String str, int len) {
        Adler32 c = new Adler32();
                c.update(str.getBytes());
                return (int) (c.getValue() % len);
    }
}
