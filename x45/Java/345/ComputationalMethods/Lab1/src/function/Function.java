package function;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class Function {
  protected Polynomial polynomial;
  protected NonPolynomial nonPolynomial;
  protected boolean isPolynomial;

  public Function(Polynomial polynomial, NonPolynomial nonPolynomial) {
    this.polynomial = polynomial;
    this.nonPolynomial = nonPolynomial;
    isPolynomial = nonPolynomial == null;
  }

  public double countFunction(double x) {
    if (polynomial != null) {
      if (!isPolynomial) {
        return polynomial.countFunction(x) + nonPolynomial.countFunction(x);
      } else {
        return polynomial.countFunction(x);
      }
    } else {
      if (!isPolynomial) {
        return nonPolynomial.countFunction(x);
      } else {
        return 0;
      }
    }
  }

  public Function getDerivative() {
    if (polynomial != null) {
      if (!isPolynomial) {
        return new Function(polynomial.getDerivative(), nonPolynomial.getDerivative());
      } else {
        return new Function(polynomial.getDerivative(), null);
      }
    } else {
      if (!isPolynomial) {
        return new Function(null, nonPolynomial.getDerivative());
      } else {
        return null;
      }
    }
  }

  public Polynomial getPolynomial() {
    return polynomial;
  }

  public NonPolynomial getNonPolynomial() {
    return nonPolynomial;
  }

  public boolean isPolynomial() {
    return isPolynomial;
  }
}
