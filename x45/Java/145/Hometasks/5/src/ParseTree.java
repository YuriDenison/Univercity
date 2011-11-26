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
        cur = new Node("", null);
        root = new Node("", null);
        root = cur;
        result = calculate(str);
    }

    private int calculate(String s) {
        String[] str = s.split(" ");

        for (int i = 0; i < str.length; i++) {
            //  printTree(root);
            if (!Node.isNumber(str[i])) {
                switch (str[i].charAt(0)) {
                    case '(':
                        Node node = new Node("", cur);
                        Node node1 = new Node("", cur);
                        cur.setLeft(node);
                        cur.setRight(node1);
                        break;
                    case ')':
                        if (cur != root) {
                            if (cur == (cur.getUp().getLeft())) {
                                cur = cur.getUp().getRight();
                            } else {
                                cur = cur.getUp();
                            }
                        }
                        break;
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        cur.setVal("" + str[i].charAt(0));
                        cur = cur.getLeft();
                        break;
                }
            } else {
                cur.setVal(str[i]);
                if (cur.equals(cur.getUp().getLeft())) {
                    cur = cur.getUp().getRight();
                } else {
                    cur = cur.getUp();
                }
            }
        }

        try {
            return root.count();
        } catch (BadOperationException e) {
            System.out.println("Fail");
        }
        return 0;
    }


    private void p1(Node node) {
        Node n = node;
        if (n.getLeft() != null && n.getRight() != null)
            System.out.print(" '" + n.getLeft().getVal() + "' '" + n.getRight().getVal() + "'");
    }

    private void p2(Node node) {
        p1(node);
        System.out.println("");
        if (node.getLeft() != null && node.getRight() != null) {
            p2(node.getLeft());
            p2(node.getRight());
        }
    }

    public void printTree(Node node){
        System.out.println(" '" + node.getVal() + "'");
        p2(node);
    }

    public int getResult() {
        return result;
    }

    public Node getRoot() {
        return root;
    }
}
