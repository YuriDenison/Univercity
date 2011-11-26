package Code;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 07.05.2010
 * Time: 22:42:52
 */
public class Board {
    private class Listener implements MouseListener {
        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            Cell p = getCell((JLabel) (e.getComponent()));
            if (arrangements[curentNumberOfArrangement][p.getX()][p.getY()] == 1) {
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        if (!(p.getX() == i && p.getY() == j) &&
                                ((Math.abs(p.getX() - i) == Math.abs(p.getY() - j)) || p.getX() == i || p.getY() == j))
                            map[i][j].setBackground(backlight);
                    }
                }
            }
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
            update();
        }

        public void mousePressed(MouseEvent e) {
        }

        private Cell getCell(JLabel l) {
            for (int i = 0; i < ROWS; i++)
                for (int j = 0; j < COLUMNS; j++)
                    if (map[i][j] == l)
                        return new Cell(i, j);
            throw new IllegalArgumentException("Cannot find this JLabel object in map[][]");
        }
    }

    private JButton start;
    private JPanel mainPanel;
    private JPanel table;
    private JButton next;
    private JButton prev;

    public static int ROWS = 0;
    public static int COLUMNS = 0;
    public static final Color backlight = new Color(200, 200, 255);


    private JLabel[][] map;
    private Logic logic;
    private int[][][] arrangements;
    private int curentNumberOfArrangement;


    public Board() {
        String str = JOptionPane.showInputDialog("Input size of the board: ");
        ROWS = Integer.parseInt(str);
        COLUMNS = ROWS; // Рассматривать неквадратные доски не имеет смысла.
        arrangements = new int[15000][ROWS][COLUMNS];
        curentNumberOfArrangement = 1;

        table.removeAll();
        table.setLayout(new GridLayout(ROWS, COLUMNS));
        map = new JLabel[ROWS][COLUMNS];
        Listener listener = new Listener();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JLabel l = new JLabel("");
                if ((i + j) % 2 == 1)
                    l.setBackground(Color.WHITE);
                else
                    l.setBackground(Color.BLACK);
                l.setOpaque(true);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setFont(new Font(null, Font.BOLD, 30));
                l.setMinimumSize(new Dimension(50, 50));
                l.addMouseListener(listener);
                l.setBorder(new LineBorder(Color.GRAY));
                map[i][j] = l;
                table.add(l);
            }
        }
        table.setFocusable(true);
        table.requestFocus();
        prev.setEnabled(false);
        next.setEnabled(false);

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("start");
                logic = new Logic();
                arrangements = logic.getArrangements();
                next.setEnabled(true);
                update();
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prev.setEnabled(true);
                curentNumberOfArrangement++;
                if (curentNumberOfArrangement >= logic.getNumberOfVariants())
                    next.setEnabled(false);
                update();
            }
        });

        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                next.setEnabled(true);
                curentNumberOfArrangement--;
                if (curentNumberOfArrangement == 1)
                    prev.setEnabled(false);
                update();
            }
        });
    }

    private void update() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (arrangements[curentNumberOfArrangement][i][j] == 1) {
                    map[i][j].setBackground(Color.YELLOW);
                    map[i][j].setIcon(new ImageIcon("src\\Code\\Ferz.JPG"));
                } else {
                    map[i][j].setIcon(null);
                    if ((i + j) % 2 == 1)
                        map[i][j].setBackground(Color.WHITE);
                    else
                        map[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    public void showForm() {
        final JFrame frame;
        frame = new JFrame("Queens");
        frame.setBackground(Color.lightGray);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
