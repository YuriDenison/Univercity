import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 24.04.2009
 * Time: 21:43:48
 * To change this template use File | Settings | File Templates.
 */
public class Rezults extends MainForm{
    private JPanel panel1;
    public JTextField name2;
    public JTextField name8;
    public JTextField name1;
    public JTextField name7;
    public JTextField name6;
    public JTextField name3;
    public JTextField name5;
    public JTextField name4;
    public JTextField s1;
    public JTextField s2;
    public JTextField s3;
    public JTextField s4;
    public JTextField s5;
    public JTextField s6;
    public JTextField s7;
    public JTextField s8;
    public int p1 = 0, p2 = 0, p3 = 0, p4 = 0, p5 = 0, p6 = 0, p7 = 0, p8 = 0;

    public JPanel getPanel() {
        return panel1;
    }

    private JFrame frame;

    public void show() {
        frame = new JFrame("REZULTS");
        frame.setContentPane(getPanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
       
    }

    public void set(String[] names, int[] score){
        name1.setText(names[1]);
        name2.setText(names[2]);
        name3.setText(names[3]);
        name4.setText(names[4]);
        name5.setText(names[5]);
        name6.setText(names[6]);
        name7.setText(names[7]);
        name8.setText(names[8]);
        s1.setText("" + score[1]);
        s2.setText("" + score[2]);
        s3.setText("" + score[3]);
        s4.setText("" + score[4]);
        s5.setText("" + score[5]);
        s6.setText("" + score[6]);
        s7.setText("" + score[7]);
        s8.setText("" + score[8]);
    }
}
