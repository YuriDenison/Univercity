import function.Function;
import function.Polynomial;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class PolynomialBoundSearch {
  public static double getUpPositiveBound(Function function) {
    double[] factors = function.getPolynomial().getFactors();
    double maxNegativeAbs = 0;
    int firstNegativePosition = -1;
    for (int i = factors.length - 1; i >= 0; i--) {
      if (factors[i] < 0) {
        if (firstNegativePosition == -1) {
          firstNegativePosition = i;
        }
        if (Math.abs(factors[i]) > maxNegativeAbs) {
          maxNegativeAbs = Math.abs(factors[i]);
        }
      }
    }
    firstNegativePosition = factors.length - 1 - firstNegativePosition;
    return (1 + Math.pow((maxNegativeAbs / factors[factors.length - 1]), 1 / firstNegativePosition));
  }

  public static double getDownPositiveBound(Function function) {
    double[] factors = function.getPolynomial().getFactors();
    reverseArray(factors);

    return 1 / getUpPositiveBound(new Function(new Polynomial(factors), null));
  }

  public static double getDownNegativeBound(Function function) {
    double[] factors = function.getPolynomial().getFactors().clone();
    for (int i = factors.length - 2; i >= 0; i = i - 2) {
      factors[i] *= -1;
    }

    return (-1) * getUpPositiveBound(new Function(new Polynomial(factors), null));
  }

  public static double getUpNegativeBound(Function function) {
    double[] factors = function.getPolynomial().getFactors();
    reverseArray(factors);
    for (int i = factors.length - 2; i >= 0; i = i - 2) {
      factors[i] *= -1;
    }

    return (-1) / getUpPositiveBound(new Function(new Polynomial(factors), null));
  }

  private static void reverseArray(double[] b) {
    int left = 0;          // index of leftmost element
    int right = b.length - 1; // index of rightmost element

    while (left < right) {
      // exchange the left and right elements
      double temp = b[left];
      b[left] = b[right];
      b[right] = temp;

      // move the bounds toward the center
      left++;
      right--;
    }
  }
}
