/**
 * User: Volkman
 * Date: 20.03.2010
 * Time: 19:29:59
 */
public class Compile {
    private StackChar oper;
    private final static int OPER_PLUS = 1,
            OPER_MINUS = 1,
            OPER_MP = 2,
            OPER_DIV = 2;

    private static int operType(char c) {
        switch (c) {
            case '+':
                return OPER_PLUS;
            case '-':
                return OPER_MINUS;
            case '*':
                return OPER_MP;
            case '/':
                return OPER_DIV;
            default:
                return -1;
        }
    }

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Compile() {
        oper = new StackChar();
    }

    public String compileString(String string) {
        String res = new String();
        String[] str = string.split(" ");
        for (int i = 0; i < str.length; i++) {
            if (str[i].length() > 1 || isNumber(str[i])) {
                res += str[i] + " ";
            }
            char c0 = str[i].charAt(0);
            if (c0 == '(') {
                oper.push(c0);
            }
            if (c0 == ')') {
                while (oper.top() != '(') {
                    res += oper.top() + " ";
                    oper.pop();
                }
                oper.pop();
            }
            if (c0 == '+' || c0 == '-' || c0 == '*' || c0 == '/') {

                while ( !oper.isEmpty() && (operType(c0) <= operType(oper.top())) ) {
                    res += oper.top() + " ";
                    oper.pop();
                }

                oper.push(c0);
            }
        }
        while (!oper.isEmpty()) {
            res += oper.top() + " ";
            oper.pop();
        }
        return res;
    }
}
