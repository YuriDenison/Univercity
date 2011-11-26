/**
 * User: Volkman
 * Date: 23.03.2010
 * Time: 13:27:37
 */
public class NodeNum extends Node {

    public NodeNum(String st, Node up) {
        super(st, up);
    }

    public int count() {
        return Integer.parseInt(val);
    }

}
