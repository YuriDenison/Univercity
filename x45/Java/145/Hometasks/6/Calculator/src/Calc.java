/**
 * User: Volkman
 * Date: 20.03.2010
 * Time: 19:38:39
 */
public class Calc {
    private Stack s;

    public int getResult() {
        return result;
    }

    private int result;


    private void oper(char c) {
        int second = s.pop();
        int first = s.pop();
        switch (c) {
            case '+':
                s.push(first + second);
                break;
            case '-':
                s.push(first - second);
                break;
            case '*':
                s.push(first * second);
                break;
            case '/':
                s.push(first / second);
                break;
        }
    }

    public Calc(String st) {
        s = new Stack();
        this.result = calculate(st);
    }

    private int calculate(String str) {
        Compile comp = new Compile();
        String[] res = comp.compileString(str).split(" ");
        //    System.out.println(comp.compileString(str));

        for (int i = 0; i < res.length; i++) {
            if (res[i].length() > 1 || Compile.isNumber(res[i])) {
                s.push(Integer.parseInt(res[i]));
            } else
                oper(res[i].charAt(0));
        }
        return s.top();
    }
}

