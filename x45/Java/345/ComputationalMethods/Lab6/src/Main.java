/**
 * @author Yuri Denison
 * @date 16.11.11
 */
public class Main {
  public static void main(String[] args) {
    System.out.printf("mediumRectangleFormula (n = %d): %f\n", Task.N, IntegralFormulas.mediumRectangleFormula(Task.N));
    System.out.printf("mediumRectangleFormula (n = %d): %f\n\n", Task.N * 2, IntegralFormulas.mediumRectangleFormula(Task.N * 2));

    System.out.printf("trapezoidFormula (n = %d): %f\n", Task.N, IntegralFormulas.trapezoidFormula(Task.N));
    System.out.printf("trapezoidFormula (n = %d): %f\n\n", Task.N * 2, IntegralFormulas.trapezoidFormula(Task.N * 2));

    System.out.printf("simpsonFormula (n = %d): %f\n", Task.N, IntegralFormulas.simpsonFormula(Task.N));
    System.out.printf("simpsonFormula (n = %d): %f\n\n", Task.N * 2, IntegralFormulas.simpsonFormula(Task.N * 2));

    System.out.printf("ExactIntegral rectangle: %f\n", IntegralFormulas.getExactIntegralValue(1));
    System.out.printf("ExactIntegral trapezoid: %f\n", IntegralFormulas.getExactIntegralValue(2));
    System.out.printf("ExactIntegral simpson: %f\n\n", IntegralFormulas.getExactIntegralValue(3));

    System.out.printf("EstimateError: %f, |It - In| = %f\n\n", IntegralFormulas.getEstimateError(),
        IntegralFormulas.getDifferenceBetweenExactValueAndSimpleValue(1));

  }
}
