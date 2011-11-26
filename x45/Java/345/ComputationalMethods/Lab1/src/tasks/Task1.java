package tasks;

import function.Function;
import function.NonPolynomial;
import function.Polynomial;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class Task1 {
  private static final NonPolynomial cos = new NonPolynomial() {
    public double countFunction(double x) {
      return (-1) * Math.cos(x);
    }

    public NonPolynomial getDerivative() {
      return cos1;
    }
  };

  private static final NonPolynomial cos1 = new NonPolynomial() {
    public double countFunction(double x) {
      return Math.sin(x);
    }

    public NonPolynomial getDerivative() {
      return cos2;
    }
  };
  private static final NonPolynomial cos2 = new NonPolynomial() {
    public double countFunction(double x) {
      return Math.cos(x);
    }

    public NonPolynomial getDerivative() {
      return cos3;
    }
  };
  private static final NonPolynomial cos3 = new NonPolynomial() {
    public double countFunction(double x) {
      return (-1) * Math.sin(x);
    }

    public NonPolynomial getDerivative() {
      return cos;
    }
  };

  public static Function getFunction() {
    return new Function(
        new Polynomial(new double[]{-1, 3}),
        cos
    );

  }
}
