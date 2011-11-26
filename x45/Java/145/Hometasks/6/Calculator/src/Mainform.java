import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Volkman
 * Date: 20.03.2010
 * Time: 19:54:19
 */
public class Mainform {
    private JPanel main_panel;
    private JTextField text_input;
    private JButton num_7;
    private JButton num_8;
    private JButton num_9;
    private JButton oper_plus;
    private JButton num_4;
    private JButton num_1;
    private JButton lol;
    private JButton num_5;
    private JButton num_6;
    private JButton oper_minus;
    private JButton oper_multiply;
    private JButton num_3;
    private JButton num_2;
    private JButton num_0;
    private JButton oper_equality;
    private JButton oper_division;
    private JButton num_bracket_left;
    private JButton num_bracket_right;

    private ActionListener addNumListener(final int num){
        return new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                text_input.setText(text_input.getText() + num);
            }
        };
    }
    private ActionListener addOperListener(final String oper){
        return new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                text_input.setText(text_input.getText() + oper);
            }
        };
    }

    public Mainform() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        lol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "OLOLOLOLO =))");
                text_input.setText("");
            }
        });

        num_1.addActionListener(addNumListener(1));
        num_2.addActionListener(addNumListener(2));
        num_3.addActionListener(addNumListener(3));
        num_4.addActionListener(addNumListener(4));
        num_5.addActionListener(addNumListener(5));
        num_6.addActionListener(addNumListener(6));
        num_7.addActionListener(addNumListener(7));
        num_8.addActionListener(addNumListener(8));
        num_9.addActionListener(addNumListener(9));
        num_0.addActionListener(addNumListener(0));


        oper_plus.addActionListener(addOperListener(" + "));
        oper_minus.addActionListener(addOperListener(" - "));
        oper_multiply.addActionListener(addOperListener(" * "));
        oper_division.addActionListener(addOperListener(" / "));


        num_bracket_left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text_input.getText().length() == 0) {
                    text_input.setText(text_input.getText() + "( ");
                } else {
                    text_input.setText(text_input.getText() + " ( ");
                }
            }
        });

        num_bracket_right.addActionListener(addOperListener(" ) "));


        oper_equality.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calc c = new Calc(text_input.getText());
                JOptionPane.showMessageDialog(null, text_input.getText() + " = " + c.getResult());
                text_input.setText("");
            }
        });


    }

    public JPanel getForm() {
        return main_panel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here 
    }
}
