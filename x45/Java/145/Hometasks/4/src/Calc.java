/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 09.03.2010
 * Time: 3:45:20
 * To change this template use File | Settings | File Templates.
 */
// Калькулятор арифметических формул.
public class Calc {
    private IStack s;

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

    public Calc(IStack stack, String st) {
        this.s = stack;
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

