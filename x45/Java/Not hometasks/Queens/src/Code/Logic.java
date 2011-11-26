package Code;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 07.05.2010
 * Time: 17:45:08
 */
public class Logic {
    private int nX;
    private int nY;
    private int[][][] arrangements;
    private int numberOfVariants = -1, curRow = -1;


    private boolean[] column;    // массив столбцов. если значение будет false, значит в этом столбце уже стоит ферзь.
    private boolean[] upDiagFree;    // массив диагоналей слева-направо, сверху-вниз. ---//---
    private boolean[] downDiagFree;  // массив диагоналей справа-налево, сверху-вниз. ---//---
    private int[] queenLocationInRows;  // номера строк, в которых стоят ферзи, для каждого столбца

    public Logic() {
        this.nX = Board.ROWS;
        this.nY = Board.COLUMNS;
        numberOfVariants = 0;
        arrangements = new int[15000][nX][nY];

        column = new boolean[nX];
        upDiagFree = new boolean[nX + nY - 1];
        downDiagFree = new boolean[nX + nY - 1];
        queenLocationInRows = new int[nY];

        for (int i = 0; i < nX; i++)
            column[i] = true;
        for (int i = 0; i < nX + nY - 1; i++) {
            downDiagFree[i] = true;
            upDiagFree[i] = true;
        }

        findArrangement();
    }

    private void findArrangement() {
        curRow++;
        // Проверяем колонки
        for (int i = 0; i < nX; i++) {
            // если клетка не находится под ударом
            if (column[i] && upDiagFree[i + curRow] && downDiagFree[curRow - i + nX -1]) {
                // запоминаем, что в строке находится ферзь
                queenLocationInRows[curRow] = i;

                // маскируем колонку и диагональ
                column[i] = false;
                upDiagFree[i + curRow] = false;
                downDiagFree[curRow - i + nX - 1] = false;

                // если заполнены все строки
                if (curRow >= nX - 1) {
                    numberOfVariants++;
                    /*System.out.println("");
                    System.out.println("");
                    */
                    System.out.println("combination no. " + numberOfVariants);
                    /*for (int r = 0; r < nX; r++) {
                        System.out.println("");
                        for (int c = 0; c < nY; c++) {
                            if (c == queenLocationInRows[r])
                                System.out.print(" Q ");
                            else
                                System.out.print(" . ");
                        }
                    }         */
                    for (int m = 0; m < nX; m++) {
                        for (int n = 0; n < nY; n++) {
                            if (n == queenLocationInRows[m]) {
                                arrangements[numberOfVariants][m][n] = 1;
                            } else {
                                arrangements[numberOfVariants][m][n] = 0;
                            }
                        }
                    }
                } else
                    findArrangement();
                // снимаем пометку с колонки и диагонали
                column[queenLocationInRows[curRow]] = true;
                upDiagFree[curRow + queenLocationInRows[curRow]] = true;
                downDiagFree[curRow - queenLocationInRows[curRow] + nX - 1] = true;
            }
        }
        curRow--; // уменьшаем счетчик строк, пробуем следующую комбинацию
    }


    public int[][][] getArrangements() {
        return arrangements;
    }

    public int getNumberOfVariants() {
        return numberOfVariants;
    }
}