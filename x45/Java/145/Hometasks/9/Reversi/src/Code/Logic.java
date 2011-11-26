package Code;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Class.forName;

/**
 * User: Volkman
 * Date: 29.04.2010
 * Time: 2:05:44
 */
public class Logic {
    private int[][] board;
    private int ROWS, COLUMNS;
    private int[] sumPlayer;
    private int curPlayerCourse;
    private static final int[] dx = new int[]{1, 1, 0, -1, -1, -1, 0, 1};
    private static final int[] dy = new int[]{0, -1, -1, -1, 0, 1, 1, 1};
    private static final int MOVES = 8;

    public Logic(int ROWS, int COLUMNS) {
        this.ROWS = ROWS;
        this.COLUMNS = COLUMNS;
        board = new int[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = 0;
            }
        }
        board[ROWS/2][COLUMNS/2 - 1] = 1;
        board[ROWS/2 - 1][COLUMNS/2] = 1;
        board[ROWS/2 - 1][COLUMNS/2 - 1] = 2;
        board[ROWS/2][COLUMNS/2] = 2;
        curPlayerCourse = 1;
        sumPlayer = new int[3];
        sumPlayer[1] = 2;
        sumPlayer[2] = 2;
    }

    public static Cell botCourse(Logic log, String filename, int curPlayer) {
        try {
            IBot obj = (IBot) forName(filename).newInstance();
            return obj.getPos(log, curPlayer);
        }
        catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            System.exit(0);
        }
        catch (IllegalAccessException e1) {
            System.out.println("IllegalAccessException");
            System.exit(0);
        }
        catch (InstantiationException e) {
            System.out.println("InstantiationException");
            System.exit(0);
        }
        return new Cell(-1, -1);
    }

    public static String[] getBotList() {
        String[] helpBotList = new String[20];
        String[] botList = null;

        String file = "src/botList.txt";
        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(file)));
            int botListLengh = 0;
            String line = reader.readLine();
            while (line != null) {
                helpBotList[botListLengh] = line;
                botListLengh++;
                line = reader.readLine();
            }
            reader.close();

            botList = new String[botListLengh];
            System.arraycopy(helpBotList, 0, botList, 0, botListLengh);
        }
        catch (IOException e) {
            System.out.println("I/O");
        }
        return botList;
    }

    public boolean emptyCell(Cell p) {
        return board[p.x][p.y] == 0;
    }

    public boolean checkCourse(Cell p){
        for (int i = 0; i < MOVES; i++) {
            if (checkCurrentLine(p, i) && emptyCell(p)) {
                return true;
            }
        }
        return false;
    }

    public void setCourse(Cell course) {
        board[course.x][course.y] = curPlayerCourse;
        sumPlayer[curPlayerCourse]++;
        for (int i = 0; i < MOVES; i++) {
            if (checkCurrentLine(course, i)) {
                int x = course.x + dx[i];
                int y = course.y + dy[i];
                while (board[x][y] != curPlayerCourse) {
                    board[x][y] = curPlayerCourse;
                    sumPlayer[curPlayerCourse]++;
                    sumPlayer[(2 * curPlayerCourse) % 3]--;
                    x += dx[i];
                    y += dy[i];
                }
            }
        }
        curPlayerCourse = (2 * curPlayerCourse) % 3;
    }


    public int checkWin() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == 0)
                    return 0;
            }
        }
        if (sumPlayer[1] > sumPlayer[2])
            return 1;
        if (sumPlayer[1] < sumPlayer[2])
            return 2;
        return 3; // if(sumPlayer1 == sumPlayer2)
    }

    public boolean checkCourseAvailable(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (checkCourse(new Cell(i, j)))
                    return true;
            }
        }
        return false;
    }

    public boolean checkCurrentLine(Cell course, int i) {
        int x = course.x + dx[i];
        int y = course.y + dy[i];
        while (x >= 0 && y >= 0 && x < ROWS && y < COLUMNS) {
            if (board[x][y] == 0 ||
                    ( board[x][y] == curPlayerCourse && (x - course.x == dx[i]) && (y - course.y == dy[i]) ))
                return false;
            if (board[x][y] == curPlayerCourse)
                return true;
            x += dx[i];
            y += dy[i];
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSumPlayer(int player) {
        return sumPlayer[player];
    }

    public int getCurPlayerCourse() {
        return curPlayerCourse;
    }

    public int getROWS() {
        return ROWS;
    }

    public int getCOLUMNS() {
        return COLUMNS;
    }

    public void setCurPlayerCourse(int curPlayerCourse) {
        this.curPlayerCourse = curPlayerCourse;
    }

    public static Logic clone(Logic log){
        Logic result = new Logic(log.ROWS, log.COLUMNS);
        for(int i = 0; i < log.ROWS; i++){
            for(int j = 0; j < log.COLUMNS; j++){
                result.getBoard()[i][j] = log.getBoard()[i][j];
            }
        }
        result.sumPlayer[1] = log.getSumPlayer(1);
        result.sumPlayer[2] = log.getSumPlayer(2);
        result.ROWS = log.getROWS();
        result.curPlayerCourse = log.getCurPlayerCourse();
        result.COLUMNS = log.getCOLUMNS();
        return result;
    }
}
