/**
 * @author Yuri Denison
 * @date 16.11.11
 */
public class IntegralFormulas {
  public static double mediumRectangleFormula(int n) {
    double res = 0;
    double h = (Task.X1 - Task.X0) / n;
    for (int i = 0; i <= n - 1; i++) {
      res += Task.function(Task.X0 + (2 * i + 1) * h / 2);
    }
    return h * res;
  }

  public static double trapezoidFormula(int n) {
    double res = 0;
    double h = (Task.X1 - Task.X0) / n;
    for (int i = 1; i <= n - 1; i++) {
      res += Task.function(Task.X0 + i * h);
    }
    return h * ((Task.function(Task.X0) + Task.function(Task.X1)) / 2 + res);
  }

  public static double simpsonFormula(int n) {
    double res = 0;
    double h = (Task.X1 - Task.X0) / n;
    for (int i = 1; i <= n - 1; i++) {
      res += 2 * Task.function(Task.X0 + i * h);
    }
    for (int i = 1; i <= n; i++) {
      res += 4 * Task.function(Task.X0 + (2 * i - 1) * h / 2);
    }
    return h / 6 * (Task.function(Task.X0) + Task.function(Task.X1) + res);
  }

  public static double getExactIntegralValue(int mode) {
    switch (mode) {
      case 1:
        return (Math.pow(2, Task.K1_FORMULA) * mediumRectangleFormula(2 * Task.N) - mediumRectangleFormula(Task.N)) / (Math.pow(2, Task.K1_FORMULA) - 1);
      case 2:
        return (Math.pow(2, Task.K2_FORMULA) * trapezoidFormula(2 * Task.N) - trapezoidFormula(Task.N)) / (Math.pow(2, Task.K2_FORMULA) - 1);
      case 3:
        return (Math.pow(2, Task.K3_FORMULA) * simpsonFormula(2 * Task.N) - simpsonFormula(Task.N)) / (Math.pow(2, Task.K3_FORMULA) - 1);
    }
    return 0;
  }

  public static double getEstimateError() {
    return Math.abs(Math.pow(Task.X1 - Task.X0, 3)) / (12 * Task.N * Task.N) * Task.MAX_FUNCTION;
  }

  public static double getDifferenceBetweenExactValueAndSimpleValue(int mode) {
    switch (mode) {
      case 1:
        return Math.abs(getExactIntegralValue(mode) - mediumRectangleFormula(2 * Task.N));
      case 2:
        return Math.abs(getExactIntegralValue(mode) - trapezoidFormula(2 * Task.N));
      case 3:
        return Math.abs(getExactIntegralValue(mode) - simpsonFormula(2 * Task.N));
    }
    return 0;
  }
}
