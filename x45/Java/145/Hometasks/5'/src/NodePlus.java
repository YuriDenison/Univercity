/**
 * User: Volkman
 * Date: 23.03.2010
 * Time: 11:13:29
 */
public class NodePlus extends Node{

    public NodePlus(Node up) {
        super("+", up);
    }

    public int count(){
        return left.count() + right.count();    
    }
}
