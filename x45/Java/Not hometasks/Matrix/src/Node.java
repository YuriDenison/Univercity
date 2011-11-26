/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 26.05.2010
 * Time: 11:00:35
 */
public class Node {
    // arr[i][j] means 'arr[i][j] * a^i * b^j in node of matrix'
    private int[][] arrDegreeCoef;
    private int size = 0;

    public Node(int size) {
        arrDegreeCoef = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arrDegreeCoef[i][j] = 0;
            }
        }
        this.size = size;
    }

    public Node(int[][] input, int size) {
        arrDegreeCoef = input;
        this.size = size;
    }

    public Node nodePlus(Node node) {
        Node res = new Node(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                res.setDegreeCoef(i, j, arrDegreeCoef[i][j] + node.getDegreeCoef(i, j));
            }
        }
        return res;
    }

    public Node nodeMinus(Node node) {
        Node res = new Node(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                res.setDegreeCoef(i, j, arrDegreeCoef[i][j] - node.getDegreeCoef(i, j));
            }
        }
        return res;
    }

    // Размер остается прежним, а не удваивается, так как изначально берется с расчетом на то, что
    // степень ни a ни b не станет больше него.

    public Node nodeMultiplication(Node node) {
        Node res = new Node(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                for (int k = 0; k <= i; k++) {
                    for (int n = 0; n <= j; n++) {
                        res.setDegreeCoef(i, j, res.getDegreeCoef(i, j) +
                                arrDegreeCoef[k][n] * node.getDegreeCoef(i - k, j - n));
                    }
                }
            }
        }
        return res;
    }

    public void divisionNod(int div){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(arrDegreeCoef[i][j] != 0)
                    arrDegreeCoef[i][j] /= div;
            }
        }    
    }

    public void printNode() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(arrDegreeCoef[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public boolean isNil() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arrDegreeCoef[i][j] != 0)
                    return false;
            }
        }
        return true;
    }

    public int commonMultiply() {
        int res = arrDegreeCoef[0][0];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arrDegreeCoef[i][j] != 0) {
                    res = ParseLogic.nod(res, arrDegreeCoef[i][j]);
                    if (res == 1)
                        break;
                }
            }
        }
        return res;
    }

    private int findMinNilX(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(arrDegreeCoef[i][j] != 0)
                    return j;
            }
        }
        return -1;
    }

    private int findMinNilY(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(arrDegreeCoef[j][i] != 0)
                    return j;
            }
        }
        return -1;
    }

    public int getDegreeCoef(int i, int j) {
        return arrDegreeCoef[i][j];
    }

    public void setDegreeCoef(int i, int j, int num) {
        arrDegreeCoef[i][j] = num;
    }

    public int getSize() {
        return size;
    }

    public void setArrDegreeCoef(int[][] arrDegreeCoef) {
        this.arrDegreeCoef = arrDegreeCoef;
    }
}
