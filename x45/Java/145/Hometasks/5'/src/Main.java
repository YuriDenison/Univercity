import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Volkman
 * Date: 19.03.2010
 * Time: 14:03:42
 */
public class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        System.out.println("Enter expression: ");
        String str = br.readLine();
        System.out.println(str);
        ParseTree ptree = new ParseTree(str);

        Node.printTree(ptree.getRoot(), 0);

        System.out.println("Result = " + ptree.getResult());
    }
}

/*
    Example:
    "( + ( * 3 ( + 1 ( - 2 1 ) ) ) -5 )"
    "( * ( + 1 1 ) 2 )"
 */
