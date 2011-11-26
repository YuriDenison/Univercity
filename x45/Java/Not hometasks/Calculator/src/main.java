import javax.swing.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: denison
 * Date: 22.10.2008
 * Time: 18:04:19
 * To change this template use File | Settings | File Templates.
 */

public class main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator by Volkman");
        frame.setContentPane(new Calculator().getForm());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

