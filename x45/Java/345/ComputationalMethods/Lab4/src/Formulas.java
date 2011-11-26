/**
 * @author Yuri Denison
 */
public class Formulas {
  private double[][] table;
  private int minIndex;
  private double[] xArray;

  public Formulas(double x, double[] xArray, double[] yArray) {
    table = new SubtractionTable(xArray, yArray).getTable();
    this.xArray = xArray;
    minIndex = findMin(x);
  }

  public double startFormula(double t) {
    return table[0][minIndex] +
        t * table[1][minIndex] +
        t * (t - 1) / 2 * table[2][minIndex] +
        t * (t - 1) * (t - 2) / 6 * table[3][minIndex] +
        t * (t - 1) * (t - 2) * (t - 3) / 24 * table[4][minIndex];

  }

  private int findMin(double x) {
    for (int i = 0; i < xArray.length; i++) {
      if (xArray[i] > x) {
        return i;
      }
    }
    return xArray.length - 2; // TODO bad practices
  }

  public double endFormula(double t) {
    return table[0][minIndex + 1] +
        t * table[1][minIndex] +
        t * (t - 1) / 2 * table[2][minIndex - 1] +
        t * (t - 1) * (t - 2) / 6 * table[3][minIndex - 2] +
        t * (t - 1) * (t - 2) * (t - 3) / 24 * table[4][minIndex - 3];
  }

  public double middleFormula(double t) {
    return table[0][minIndex] +
        t * table[1][minIndex] +
        t * (t - 1) / 2 * table[2][minIndex - 1] +
        t * (t - 1) * (t + 1) / 6 * table[3][minIndex - 1] +
        t * (t - 1) * (t + 1) * (t - 2) / 24 * table[4][minIndex - 2];
  }
}
