/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 26.05.2010
 * Time: 11:00:19
 */
public class Matrix {
    private Node[][] elem;
    private int size;
    private int NODE_SIZE;
    private boolean simplified;

    public Matrix(int size, int n) {
        simplified = false;
        elem = new Node[size][size];
        this.size = size;
        this.NODE_SIZE = n;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                elem[i][j] = new Node(NODE_SIZE);
    }

    public Node findDiscriminant() {
        if (size == 2) {
            return elem[0][0].nodeMultiplication(elem[1][1]).nodeMinus(elem[0][1].nodeMultiplication(elem[1][0]));
        } else {
            if (!simplified) {
                Node commonMultiple = simplifyCoef();
                return commonMultiple.nodeMultiplication(this.findDiscriminant());
            } else {
                Node res = new Node(NODE_SIZE);
                int indexCol = findMaxNilColumn();
                for (int i = 0; i < size; i++) {
                    if (!elem[i][indexCol].isNil()) {
                        Node cur;
                        if (signCoef(i + indexCol))
                            cur = res.nodePlus(elem[i][indexCol].nodeMultiplication(
                                    deleteRowAndColumn(i, indexCol).findDiscriminant()));
                        else
                            cur = res.nodeMinus(elem[i][indexCol].nodeMultiplication(
                                    deleteRowAndColumn(i, indexCol).findDiscriminant()));
                        res = cur;
                    }
                }
                return res;
            }
        }
    }

    private int findMaxNilColumn() {
        int flag = 0, res = 0;
        for (int i = 0; i < size; i++) {
            int num = 0;
            for (int j = 0; j < size; j++) {
                if (elem[j][i].isNil())
                    num++;
            }
            if (num > flag) {
                flag = num;
                res = i;
            }
        }
        return res;
    }

    public Matrix deleteRowAndColumn(int row, int col) {
        Matrix matr = new Matrix(size - 1, NODE_SIZE);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matr.setElem(i, j, elem[i][j]);
            }
        }
        for (int i = row; i < size - 1; i++) {
            for (int j = 0; j < col; j++) {
                matr.setElem(i, j, elem[i + 1][j]);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = col; j < size - 1; j++) {
                matr.setElem(i, j, elem[i][j + 1]);
            }
        }
        for (int i = row; i < size - 1; i++) {
            for (int j = col; j < size - 1; j++) {
                matr.setElem(i, j, elem[i + 1][j + 1]);
            }
        }
        return matr;
    }

    // true <=> (-1)^n = 1
    private boolean signCoef(int n) {
        return n % 2 == 0;
    }

    public void setElem(int i, int j, Node node) {
        Node copy = new Node(NODE_SIZE);
        for(int k = 0; k < node.getSize(); k++){
            for(int n = 0; n < node.getSize(); n++){
                copy.setDegreeCoef(k, n, node.getDegreeCoef(k, n));
            }
        }
        elem[i][j] = copy;
    }

    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.println("(" + i + ", " + j + "):");
                elem[i][j].printNode();
                System.out.println("");
            }
        }
    }

    private Node simplifyCoef() {
        simplified = true;
        int res = 1;
        for(int i = 0; i < size; i++){
            int nod = nodOfRow(i);
            if(nod != 1){
                res *= nod;
                for(int j = 0; j < size; j++){
                    elem[i][j].divisionNod(nod);
                }
            }
        }
        Node result = new Node(NODE_SIZE);
        result.setDegreeCoef(0, 0, res);
        return result;
    }

    private int nodOfRow(int row){
        int nod = elem[row][0].commonMultiply();
        for(int i = 0; i < size; i++){
            nod = ParseLogic.nod(nod, elem[row][i].commonMultiply());
            if(nod == 1)
                break;
        }
        return nod;
    }


}
