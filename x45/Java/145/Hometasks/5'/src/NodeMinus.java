/**
 * User: Volkman
 * Date: 23.03.2010
 * Time: 11:55:14
 */
public class NodeMinus extends Node{
    public NodeMinus(Node up) {
        super("-", up);
    }

    public int count(){
        return left.count() - right.count();   
    }
}
