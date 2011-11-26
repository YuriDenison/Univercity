package game;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm {
    private JTextField widthField;
    private JTextField heightField;
    private JButton newGameButton;
    private JComboBox moveBox;
    private JPanel boardPanel;
    private JTextField lenField;
    private JPanel mainPanel;

    public MainForm() {
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int w , h , l;
                try
                {
                    w = Integer.parseInt(widthField.getText());
                    h = Integer.parseInt(heightField.getText());
                    l = Integer.parseInt(lenField.getText());
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null , "Введены неправильные значения");
                    return;
                }
                Board b = new Board(boardPanel , w , h , l , moveBox.getSelectedIndex() == 1);
                frame.setSize(frame.getWidth() , frame.getWidth() - 1);
                frame.setSize(frame.getWidth() , frame.getWidth());
            }
        });
    }

    public JPanel getPanel()
    {
        return mainPanel;
    }

    private JFrame frame;

    private void showForm()
    {
        frame = new JFrame("Tic-tac-toe by Volkman");
        frame.setContentPane(getPanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String args[])
    {
        new MainForm().showForm();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
