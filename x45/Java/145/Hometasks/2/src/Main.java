import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 19.02.2010
 * Time: 17:47:03
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void BubbleSortColumn(int[] mas, SquareMatrix sm) {
        boolean t = true;
        while (t) {
            t = false;
            for (int j = 0; j < mas.length - 1; j++) {
                 if (sm.getValue(0, j) > sm.getValue(0, j + 1)) {
                    sm.swapColumn(j, j + 1);
                    t = true;
                }
            }
        }
    }

    public static void roundSpiral(SquareMatrix mat) {
        int[][] cur = new int[mat.getLength()][mat.getHeight()];
        for (int i = 0; i < mat.getLength(); i++) {
            for (int j = 0; j < mat.getHeight(); j++) {
                cur[i][j] = 0;
            }
        }
        int x = 0, y = 1;
        int start_x = (mat.getLength() - 1) / 2,
                start_y = (mat.getHeight() - 1) / 2;
        cur[start_x][start_y] = 1;
        System.out.print(mat.getValue(start_x, start_y) + " ");
        while ( start_x + x != 0 || start_y + y != (mat.getLength() - 1) ){
            if (x >= 0 && y > 0) {
                System.out.print(mat.getValue(start_x + x, start_y + y) + " ");
                cur[start_x + x][start_y + y] = 1;
                if (cur[start_x + x][start_y + y - 1] == 0)
                    y--;
                else
                    x++;
            }
            if (x > 0 && y <= 0) {
                System.out.print(mat.getValue(start_x + x, start_y + y) + " ");
                cur[start_x + x][start_y + y] = 1;
                if (cur[start_x + x - 1][start_y + y] == 0)
                    x--;
                else
                    y--;
            }
            if (x <= 0 && y < 0) {
                System.out.print(mat.getValue(start_x + x, start_y + y) + " ");
                cur[start_x + x][start_y + y] = 1;
                if (cur[start_x + x][start_y + y + 1] == 0)
                    y++;
                else
                    x--;
            }
            if (x < 0 && y >= 0) {
                System.out.print(mat.getValue(start_x + x, start_y + y) + " ");
                cur[start_x + x][start_y + y] = 1;
                if (cur[start_x + x + 1][start_y + y] == 0)
                    x++;
                else
                    y++;
            }
        }
        System.out.println(mat.getValue(start_x + x, start_y + y));
    }

    public static void sortColumn(SquareMatrix sm) {
        int[] num = new int[sm.getLength()];
        for (int i = 0; i < num.length; i++) {
            num[i] = sm.getValue(0, i);
        }
        BubbleSortColumn(num, sm);
    }

    public static void main(String[] args) throws IOException {
        SquareMatrix mat, sortmatr;
        System.out.println("Please, input matrix or input 'File', if you want to read matrix from input.txt: ");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        System.out.print(" ");
        String[] str = br.readLine().split(" ");
        if (str[0].equals("File")) {
            String file = "src/input.txt";
            isr = new InputStreamReader(new FileInputStream(file));
            br = new BufferedReader(isr);
            str = br.readLine().split(" ");
        }

        mat = new SquareMatrix(str.length);
        sortmatr = new SquareMatrix(str.length);
        boolean check = false;
        for (int i = 0; i < mat.getLength(); i++) {
            if (check) {
                System.out.print(" ");
                str = br.readLine().split(" ");
            }
            for (int j = 0; j < mat.getHeight(); j++) {
                int cur = Integer.parseInt(str[j]);
                mat.set(i, j, cur);
                sortmatr.set(i, j, mat.getValue(i, j));
            }
            check = true;
        }
        System.out.println("");
        mat.printMatrix();

        System.out.println("1. Column Sort: ");
        sortColumn(sortmatr);
        sortmatr.printMatrix();

        System.out.println("2. Spiral Round: ");
        if (mat.getLength() % 2 == 0)
            System.out.println("Sorry, but the side of matrix must be odd!!! Try to restart and insert another matrix.");
        else {
            roundSpiral(mat);
        }
    }
}
