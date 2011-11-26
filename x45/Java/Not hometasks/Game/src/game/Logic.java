package game;

import java.util.Vector;

public class Logic
{
    private final long AT = 2 , DEF = 1;
    private final int ROWS , COLUMNS , LEN;
    private short [][]board;
    private long [][]moves;
    private Vector<Line> moveLines[][];

    public Logic(short [][]board , int rows , int columns , int len)
    {
        ROWS = rows;
        COLUMNS = columns;
        LEN = len;
        this.board = board;
        moves = new long[ROWS][COLUMNS];
        moveLines = new Vector [ROWS][COLUMNS];
        for(int i = 0 ; i < ROWS ; i++)
            for(int j = 0 ; j < COLUMNS ; j++)
                moveLines[i][j] = new Vector<Line>();
        for(int i = 0 ; i < ROWS - LEN + 1; i++)
            for(int j = 0 ; j < COLUMNS ; j++)
                (new Line(LEN , 0)).attach(moveLines, i, j, 1, 0);
        for(int i = 0 ; i < ROWS ; i++)
            for(int j = 0 ; j < COLUMNS - LEN + 1; j++)
                (new Line(LEN , 1)).attach(moveLines, i, j, 0, 1);
        for(int i = 0 ; i < ROWS - LEN + 1 ; i++)
            for(int j = 0 ; j < COLUMNS - LEN + 1; j++)
                (new Line(LEN , 2)).attach(moveLines, i, j, 1, 1);
        for(int i = LEN - 1 ; i < ROWS ; i++)
            for(int j = 0 ; j < COLUMNS - LEN + 1; j++)
                (new Line(LEN , 3)).attach(moveLines, i, j, -1, 1);

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

    private void update(int x , int y)
    {
        int a1 , b1 , a2 , b2;
        a1 = (x - LEN >= 0) ? x - LEN : 0;
        b1 = (x + LEN < ROWS) ? x + LEN : ROWS;
        a2 = (y - LEN >= 0) ? y - LEN : 0;
        b2 = (y + LEN < COLUMNS) ? y + LEN : COLUMNS;
        for(int i = a1 ; i < b1 ; i++)
            for(int j = a2 ; j < b2 ; j++)
            {
                if(board[i][j] != Board.EMPTY)
                    continue;
                long at = 0 , def = 0;
                double tmp[] = new double[4];
                tmp[0] = tmp[1] = tmp[2] = tmp[3] = 0;
                for(Line l : moveLines[i][j])
                    tmp[l.getDirection()] += l.getCount1() * 10000;
                at += (long)(Math.sqrt(tmp[0]) + Math.sqrt(tmp[1]) + Math.sqrt(tmp[2]) + Math.sqrt(tmp[3]));
                tmp[0] = tmp[1] = tmp[2] = tmp[3] = 0;
                for(Line l : moveLines[i][j])
                    tmp[l.getDirection()] += l.getCount0() * 10000;
                def += (long)(Math.sqrt(tmp[0]) + Math.sqrt(tmp[1]) + Math.sqrt(tmp[2]) + Math.sqrt(tmp[3]));
                moves[i][j] = at * AT + def * DEF;
            }
    }

    public void setMove(int x , int y)
    {
        for(Line l : moveLines[x][y])
        {
            l.inc0();
            l.disable1();
        }
        update(x , y);
    }

    public Board.Cell getMove()
    {
        int xb = 0 , yb = 0;
        for(xb = 0 ; xb < ROWS ; xb++)
        {
            for(yb = 0 ; yb < COLUMNS ; yb++)
                if(board[xb][yb] == Board.EMPTY)
                    break;
            if(yb != COLUMNS)
                break;
        }
        for(int i = 0 ; i < ROWS ; i++)
            for(int j = 0 ; j < COLUMNS ; j++)
                if(board[i][j] == Board.EMPTY && moves[xb][yb] < moves[i][j])
                {
                    xb = i;
                    yb = j;
                }
        for(Line l : moveLines[xb][yb])
        {
            l.inc1();
            l.disable0();
        }
        board[xb][yb] = Board.PL1;
        update(xb , yb);
        return new Board.Cell(xb , yb);
    }

}
