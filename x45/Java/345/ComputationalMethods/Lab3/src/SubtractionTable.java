/**
 * @author Yuri Denison
 * @since 17.10.11
 */
public class SubtractionTable {
  private double[] inputY;
  private double[] inpX;
  private double[][] table;

  public SubtractionTable(double[] inpX, double[] input) {
    this.inputY = input;
    this.inpX = inpX;
    createTable(input);
  }

  private void createTable(double[] input) {
    table = new double[input.length][input.length];

    System.arraycopy(input, 0, table[0], 0, input.length);
    for (int i = 1; i < input.length; i++) {
      for (int j = 0; j < input.length - i; j++) {
        table[i][j] = table[i - 1][j + 1] - table[i - 1][j];
      }
    }
  }

  private void printNumber(double num) {
    if (num >= 0) System.out.print(" ");
    if (num != 0) {
      System.out.format("%08.6f", num);
      System.out.print("             ");   // 13 spaces
    }
  }

  public void printTable(int order) {
    for (int i = 0; i < 2 * inputY.length; i++) {
      if (i % 2 == 1) {
        System.out.print("               "); // 15 spaces
      } else {
        System.out.format("%02.1f  ", inpX[i / 2]);
      }
      for (int j = 0, k = i / 2; j <= order && k >= 0; j = j + 2, k--) {
//                System.out.format("(%d,%d)", j + i % 2, k);
        printNumber(table[j + i % 2][k]);
      }
      System.out.println();
    }
  }

  public double[][] getTable() {
    return table;
  }
}
