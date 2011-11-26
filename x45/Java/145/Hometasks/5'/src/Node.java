/**
 * User: Volkman
 * Date: 22.03.2010
 * Time: 20:20:20
 */
public abstract class Node {
    protected String val;
    protected Node left;
    protected Node right;
    protected Node up;

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

    public abstract int count();

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

    public static void printTree(Node node, int lev) {
        System.out.println("" + node.getVal());
        lev++;
        if (node.getLeft() != null && node.getRight() != null) {
            for (int j = 0; j < lev; j++)
                System.out.print(" ");
            printTree(node.getLeft(), lev);
            for (int j = 0; j < lev; j++)
                System.out.print(" ");
            printTree(node.getRight(), lev);
        }
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
