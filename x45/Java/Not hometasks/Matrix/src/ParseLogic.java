/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 26.05.2010
 * Time: 14:50:14
 */
public class ParseLogic {


    public static Matrix createMatrix(Node[] node, int node_size) {
        int step = node.length - 1;
        Matrix matrix = new Matrix(2 * step - 1, node_size);
        for(int i = 0; i < step - 1; i++){
            int flag = step;
            for(int j = i; j < i + step + 1; j++){
                matrix.setElem(i, j, node[flag]);
                flag--;
            }
        }
        Node[] derivative = ParseLogic.getDerivative(node, node_size);
        for(int i = step - 1; i < 2 * step - 1; i++){
            int flag = step - 1;
            for(int j = i - step + 1; j <= i; j++){
                matrix.setElem(i, j, derivative[flag]);
                flag--;
            }
        }
        return matrix;
    }

    public static Node[] getDerivative(Node[] node, int node_size) {
        Node[] res = new Node[node.length - 1];
        for(int i = 0; i < res.length; i++)
            res[i] = new Node(node_size);
        for(int i = node.length - 2; i >= 0; i--){
            Node nodeStep = new Node(node_size);
            nodeStep.setDegreeCoef(0, 0, i+1);
            res[i] = node[i+1].nodeMultiplication(nodeStep);
        }
        return res;
    }

  

    public static int nod(int a, int b) {
        a = Math.abs(a);  b = Math.abs(b);
        while (a != 0 && b != 0) {
            if (a >= b)
                a = a % b;
            else
                b = b % a;
        }
        return a + b;
    }
}
