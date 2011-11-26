import java.util.LinkedList;
import java.util.Queue;

/*
*    Breadth-first search
 */

public class Logic {

    private static final int[] dx = new int[]{1, -1, 0, 0};
    private static final int[] dy = new int[]{0, 0, 1, -1};
    private static final int MOVES = 4;

    private Pair[][] prev;
    private Pair[] Way;
    private int[][] col;
    private int[][] dist;
    private boolean result;

    public Logic(Integer[][] board, int sx, int sy, int fx, int fy) {
        this(board, new Pair(sx, sy), new Pair(fx, fy));
    }


    public Logic(Integer[][] board, Pair start, Pair end) {
        Queue<Pair> q = new LinkedList<Pair>();
        q.add(start);
        int r = board.length;
        int c = board[0].length;
        col = new int[r][c];
        dist = new int[r][c];
        prev = new Pair[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                col[i][j] = 0;
                prev[i][j] = null;
            }
        }

        while (!q.isEmpty()) {
            Pair cur = q.remove();
            for (int i = 0; i < MOVES; i++) {
                if (((cur.getX() + dx[i]) >= 0) && ((cur.getY() + dy[i]) >= 0) &&
                        ((cur.getX() + dx[i]) < r) && ((cur.getY() + dy[i]) < c)) {
                    Pair next = new Pair(cur.getX() + dx[i], cur.getY() + dy[i]);
                    if (board[next.getX()][next.getY()] != 1 &&
                            col[next.getX()][next.getY()] == 0) {
                        prev[next.getX()][next.getY()] = cur;
                        col[next.getX()][next.getY()] = 1;
                        dist[next.getX()][next.getY()] = dist[cur.getX()][cur.getY()] + 1;
                        q.add(next);
                    }
                }
            }
            col[cur.getX()][cur.getY()] = 2;
        }
        Pair cur1 = end;
        if (prev[cur1.getX()][cur1.getY()] == null) {
            result = false;
        } else {
            Way = new Pair[dist[end.getX()][end.getY()]];
            result = true;
            for (int i = 0; i < dist[end.getX()][end.getY()]; i++) {
                Way[i] = prev[cur1.getX()][cur1.getY()];
                cur1 = prev[cur1.getX()][cur1.getY()];
            }

        }
    }
// no comment: get shortest Way
    public Pair[] getWay() {
        return Way;
    }
// getResult: is there any way?
    public boolean getResult() {
        return result;
    }
}