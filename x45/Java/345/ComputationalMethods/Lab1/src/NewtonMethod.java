import function.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class NewtonMethod {
  private static final int SEGMENTS_NUMBER = 100;
  private static final double MIN_DIFF = 0.00001;

  public static List<Double> getSolutions(Function function, double left, double right) {
    List<Double> initialApproximations = countInitialApproximations(function, left, right);
    List<Double> results = new ArrayList<Double>();

    System.out.println("New Function!");
    for (double x0 : initialApproximations) {
      int step = 0;
      double cur = x0;
      double next = cur - function.countFunction(cur) / function.getDerivative().countFunction(cur);
      System.out.println("  Step (" + step + "):, xN = " + x0 + ", f(xN) = " + function.countFunction(x0));
      while (cur - next > MIN_DIFF) {
        cur = next;
        next = cur - function.countFunction(cur) / function.getDerivative().countFunction(cur);
        step++;
        System.out.println("  Step (" + step + "):, xN = " + cur + ", f(xN) = " + function.countFunction(cur));
      }
      results.add(next);
      System.out.println("  Step (" + (step + 1) + "):, xN = " + next + ", f(xN) = " + function.countFunction(next));
      System.out.println("root: " + next);
      System.out.println();
    }
    return results;
  }

  public static List<Double> countInitialApproximations(Function function, double left, double right) {
    double leftBound = (function.isPolynomial()) ? PolynomialBoundSearch.getDownNegativeBound(function) : left;
    double rightBound = (function.isPolynomial()) ? PolynomialBoundSearch.getUpPositiveBound(function) : right;
    List<Double> initialApproximations = new ArrayList<Double>();

    double step = (rightBound - leftBound) / SEGMENTS_NUMBER;
    double cur = leftBound;
    double next = leftBound + step;
    for (int i = 0; i < SEGMENTS_NUMBER; i++) {
      if (function.countFunction(cur) * function.countFunction(next) <= 0) {
        initialApproximations.add(next);
      }
      cur = next;
      next += step;
    }
    return initialApproximations;
  }
}
