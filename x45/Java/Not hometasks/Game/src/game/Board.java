package game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Board
{

    public static final class Cell
    {
        public int a;
        public int b;
        public Cell(){}
        public Cell(int a , int b)
        {
            this.a = a;
            this.b = b;
        }
    }

    class Listener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!gameIsRunning)
                return;
            Cell p = getCell((JLabel)(e.getComponent()));
            if(board[p.a][p.b] != EMPTY)
                return;
            move(0 , p.a , p.b);
            logic.setMove(p.a, p.b);
            if(checkWin(p.a , p.b))
            {
                show("You wins!!!");
                return;
            }
            if(cellsUsed < ROWS * COLUMNS)
            {
                Cell tmp = logic.getMove();
                move(1 , tmp.a , tmp.b);
                if(checkWin(tmp.a , tmp.b))
                    show("Computer wins");
            }
            if(cellsUsed == ROWS * COLUMNS)
                show("Draw in this game");
        }

    }

    private final int ROWS , COLUMNS , LEN;
    private static final Color BACKGROUND = Color.WHITE;
    private static final Color LM_BACKGROUND = Color.YELLOW;
    public static final short EMPTY = 0 , PL0 = 1 , PL1 = 2;
    private static final char []PLAYERS = {'X' , 'O'};
    private Listener listener;
    private JLabel [][]map;
    private JPanel panel;
    private Logic logic;
    private short [][]board;
    private int cellsUsed;
    private boolean gameIsRunning;


    private int lx[] , ly[]; // Coordinates of last moves of first and second players (-1 if no move made yet)

    public Board(JPanel panel , int r , int c , int len , boolean makeMove)
    {
        ROWS = r;
        COLUMNS = c;
        LEN = len;
        lx = new int[2];
        ly = new int[2];
        lx[0] = ly[0] = -1;
        lx[1] = ly[1] = -1;
        gameIsRunning = true;
        cellsUsed = 0;
        map = new JLabel[r][c];
        board = new short[r][c];
        listener = new Listener();
        this.panel = panel;
        panel.removeAll();
        panel.setLayout(new GridLayout(r , c));
        for(int i = 0 ; i < r ; i++)
            for(int j = 0 ; j < c ; j++)
            {
                JLabel l = new JLabel("");
                l.setBackground(BACKGROUND);
                l.setOpaque(true);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.addMouseListener(listener);
                l.setBorder(new LineBorder(Color.GRAY));
                l.setFont(new Font(null , Font.BOLD  , 30));
                l.setMinimumSize(new Dimension(1 , 1));
                map[i][j] = l;
                board[i][j] = EMPTY;
                panel.add(l);
            }
        if(makeMove)
            move(1 , ROWS / 2 , COLUMNS / 2);
        logic = new Logic(board , ROWS , COLUMNS , LEN);
    }

    private void show(String a)
    {
        JOptionPane.showMessageDialog(null, a);
        gameIsRunning = false;
    }

    private boolean checkWin(int x , int y)
    {
        return countCells(x , y , 0 , 1) >= LEN ||
                   countCells(x , y , 1 , 1) >= LEN ||
                   countCells(x , y , 1 , 0) >= LEN ||
                   countCells(x , y , 1 , -1) >= LEN;
    }

    private boolean isBetween(int a , int b , int c)
    {
        return (a <= b && b < c) ||
                   (c <= b && b < a);
    }

    private int countCells(int x , int y , int a , int b)
    {
        int m = 0 , n = 0;
        while(isBetween(0 , x - m * a , ROWS)  && isBetween(0 , y - m * b , COLUMNS))
        {
            if(board[x - m * a][y - m * b] != board[x][y])
                break;
            else
                m++;
        }
        while(isBetween(0 , x + n * a , ROWS)  && isBetween(0 , y + n * b , COLUMNS))
        {
            if(board[x + n * a][y + n * b] != board[x][y])
                break;
            else
                n++;
        }
        return m + n - 1;
    }

    private Cell getCell(JLabel l)
    {
        for(int i = 0 ; i < ROWS ; i++)
            for(int j = 0 ; j < COLUMNS ; j++)
                if(map[i][j] == l)
                    return new Cell(i , j);
        throw new IllegalArgumentException("Cannot find this JLabel object in map[][]");
    }

    public void move(int pl , int x , int y)
    {
        char ch;
        if(pl != 0 && pl != 1)
            throw new IllegalArgumentException("Variable pl should be 0 or 1 (not " +pl + ").");
        //if(board[x][y] != EMPTY)
        //    throw new IllegalArgumentException("(x , y) should be empty cell.");
        ch = PLAYERS[pl];
        if(lx[pl] != -1)
        {
            map[lx[pl]][ly[pl]].setBackground(BACKGROUND);
        }
        lx[pl] = x;
        ly[pl] = y;
        map[x][y].setBackground(LM_BACKGROUND);
        map[x][y].setText("" + ch);
        board[x][y] = (pl == 0) ? PL0 : PL1;
        cellsUsed++;
    }

}
