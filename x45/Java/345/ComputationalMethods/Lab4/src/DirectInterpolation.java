/**
 * @author Yuri Denison
 * @date 19.10.11
 */
public class DirectInterpolation {
  private static int findMinIndex(double x, double[] arr) {
    for (int i = arr.length - 1; i >= 0; i--) {
      if (x > arr[i]) {
        return i;
      }
    }
    return 0;
  }

  public static double startFormula(double x, double[] xArray, double[] yArray) {
    double[][] table = new SubtractionTable(xArray, yArray).getTable();
    int minIndex = findMinIndex(x, xArray);
    double step = Math.abs(xArray[1] - xArray[0]);
    double t = (x - xArray[minIndex]) / step;
    return table[0][minIndex] +
        t * table[1][minIndex] +
        t * (t - 1) / 2 * table[2][minIndex] +
        t * (t - 1) * (t - 2) / 6 * table[3][minIndex] +
        t * (t - 1) * (t - 2) * (t - 3) / 24 * table[4][minIndex];
  }

  public static double middleFormula(double x, double[] xArray, double[] yArray) {
    double[][] table = new SubtractionTable(xArray, yArray).getTable();
    int minIndex = findMinIndex(x, xArray);
    double step = Math.abs(xArray[1] - xArray[0]);
    double t = (x - xArray[minIndex]) / step;
    return table[0][minIndex] +
        t * table[1][minIndex] +
        t * (t - 1) / 2 * table[2][minIndex - 1] +
        t * (t - 1) * (t + 1) / 6 * table[3][minIndex - 1] +
        t * (t - 1) * (t + 1) * (t - 2) / 24 * table[4][minIndex - 2];
  }

  public static double endFormula(double x, double[] xArray, double[] yArray) {
    double[][] table = new SubtractionTable(xArray, yArray).getTable();
    int maxIndex = findMinIndex(x, xArray) + 1;
    double step = Math.abs(xArray[1] - xArray[0]);
    double t = (x - xArray[maxIndex]) / step;
    return table[0][maxIndex] +
        t * table[1][maxIndex - 1] +
        t * (t + 1) / 2 * table[2][maxIndex - 2] +
        t * (t + 1) * (t + 2) / 6 * table[3][maxIndex - 3] +
        t * (t + 1) * (t + 2) * (t + 3) / 24 * table[4][maxIndex - 4];
  }
}
