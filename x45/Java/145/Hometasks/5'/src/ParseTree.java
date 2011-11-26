/**
 * User: Volkman
 * Date: 22.03.2010
 * Time: 14:42:26
 */
public class ParseTree {
    private Node cur;
    private Node root;
    private int result;

    public ParseTree(String str) {
        result = calculate(str);
    }

    private void leftRight(Node cur, Node node) {
        if (cur.getLeft() == null)
            cur.setLeft(node);
        else
            cur.setRight(node);
    }

    private int calculate(String s) {
        String[] str = s.split(" ");

        for (int i = 0; i < str.length; i++) {
            if (!Node.isNumber(str[i])) {
                switch (str[i].charAt(0)) {
                    case '(':
                        break;
                    case ')':
                        if (cur != root) {

                            cur = cur.getUp();

                        }
                        break;
                    case '+':
                        if (root == null) {
                            root = new NodePlus(null);
                            cur = root;
                        } else {
                            NodePlus node = new NodePlus(cur);
                            leftRight(cur, node);
                            cur = node;
                        }
                        break;
                    case '-':
                        if (root == null) {
                            root = new NodeMinus(null);
                            cur = root;
                        } else {
                            NodeMinus node = new NodeMinus(cur);
                            leftRight(cur, node);
                            cur = node;
                        }
                        break;
                    case '*':
                        if (root == null) {
                            root = new NodeMultiplication(null);
                            cur = root;
                        } else {
                            NodeMultiplication node = new NodeMultiplication(cur);
                            leftRight(cur, node);
                            cur = node;
                        }
                        break;
                    case '/':
                        if (root == null) {
                            root = new NodeDivision(null);
                            cur = root;
                        } else {
                            NodeDivision node = new NodeDivision(cur);
                            leftRight(cur, node);
                            cur = node;
                        }
                        break;
                }
            } else {
                if (root == null) {
                    root = new NodeNum(str[i], null);
                } else {
                    NodeNum node = new NodeNum(str[i], cur);
                    leftRight(cur, node);
                }
            }
        }

        return root.count();
    }

    public int getResult() {
        return result;
    }

    public Node getRoot() {
        return root;
    }
}
