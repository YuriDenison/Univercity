/**
 * User: Volkman
 * Date: 22.03.2010
 * Time: 20:20:20
 */
public class Node {
    private String val;
    private Node left;
    private Node right;
    private Node up;

    public static boolean isNumber(String c) {
        try {
            Integer.parseInt("" + c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Node(String s, Node up) {
        this.val = s;
        this.up = up;
        left = null;
        right = null;
    }

    public int count() throws BadOperationException {
        if (!isNumber(val)) {
            switch (val.charAt(0)) {
                case '+':
                    return left.count() + right.count();
                case '-':
                    return left.count() - right.count();
                case '*':
                    return left.count() * right.count();
                case '/':
                    return left.count() / right.count();
            }
        } else {
            return Integer.parseInt(val);
        }
        throw new BadOperationException("Not +, -, * or /");
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getUp() {

        return up;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }

    public String getVal() {
        return val;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getRight() {
        return right;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
