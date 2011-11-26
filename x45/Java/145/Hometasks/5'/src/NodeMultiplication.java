/**
 * User: Volkman
 * Date: 23.03.2010
 * Time: 11:57:13
 */
public class NodeMultiplication extends Node{
    public NodeMultiplication(Node up) {
        super("*", up);
    }

    public int count(){
        return left.count() * right.count(); 
    }
}
