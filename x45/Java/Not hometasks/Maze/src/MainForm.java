import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: denison
 * Date: 04.04.2009
 * Time: 16:51:06
 * To change this template use File | Settings | File Templates.
 */
public class MainForm {
    private JTextField Level;
    private JTextField Name;
    private JPanel MainPanel;
    private JPanel Board_1;
    private JTextField text;
    private JMenuBar menuBar;
    private Board b;
    private Redaktor r;
    private boolean rezult_show = false;
    public String name;
    private int w = 50,
            h = 50;
    private boolean red = false,
            game = false;


    public MainForm() {
        menuBar = new JMenuBar();
        JMenu game_menu = new JMenu("Game");
        JMenu redaktor = new JMenu("Editor");

        JMenuItem newgame = new JMenuItem("New Game");
        game_menu.add(newgame);
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name = JOptionPane.showInputDialog("Input your name: ");
                Name.setText("Player: " + name);
                b = new Board(Board_1, 1, w, h, Level, name);
                rezult_show = true;
                b.setBoard(Color.GREEN, Color.RED, Color.BLUE,
                        Color.BLUE, Color.BLACK, Color.YELLOW);
                frame.setSize(frame.getWidth(), frame.getWidth() - 1);
                frame.setSize(frame.getWidth(), frame.getWidth());
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = dimension.width;
                int y = dimension.height;
                frame.setSize(x, y);
                frame.setLocation(0, 0);
                frame.setVisible(true);
                game = true;
                red = false;
            }
        });

        JMenuItem findtheway = new JMenuItem("Find The Way");
        game_menu.add(findtheway);
        findtheway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game) b.Log(b.getCur());
            }
        });

        JMenuItem result = new JMenuItem("Results");
        game_menu.add(result);
        result.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(rezult_show) b.show_rez();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        game_menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });


        JMenuItem redakt = new JMenuItem("Start Editor");
        redaktor.add(redakt);
        redakt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                r = new Redaktor(Board_1, 50, 50, text);
                frame.setSize(frame.getWidth(), frame.getWidth() - 1);
                frame.setSize(frame.getWidth(), frame.getWidth());
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = dimension.width;
                int y = dimension.height;
                frame.setSize(x, y);
                frame.setLocation(0, 0);
                frame.setVisible(true);
                red = true;
                game = false;
            }
        });
        JMenuItem savemap = new JMenuItem("Save Map");
        redaktor.add(savemap);
        savemap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (red) {
                    r.Log(50, 50);
                    try {
                        FileWriter writer = new FileWriter("src/Redaktor.txt");
                        BufferedWriter buffered = new BufferedWriter(writer);
                        for (int i = 0; i < w; i++) {
                            for (int j = 0; j < h; j++) {
                                buffered.write(r.getBoard()[i][j] + " ");
                                buffered.write("\n");
                            }
                        }
                        buffered.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        menuBar.add(game_menu);
        menuBar.add(redaktor);

    }

    public JPanel getPanel() {
        return MainPanel;
    }

    private JFrame frame;

    private void showForm() {
        _write();
        frame = new JFrame("Maze by Volkman");
        frame.setContentPane(getPanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setJMenuBar(menuBar);
    }

    public static void main(String args[]) {
        new MainForm().showForm();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void _write() {
        try {
            FileWriter writer = new FileWriter("src/Rez.txt");
            BufferedWriter buffered = new BufferedWriter(writer);
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");
            buffered.write("comp\n9999\n");

            buffered.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
