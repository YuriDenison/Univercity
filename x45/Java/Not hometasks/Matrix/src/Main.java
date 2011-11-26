/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 26.05.2010
 * Time: 12:43:24
 */
public class Main {
    public static void main(String[] args) {
        final int NODE_SIZE = 10;

        Node[] node = new Node[6];
        for (int i = 0; i < 6; i++)
            node[i] = new Node(NODE_SIZE);
        node[5].setDegreeCoef(0, 0, 1);
        node[3].setDegreeCoef(0, 1, -5);
        node[1].setDegreeCoef(0, 2, 5);
        node[0].setDegreeCoef(1, 0, -1);     

       /* Node[] node = new Node[4];
        for (int i = 0; i < 4; i++)
            node[i] = new Node(NODE_SIZE);
        node[3].setDegreeCoef(0, 1, 1);
        node[2].setDegreeCoef(1, 0, -1);
        node[1].setDegreeCoef(1, 0, 1);
        node[1].setDegreeCoef(0, 1, -3);
        node[0].setDegreeCoef(0, 1, 1);*/

        Matrix matrix = ParseLogic.createMatrix(node, NODE_SIZE);

        matrix.findDiscriminant().printNode();
    }
}
