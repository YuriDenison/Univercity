import java.util.zip.CRC32;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.03.2010
 * Time: 20:51:34
 * To change this template use File | Settings | File Templates.
 */
public class Hash2 implements IHash{
    public int hash(String str, int len) {
        CRC32 c = new CRC32();
                c.update(str.getBytes());
                return (int) (c.getValue() % len);
    }
}

