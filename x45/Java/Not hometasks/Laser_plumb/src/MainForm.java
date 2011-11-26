import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Алексей
 * Date: 17.04.2009
 * Time: 14:52:56
 * To change this template use File | Settings | File Templates.
 */
public class MainForm {
    //private JTextField heightField;
    //private JTextField widthField;

    private JPanel MainPanel;
    private JPanel Plumber;
    private JTextField Name;
    private JTextField Level;
    private JLabel start;
    private JLabel finish;
    private Logic Lazer;
    public Filling b;
    public Graphics g;
    private String pictPath = "";
    ImageIcon imageIcon;
    private int level = 0;
    JMenuBar menuBar;

    private void show(String a) {
        JOptionPane.showMessageDialog(null, a);
    }

    private void SF() {
        pictPath = "src\\Start.jpg";
        ImageIcon imageIcon = new ImageIcon(pictPath);
        start.setIcon(imageIcon);
        pictPath = "src\\Finish.jpg";
        ImageIcon imageIcon1 = new ImageIcon(pictPath);
        finish.setIcon(imageIcon1);
    }


    public MainForm() {
        menuBar = new JMenuBar();
        JMenu game_menu = new JMenu("Game");
        JMenu redaktor = new JMenu("Editor");

        JMenuItem newgame = new JMenuItem("New Game");
        game_menu.add(newgame);
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Input your name: ");
                Name.setText("Player: " + name);
                Level.setText("Level 1; Turns 0");
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                b = new Filling(Level, level, Plumber, 10, 10, false);

                SF();
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = dimension.width;
                int y = dimension.height;
                frame.setSize(x, y);
                frame.setLocation(0, 0);
                frame.setVisible(true);
                //frame.setSize(frame.getWidth(), frame.getWidth() + 10);
            }
        });

        JMenuItem decision = new JMenuItem("Correct Way");
        game_menu.add(decision);
        decision.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b._Read(level, false);
                
            }
        });
        JMenuItem wat = new JMenuItem("Laser");
        game_menu.add(wat);
        wat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                try {
                    Lazer = new Logic(new Pair(2, 3), b.board1, b.map, g);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if (Lazer.res == 0) {
                    System.out.println("WIN");
                    show("WIN");
                    level++;
                    b._Read(level, true);
                }
                if (Lazer.res == 1) {
                    System.out.println("LOSE");
                    show("Lose");
                }
            }

        });

        menuBar.add(game_menu);

    }

    public JPanel getPanel() {
        return MainPanel;
    }

    private JFrame frame;

    public void showForm() {

        frame = new JFrame("Vodoprovod by Volkman & Lexus");
        frame.setContentPane(getPanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setJMenuBar(menuBar);
        //frame.setMaximumSize();
    }

    //@Override


    public static void main(String args[]) {
        new MainForm().showForm();
    }

    // private void createUIComponents() {
    protected void paintComponent(final Graphics g) {
        g.drawLine(100, 100, 768, 890);
        // }
// TODO: place custom component creation code here
    }
}