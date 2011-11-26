import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Volkman
 * Date: 09.03.2010
 * Time: 23:54:22
 */
public class Main {
    private static IStack stack(int type) {
        switch(type){
            case 1:
                StackInt stack = new StackInt();
                return stack;
            case 2:
                StackList stacklist = new StackList();
                return stacklist;
            case 3:
                TrueStack s = new TrueStack();
                return s;
            default:
                StackInt st = new StackInt();
                return st;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("To select a stack enter 1..3:");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int type = Integer.parseInt(br.readLine());

        System.out.println("");
        System.out.println("Enter expression:");

        String exp = br.readLine();
        Calc c = new Calc(stack(type), exp);

        System.out.println("result = " + c.getResult()); 
    }
}
