import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Алексей
 * Date: 17.04.2009
 * Time: 14:58:14
 * To change this template use File | Settings | File Templates.
 */
public class Filling {

    public static final class Cell {
        public int x;
        public int y;

        public Cell() {
        }

        public Cell(int a, int b) {
            this.x = a;
            this.y = b;
        }
    }

    class Listener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Cell p = getCell((JLabel) (e.getComponent()));
            turn(p.x, p.y);
            turns++;
            Level.setText("Level " + (level+1) + "; " + turns + " turns");
        }

    }

    private final int ROWS, COLUMNS;
    private static final Color BACKGROUND = Color.WHITE;
    public static final short EMPTY = 0;
    public JLabel[][] map;
    public JLabel[][] map1;
    private Random r;
    private JPanel panel;
    private Listener listener1;
    public Integer[][] board1;
    private int n;
    Random generator;
    private String pictPath = "";
    ImageIcon imageIcon;
    public int turns = 0;
    public int level = 1;
    public boolean answer;
    private String line;
    JTextField Level;


    public Filling(JTextField Level, int level, JPanel panel, int r, int c, boolean ans) {
        ROWS = r;
        COLUMNS = c;
        this.level = level;
        this.Level = Level;
        map = new JLabel[r][c];
        board1 = new Integer[ROWS][COLUMNS];
        this.panel = panel;
        panel.removeAll();
        panel.setLayout(new GridLayout(r, c));

        listener1 = new Listener();
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                ImageIcon imageIcon1 = new ImageIcon(pictPath);
                JLabel l = new JLabel(imageIcon1);
                l.setBackground(BACKGROUND);
                l.addMouseListener(listener1);
                l.setOpaque(true);
                l.setBorder(new LineBorder(Color.BLUE));
                l.setMinimumSize(new Dimension(1, 1));
                map[i][j] = l;
                panel.add(l);

            }
        _Read(0, true);
    }

    public void _Read(int level, boolean r) {
        this.level = level;
        if (level == 0) line = "src/level1.txt";
        if (level == 1) line = "src/level2.txt";
        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream( line )));
            String line;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    line = reader.readLine();
                    board1[i][j] = (int) Double.parseDouble(line);
                }
            }

            reader.close();
        }
        catch (IOException e) {
            System.out.println("I/O");
        }
        catch (NumberFormatException e) {
            System.out.println("Not a number");
        }

        if(r) Rand();
        fill();
    }

    private void Rand() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                generator = new Random();
                if ((int) (board1[i][j] / 10) == 1) {
                    n = RangedRand(4);
                    switch (n) {
                        case 1:
                            pictPath = "src\\1.1.jpg";
                            board1[i][j] = 11;
                            break;
                        case 2:
                            pictPath = "src\\1.2.jpg";
                            board1[i][j] = 12;
                            break;
                        case 3:
                            pictPath = "src\\1.3.jpg";
                            board1[i][j] = 13;
                            break;
                        case 0:
                            pictPath = "src\\1.4.jpg";
                            board1[i][j] = 14;
                            break;
                    }
                }
                if ((int) (board1[i][j] / 10) == 2) {
                    n = RangedRand(2);
                    switch (n) {
                        case 1:
                            pictPath = "src\\2.1.jpg";
                            board1[i][j] = 21;
                            break;
                        case 0:
                            pictPath = "src\\2.2.jpg";
                            board1[i][j] = 22;
                            break;
                    }
                }
                if ((int) (board1[i][j] / 10) == 3) {
                    pictPath = "src\\3.1.jpg";
                }
                if ((int) (board1[i][j] / 10) == 4) {
                    n = RangedRand(2);
                    switch (n) {
                        case 1:
                            pictPath = "src\\4.1.jpg";
                            board1[i][j] = 41;
                            break;
                        case 0:
                            pictPath = "src\\4.2.jpg";
                            board1[i][j] = 42;
                            break;
                    }
                }
            }
    }

    public void fill() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                switch (board1[i][j]) {
                    case 11:
                        pictPath = "src\\1.1.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        break;
                    case 12:
                        pictPath = "src\\1.2.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 12;
                        break;
                    case 13:
                        pictPath = "src\\1.3.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 13;
                        break;
                    case 14:
                        pictPath = "src\\1.4.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 14;
                        break;
                    case 21:
                        pictPath = "src\\2.1.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 21;
                        break;
                    case 22:
                        pictPath = "src\\2.2.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 22;
                        break;
                    case 31:
                        pictPath = "src\\3.1.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 31;
                        break;
                    case 41:
                        pictPath = "src\\4.1.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 41;
                        break;
                    case 42:
                        pictPath = "src\\4.2.jpg";
                        map[i][j].setIcon(new ImageIcon(pictPath));
                        board1[i][j] = 42;
                        break;
                }
            }
    }

    private Cell getCell(JLabel l) {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                if (map[i][j] == l)
                    return new Cell(i, j);

            }
        throw new IllegalArgumentException("Cannot find this JLabel object in map[][]");
    }

    private int RangedRand(int k) {
        return (generator.nextInt(k));
    }

    public void turn(int x, int y) {
        switch (board1[x][y]) {
            case 11:
                board1[x][y] = 12;
                pictPath = "src\\1.2.jpg";
                imageIcon = new ImageIcon(pictPath);

                map[x][y].setIcon(imageIcon);
                break;
            case 12:
                board1[x][y] = 13;
                pictPath = "src\\1.3.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 13:
                board1[x][y] = 14;
                pictPath = "src\\1.4.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 14:
                board1[x][y] = 11;
                pictPath = "src\\1.1.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 21:
                board1[x][y] = 22;
                pictPath = "src\\2.2.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 22:
                board1[x][y] = 21;
                pictPath = "src\\2.1.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 31:
                board1[x][y] = 31;
                pictPath = "src\\3.1.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 41:
                board1[x][y] = 42;
                pictPath = "src\\4.2.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
            case 42:
                board1[x][y] = 41;
                pictPath = "src\\4.1.jpg";
                imageIcon = new ImageIcon(pictPath);
                map[x][y].setIcon(imageIcon);
                break;
        }
    }


}
