package game;

import java.util.Vector;

public class Line
{
    public static double INF = 1e20;
    private final int LEN;
    private int direction;
    private int count0 , count1;

    public Line(int len, int direction) {
        super();
        this.LEN = len;
        this.direction = direction;
        count0 = count1 = 0;
    }

    public void attach(Vector<Line>[][] l, int x, int y, int a, int b) {
        for (int i = 0; i < LEN; i++) {
            l[x + i * a][y + i * b].add(this);
        }
    }

    public void inc0() {
        if (count0 != -1) {
            count0++;
        }
    }

    public void disable0() {
        count0 = -1;
    }

    public double getCount0() {
        if(count0 == LEN - 1)
            return INF ;
        if(count0 == LEN - 2)
            return LEN * LEN * LEN;
        return count0 + 1;
    }

    public void inc1() {
        if (count1 != -1) {
            count1++;
        }
    }

    public void disable1() {
        count1 = -1;
    }

    public double getCount1() {
        if(count1 == LEN - 1)
            return INF ;
        if(count1 == LEN - 2)
            return LEN * LEN * LEN;
        return count1 + 1;
    }

    public int getDirection()
    {
        return direction;
    }
}
