import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 23.04.2009
 * Time: 9:53:14
 * To change this template use File | Settings | File Templates.
 */
public class Redaktor {

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
            if (state == 0) {
                Cell p = getCell((JLabel) (e.getComponent()));
                move(st, p.x, p.y);
                if (st == 1 || st == 2)
                    st++;
            }
            if (state == 1) {
                Cell p = getCell((JLabel) (e.getComponent()));
                move(st, p.x, p.y);
                if (st == 1 || st == 2)
                    st++;
                fill();
                state = 0;
            }
        }

    }

    private final int ROWS, COLUMNS;
    private static final Color BACKGROUND = Color.GREEN;
    private static final Color BLOCK = Color.BLACK;
    private static final Color START = Color.BLUE;
    private static final Color FINISH = Color.BLUE;
    public static final short EMPTY = 0;
    private JLabel[][] map;
    private JPanel panel;
    private Integer[][] board1;
    private Listener listener;
    private int st, fx, fy, sx, sy;
    private JTextField text;
    private int state = 0;

    public void setState(int s) {
        this.state = s;
    }


    public Redaktor(JPanel panel, int r, int c, JTextField text) {
        ROWS = r;
        COLUMNS = c;
        //text.setText("Choose start position of robot :) ");
        st = 1;
        listener = new Listener();
        map = new JLabel[r][c];
        board1 = new Integer[r][c];
        this.panel = panel;
        panel.removeAll();
        panel.setLayout(new GridLayout(r, c));
        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream("src/Level_5.txt")));
            String line;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    line = reader.readLine();
                    double scan = Double.parseDouble(line);
                    board1[i][j] = (int) scan;

                }
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println("I/O");
        }
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                JLabel l = new JLabel("");
                l.setOpaque(true);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.addMouseListener(listener);
                l.setBorder(new LineBorder(Color.GRAY));
                l.setFont(new Font(null, Font.BOLD, 30));
                l.setMinimumSize(new Dimension(1, 1));
                map[i][j] = l;

                panel.add(l);
            }
        fill();
    }

    private void fill() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                if (board1[i][j] != 1) map[i][j].setBackground(BACKGROUND);
                if (board1[i][j] == 1) map[i][j].setBackground(Color.BLACK);
            }
    }

    private void show(String a) {
        JOptionPane.showMessageDialog(null, a);
    }

    private Cell getCell(JLabel l) {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                if (map[i][j] == l)
                    return new Cell(i, j);
        throw new IllegalArgumentException("Cannot find this JLabel object in map[][]");
    }

    public void move(int st, int x, int y) {
        if (st == 3) {
            if (board1[x][y] == 1) {
                map[x][y].setBackground(Color.GREEN);
                board1[x][y] = 0;
                return;
            }
            if (board1[x][y] == 0){
                map[x][y].setBackground(BLOCK);
                board1[x][y] = 1;
                return;
            }
        }
        if (st == 2) {
            if (board1[x][y] != 0)
                return;
            map[x][y].setBackground(FINISH);
            map[x][y].setForeground(Color.WHITE);
            map[x][y].setText("F");
            board1[x][y] = 2;
            fx = x;
            fy = y;
        }
        if (st == 1) {
            map[x][y].setBackground(START);
            map[x][y].setForeground(Color.WHITE);
            map[x][y].setText("S");
            board1[x][y] = 3;
            sx = x;
            sy = y;
        }
    }

    public void Log(int r, int c) {
        fill();
        Logic l = new Logic(board1, sx, sy, fx, fy);
        Pair way[] = l.getWay();
        if ((!l.getResult())) {
            show("No way. Start a new game");
        } else {
            for (int i = 0; i < way.length - 1; i++) {
                map[way[i].getX()][way[i].getY()].setBackground(Color.YELLOW);
            }
        }
    }

    public Integer[][] getBoard(){
        return board1;
    }
}
