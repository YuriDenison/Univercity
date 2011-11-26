import javax.swing.*;
import java.awt.*;

public class Logic {
    private int in = 1;
    private int out = 3;
    public int res=1;
    //private Pair bum;
    private Graphics gr;
    Rectangle a,b;
    private JLabel[][] map;
    boolean crest=false;

    private Pair[] k = new Pair[7];
    //private MainForm frame;


    public void setK() {
        k[1] = new Pair(1, 2);
        k[2] = new Pair(2, 3);
        k[3] = new Pair(1, 4);
        k[4] = new Pair(3, 4);
        k[5] = new Pair(1, 3);
        k[6] = new Pair(2, 4);
    }


   /*public void paint(int x1, int y1, int x2, int y2, final Graphics g) {
        Rectangle a = map[x1][y1].getBounds();
        Rectangle b = map[x2][y2].getBounds();
        /*Pair p1 = new Pair( (int)b.getCenterX(), (int)b.getCenterY()  );
        Pair p2 = new Pair( (int)b.getCenterX(), (int)b.getCenterY() );  *
        g.drawLine((int) a.getCenterX(), (int) a.getCenterY(), (int) b.getCenterX(), (int) b.getCenterY());
    }*/


    public Logic(Pair end, Integer[][] board,JLabel[][] map,final Graphics g) throws InterruptedException {
        setK();

        int i = 0;
        int j = 0;
        this.map=map;

        Pair bum = new Pair(10,10);
        Pair next = new Pair(0, 0);
        boolean crest[][] = new boolean[10][10];
        while ((next != end) || (next != bum)) {
            switch (out) {
                case 1:
                    in = 3;
                    break;
                case 2:
                    in = 4;
                    break;
                case 3:
                    in = 1;
                    break;
                case 4:
                    in = 2;
                    break;
            }
            switch (board[i][j]) {
                case 11:
                    if (in == k[3].getX()) {
                        out = k[3].getY();
                        map[i][j].setIcon(new ImageIcon("src\\1.1.l.jpg"));
                        break;
                    }
                    if (in == k[3].getY()) {
                        out = k[3].getX();
                        map[i][j].setIcon(new ImageIcon("src\\1.1.l.jpg"));
                        break;
                    }
                    next = bum;

                    break;
                    //map[i][j].setIcon(new ImageIcon("src\\1.1.l.jpg"));
                case 12:
                    if (in == k[1].getX()) {
                        out = k[1].getY();
                        map[i][j].setIcon(new ImageIcon("src\\1.2.l.jpg"));
                        break;
                    }
                    if (in == k[1].getY()) {
                        out = k[1].getX();
                        map[i][j].setIcon(new ImageIcon("src\\1.2.l.jpg"));
                        break;
                    }
                    next = bum;

                    break;
                case 13:
                    if (in == k[2].getX()) {
                        out = k[2].getY();
                        map[i][j].setIcon(new ImageIcon("src\\1.3.l.jpg"));
                        break;
                    }
                    if (in == k[2].getY()) {
                        out = k[2].getX();
                        map[i][j].setIcon(new ImageIcon("src\\1.3.l.jpg"));
                        break;
                    }
                    next = bum;
                    break;
                case 14:
                    if (in == k[4].getX()) {
                        out = k[4].getY();
                        map[i][j].setIcon(new ImageIcon("src\\1.4.l.jpg"));
                        break;
                    }
                    if (in == k[4].getY()) {
                        out = k[4].getX();
                        map[i][j].setIcon(new ImageIcon("src\\1.4.l.jpg"));
                        break;
                    }
                    next = bum;
                    break;
                case 21:
                    if (in == k[6].getX()) {
                        out = k[6].getY();
                        map[i][j].setIcon(new ImageIcon("src\\2.1.l.jpg"));
                        break;
                    }
                    if (in == k[6].getY()) {
                        out = k[6].getX();
                        map[i][j].setIcon(new ImageIcon("src\\2.1.l.jpg"));
                        break;
                    }
                    next = bum;
                    break;
                case 22:
                    if (in == k[5].getX()) {
                        out = k[5].getY();
                        map[i][j].setIcon(new ImageIcon("src\\2.2.l.jpg"));
                        break;
                    }
                    if (in == k[5].getY()) {
                        out = k[5].getX();
                        map[i][j].setIcon(new ImageIcon("src\\2.2.l.jpg"));
                        break;
                    }
                    next = bum;
                    break;
                case 31:
                    if (in == k[5].getX()) {
                        out = k[5].getY();
                        if(!crest[i][j])
                        {
                        map[i][j].setIcon(new ImageIcon("src\\3.1.1.l.jpg"));
                        crest[i][j]=true;
                        }
                        else
                        map[i][j].setIcon(new ImageIcon("src\\3.1.3.jpg"));
                        break;
                    }
                    if (in == k[5].getY()) {
                        out = k[5].getX();
                        if(!crest[i][j])
                        {
                        map[i][j].setIcon(new ImageIcon("src\\3.1.1.l.jpg"));
                        crest[i][j]=true;
                        }
                        else
                        map[i][j].setIcon(new ImageIcon("src\\3.1.3.jpg"));
                        break;
                    }
                    if (in == k[6].getX()) {
                        out = k[6].getY();
                        if(!crest[i][j])
                        {
                        map[i][j].setIcon(new ImageIcon("src\\3.1.2.l.jpg"));
                        crest[i][j]=true;
                        }
                        else
                        map[i][j].setIcon(new ImageIcon("src\\3.1.3.jpg"));
                        break;
                    }
                    if (in == k[6].getY()) {
                        out = k[6].getX();
                        if(!crest[i][j])
                        {
                        map[i][j].setIcon(new ImageIcon("src\\3.1.2.l.jpg"));
                        crest[i][j]=true;
                        }
                        else
                        map[i][j].setIcon(new ImageIcon("src\\3.1.3.jpg"));
            
                        break;
                    }
                    next = bum;
                    break;
                case 41:
                    if (in == k[1].getX()) {
                        out = k[1].getY();
                        map[i][j].setIcon(new ImageIcon("src\\4.1.2.l.jpg"));
                        break;
                    }
                    if (in == k[1].getY()) {
                        out = k[1].getX();
                        map[i][j].setIcon(new ImageIcon("src\\4.1.2.l.jpg"));
                        break;
                    }
                    if (in == k[4].getX()) {
                        out = k[4].getY();
                        map[i][j].setIcon(new ImageIcon("src\\4.1.1.l.jpg"));
                        break;
                    }
                    if (in == k[4].getY()) {
                        out = k[4].getX();
                        map[i][j].setIcon(new ImageIcon("src\\4.1.1.l.jpg"));
                        break;
                    }
                    next = bum;
                    break;
                case 42:
                    if (in == k[2].getX()) {
                        out = k[2].getY();
                        map[i][j].setIcon(new ImageIcon("src\\4.2.2.l.jpg"));
                        break;
                    }
                    if (in == k[2].getY()) {
                        out = k[2].getX();
                        map[i][j].setIcon(new ImageIcon("src\\4.2.2.l.jpg"));
                        break;
                    }
                    if (in == k[3].getX()) {
                        out = k[3].getY();
                        map[i][j].setIcon(new ImageIcon("src\\4.2.1.l.jpg"));
                        break;
                    }
                    if (in == k[3].getY()) {
                        out = k[3].getX();
                        map[i][j].setIcon(new ImageIcon("src\\4.2.1.l.jpg"));
                        break;
                    }
                    next = bum;
                    break;

            }
            if (next == bum) {
                res = 1;
            }
            switch (out) {
                case 1:
                    i = i - 1;
                    if ((i < 0) || (i > 10)) {
                        next = bum;
                        break;
                    }

                    break;
                case 2:
                    j = j + 1;
                    if ((j < 0) || (j > 10)) {
                        next = bum;
                        break;
                    }
                    break;
                case 3:
                  i = i + 1;
                    if ((i < 0) || (i > 10)) {
                        next = bum;
                        break;
                    }
                    break;
                case 4:
                    j = j - 1;
                    if ((j < 0) || (j > 10)) {
                        next = bum;
                        break;
                    }

                    break;

            }
            if(next==bum)
            {
                break;
            }
            System.out.println(""+in);
            System.out.println(""+out);
            System.out.println(""+i+j);
            System.out.println(""+res);
            System.out.println("\n");

            if (next != bum) {
            }

            if ((i == 9) && (j == 9)) {
                if ((out == end.getX())||(out==end.getY())) {
                    //if(level==1)
                    map[i][j].setIcon(new ImageIcon("src\\1.2.l.jpg"));
                    res = 0;
                    break;
                   
                }
            }
        }
    }



}

