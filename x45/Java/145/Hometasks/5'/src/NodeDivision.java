/**
 * User: Volkman
 * Date: 23.03.2010
 * Time: 11:56:06
 */
public class NodeDivision extends Node{
    public NodeDivision(Node up) {
        super("/", up);
    }

    public int count(){
        return left.count() / right.count();   
    }
}
