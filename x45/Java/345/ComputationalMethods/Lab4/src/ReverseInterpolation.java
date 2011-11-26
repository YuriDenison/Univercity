/**
 * @author Yuri Denison
 * @date 25.10.11
 */

public class ReverseInterpolation {
  private static final double eps = Math.pow(10, -4);

  private static int findMinIndex(double x, double[] arr) {
    for (int i = arr.length - 1; i >= 0; i--) {
      if (x > arr[i]) {
        return i;
      }
    }
    return 0;
  }

  public static double countX(double y, double[] xArray, double[] yArray) {
    double[][] table = new SubtractionTable(xArray, yArray).getTable();
    int index = findMinIndex(y, yArray);

    int iterations = 0;
    double t = 0;
    double nextT = countNewT(t, y, index, table);
    while (Math.abs(t - nextT) > eps) {
      iterations++;
      t = nextT;
      nextT = countNewT(t, y, index, table);
    }
    System.out.format("Iterations: %d\n", iterations);
    double step = Math.abs(xArray[1] - xArray[0]);
    return xArray[index] + t * step;
  }

  private static double countNewT(double t, double y, int index, double[][] table) {
    return 1 / (table[1][index - 1]) *
        (y - table[0][index] -
            t * (t + 1) / 2 * table[2][index - 2] -
            t * (t + 1) * (t + 2) / 6 * table[3][index - 3] -
            t * (t + 1) * (t + 2) * (t + 3) / 24 * table[4][index - 4]);
  }
}
