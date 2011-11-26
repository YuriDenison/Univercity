/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.03.2010
 * Time: 20:53:11
 * To change this template use File | Settings | File Templates.
 */
public class HashUser {
    public static int hash(String str) {
        int h1 = 0;
        for (int i = 0; i < str.length(); i++) {
            h1 += str.charAt(i);
        }
        return h1;

    }
}
