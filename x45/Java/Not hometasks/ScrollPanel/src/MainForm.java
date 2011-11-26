import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class MainForm extends JPanel {

  JLabel label = new JLabel();

  public MainForm() {
    super(true);

    setLayout(new BorderLayout());

    JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 300);
    JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);

    hbar.setUnitIncrement(2);
    hbar.setBlockIncrement(1);

    hbar.addAdjustmentListener(new MyAdjustmentListener());
    vbar.addAdjustmentListener(new MyAdjustmentListener());

    add(hbar, BorderLayout.SOUTH);
    add(vbar, BorderLayout.EAST);
    add(label, BorderLayout.CENTER);
  }

    class MyAdjustmentListener implements AdjustmentListener {
    public void adjustmentValueChanged(AdjustmentEvent e) {
      label.setText("    New Value is " + e.getValue() + "      ");
      repaint();
    }
  }

  public static void main(String s[]) {
    JFrame frame = new JFrame("Scroll Bar Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new MainForm());
    frame.setSize(200, 200);
    frame.setVisible(true);
  }
}