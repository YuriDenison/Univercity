import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

/*
*   no comment: class board
*/
public class Board {

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

    // KeyListener to panel
    private class Key implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                if ((cur.getY() - 1) >= 0) {
                    if (board1[cur.getX()][cur.getY() - 1] == 2) {
                        win();
                    } else {
                        if (board1[cur.getX()][cur.getY() - 1] != 1) {
                            if (cur.getX() == 0 && cur.getY() == 0) {
                                map[cur.getX()][cur.getY()].setBackground(START);
                                board1[cur.getX()][cur.getY()] = 3;
                                cur.setY(cur.getY() - 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            } else {
                                map[cur.getX()][cur.getY()].setBackground(POLE);
                                board1[cur.getX()][cur.getY()] = 0;
                                cur.setY(cur.getY() - 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            }
                        }
                    }
                }
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                if ((cur.getY() + 1) < COLUMNS) {
                    if (board1[cur.getX()][cur.getY() + 1] == 2) {
                        win();
                    } else {
                        if (board1[cur.getX()][cur.getY() + 1] != 1) {
                            if (cur.getX() == 0 && cur.getY() == 0) {
                                map[cur.getX()][cur.getY()].setBackground(START);
                                board1[cur.getX()][cur.getY()] = 3;
                                cur.setY(cur.getY() + 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                cur.setX(sx);
                                cur.setY(sy);
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            } else {
                                map[cur.getX()][cur.getY()].setBackground(POLE);
                                board1[cur.getX()][cur.getY()] = 0;
                                cur.setY(cur.getY() + 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            }
                        }
                    }
                }
            }
            if (keyCode == KeyEvent.VK_UP) {
                if ((cur.getX() - 1) >= 0) {
                    if (board1[cur.getX() - 1][cur.getY()] == 2) {
                        win();
                    } else {
                        if (board1[cur.getX() - 1][cur.getY()] != 1) {
                            if (cur.getX() == 0 && cur.getY() == 0) {
                                map[cur.getX()][cur.getY()].setBackground(START);
                                board1[cur.getX()][cur.getY()] = 3;
                                cur.setX(cur.getX() - 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            } else {
                                map[cur.getX()][cur.getY()].setBackground(POLE);
                                board1[cur.getX()][cur.getY()] = 0;
                                cur.setX(cur.getX() - 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            }
                        }
                    }
                }
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                if ((cur.getX() + 1) < ROWS) {
                    if (board1[cur.getX() + 1][cur.getY()] == 2) {
                        win();
                    } else {
                        if (board1[cur.getX() + 1][cur.getY()] != 1) {

                            if (cur.getX() == 0 && cur.getY() == 0) {
                                map[cur.getX()][cur.getY()].setBackground(START);
                                board1[cur.getX()][cur.getY()] = 3;
                                cur.setX(cur.getX() + 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            } else {
                                map[cur.getX()][cur.getY()].setBackground(POLE);
                                board1[cur.getX()][cur.getY()] = 0;
                                cur.setX(cur.getX() + 1);
                                map[cur.getX()][cur.getY()].setBackground(CURENT);
                                board1[cur.getX()][cur.getY()] = 4;
                                sx = cur.getX();
                                sy = cur.getY();
                                turns++;
                                lev.setText("Level " + level + "; " + turns + " turns");
                            }

                        }
                    }
                }
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

    }


    private final int ROWS, COLUMNS;
    private static Color POLE;// = Color.GREEN;
    private static Color CURENT;// = Color.RED;
    private static Color START;// = Color.BLUE;
    private static Color FINISH;// = Color.BLUE;
    private static Color BLOCK;// = Color.BLACK;
    private static Color CWAY;// = Color.YELLOW;
    public static final short EMPTY = 0;
    private JLabel[][] map;
    private Integer[][] board1;
    private int sx, sy;
    private int level, turns = 0, level_turn = 0;
    private Pair cur = new Pair();
    private JTextField lev;
    private JPanel pan;
    private Board b;
    private String name;
    private String[] names = new String[9];
    private int[] score = new int[9];
    private Rezults rezul = new Rezults();
    private Key k;

    private Logic l;

    // create map of JLabel
    public Board(JPanel panel, int level, int r, int c, JTextField text, String name) {
        ROWS = r;
        COLUMNS = c;
        this.name = name;
        this.level = level;
        this.pan = panel;
        this.lev = text;

        panel.removeAll();
        panel.setLayout(new GridLayout(r, c));
        map = new JLabel[r][c];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                JLabel l = new JLabel("");
                l.setBackground(POLE);
                l.setOpaque(true);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setBorder(new LineBorder(Color.GRAY));
                l.setFont(new Font(null, Font.BOLD, 30));
                l.setMinimumSize(new Dimension(1, 1));
                map[i][j] = l;
                panel.add(l);
            }

        if (k != null) panel.removeKeyListener(k);
        k = new Key();
        panel.addKeyListener(k);
        panel.setFocusable(true);
        panel.requestFocus();
    }

    // reading board depending on the level + choosing colors
    public void setBoard(Color POLE, Color CURENT, Color START, Color FINISH, Color BLOCK, Color CWAY) {
        this.POLE = POLE;
        this.CURENT = CURENT;
        this.START = START;
        this.FINISH = FINISH;
        this.BLOCK = BLOCK;
        this.CWAY = CWAY;

        String file = "";
        if (level == 1) file = "src/Level_1.txt";
        if (level == 2) file = "src/Level_5.txt";
        if (level == 3) file = "src/Level_4.txt";
        if (level == 4) file = "src/Level_2.txt";
        if (level == 5) file = "src/Level_3.txt";
        board1 = new Integer[50][50];
        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(file)));
            String line;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    line = reader.readLine();
                    double scan = Double.parseDouble(line);
                    board1[i][j] = (int) scan;
                    if (board1[i][j] == 3) {
                        cur.setX(i);
                        cur.setY(j);
                        sx = i;
                        sy = j;
                    }
                }
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println("I/O");
        }

        fill();
    }

    // filling map with correct colors
    private void fill() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                if (board1[i][j] == 0) map[i][j].setBackground(POLE);
                if (board1[i][j] == 1) map[i][j].setBackground(BLOCK);
                if (board1[i][j] == 2) {
                    map[i][j].setBackground(FINISH);
                    map[i][j].setForeground(Color.WHITE);
                    map[i][j].setText("F");
                }
                if (board1[i][j] == 3) {
                    map[i][j].setBackground(START);
                    map[i][j].setForeground(Color.WHITE);
                    map[i][j].setText("S");
                }
                if (board1[i][j] == 4) {
                    map[i][j].setBackground(CURENT);
                    cur.setX(i);
                    cur.setY(j);
                }
            }
    }

    private void show(String a) {
        JOptionPane.showMessageDialog(null, a);
    }

    // fuction which uses in KeyListener: get curent Pair
    private Cell getCell(JLabel l) {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                if (map[i][j] == l)
                    return new Cell(i, j);
            }
        throw new IllegalArgumentException("Cannot find this JLabel object in map[][]");
    }

    // if we
    public void Log(Pair c) {
        fill();
        l = new Logic(board1, new Pair(sx, sy), new Pair(49, 49));
        if ((!l.getResult())) {
            show("No way. Start a new game");
        } else {
            for (int i = 0; i < l.getWay().length - 1; i++) {
                map[l.getWay()[i].getX()][l.getWay()[i].getY()].setBackground(CWAY);
            }
        }


        turns += 100;
        lev.setText("Level " + level + "; " + turns + " turns");
        pan.requestFocus();
    }

    public Integer[][] getBoard() {
        return board1;
    }


    private void win() {
        level++;
        level_turn += turns;
        turns = 0;
        cur.setX(0);
        cur.setY(0);
        sx = 0;
        sy = 0;
        lev.setText("Level " + level + "; " + turns + " turns");
        switch (level) {
            case 2:
                show("You win the stage, try next stage ");
                setBoard(new Color(51, 204, 255), Color.RED, Color.GREEN,
                        Color.GREEN, Color.BLACK, Color.YELLOW);

                break;
            case 3:
                show("You win the stage, try next stage ");
                setBoard(new Color(255, 153, 255), Color.RED, Color.GREEN,
                        Color.GREEN, Color.BLACK, Color.YELLOW);

                break;
            case 4:
                show("You win the stage, try next stage ");
                setBoard(new Color(204, 255, 102), Color.RED, Color.GREEN,
                        Color.GREEN, Color.BLACK, new Color(0, 204, 204));
                break;
            case 5:
                show("You win the stage, try next stage ");
                setBoard(new Color(204, 255, 102), Color.RED, Color.GREEN,
                        Color.GREEN, Color.BLACK, new Color(0, 204, 204));
                break;
        }
        if (level == 6) {
            show(" Congratulations! You have won ... Here are your results");
            _Read();
            DoRez();
            rezul.getPanel().setBackground(new Color(204, 204, 0));
            rezul.show();
        }

    }

    public void show_rez() {
        _Read();
        rezul.set(names, score);
        rezul.getPanel().setBackground(new Color(204, 204, 0));
        rezul.show();
    }

    private void DoRez() {
        if (level_turn <= score[1]) setSN(1);
        if (level_turn > score[1] && level_turn <= score[2]) setSN(2);
        if (level_turn > score[2] && level_turn <= score[3]) setSN(3);
        if (level_turn > score[3] && level_turn <= score[4]) setSN(4);
        if (level_turn > score[4] && level_turn <= score[5]) setSN(5);
        if (level_turn > score[5] && level_turn <= score[6]) setSN(6);
        if (level_turn > score[6] && level_turn <= score[7]) setSN(7);
        if (level_turn > score[7] && level_turn <= score[8]) setSN(8);

        rezul.set(names, score);
        try {
            FileWriter writer = new FileWriter("src/Rez.txt");
            BufferedWriter buffered = new BufferedWriter(writer);
            for (int i = 0; i < 8; i++) {
                buffered.write(names[i] + "\n");
                buffered.write(score[i] + "\n");
            }

            buffered.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void setSN(int a) {
        for (int i = 8; i > a; i--) score[i] = score[i - 1];
        score[a] = level_turn;
        for (int i = 8; i > a; i--) names[i] = names[i - 1];
        names[a] = name;
    }

    private void _Read() {
        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream("src/Rez.txt")));
            for (int i = 0; i < 8; i++) {
                names[i] = reader.readLine();
                score[i] = Integer.parseInt(reader.readLine());
            }

            reader.close();
        }
        catch (IOException e) {
            System.out.println("I/O");
        }
    }

    public Pair getCur() {
        return cur;
    }
}
