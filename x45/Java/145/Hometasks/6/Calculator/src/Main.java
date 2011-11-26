import javax.swing.*;

/**
 * User: Volkman
 * Date: 20.03.2010
 * Time: 20:38:14
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator by Volkman");
        frame.setContentPane(new Mainform().getForm());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
