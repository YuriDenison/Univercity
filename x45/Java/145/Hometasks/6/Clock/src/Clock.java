import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * User: Volkman
 * Date: 21.03.2010
 * Time: 13:59:45
 */
public class Clock {
    public JPanel main_panel;
    private JLabel hour;
    private JLabel minutes;
    private JLabel dv1;
    private JLabel dv2;
    private JLabel second;

    private String setTimeString(String str) {
        if (str.length() == 1) {
            return "0" + str;
        }
        return str;
    }

    public Clock(){

        main_panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calendar curTime = Calendar.getInstance();
                curTime.setTimeInMillis(System.currentTimeMillis());

                hour.setText(setTimeString(curTime.get(Calendar.HOUR_OF_DAY) + ""));
                minutes.setText(setTimeString(curTime.get(Calendar.MINUTE) + ""));
                second.setText(setTimeString(curTime.get(Calendar.SECOND) + ""));
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public JPanel getForm() {
        return main_panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock by Volkman");
        frame.setContentPane(new Clock().getForm());
        frame.pack();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        AWTUtilities.setWindowOpacity(frame, (float)0.25);
        frame.setLocation((int) dim.getWidth() - 400, 20);
        frame.removeNotify();
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
