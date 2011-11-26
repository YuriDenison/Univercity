package function;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class Polynomial {
  protected double[] factors;

  public Polynomial(double[] factors) {
    this.factors = factors;
  }

  public Polynomial(int degree) {
    factors = new double[degree + 1];
  }

  public Polynomial getDerivative() {
    Polynomial pol = new Polynomial(factors.length - 1);
    for (int i = 0; i < factors.length - 1; i++) {
      pol.factors[i] = (i + 1) * factors[i + 1];
    }
    return pol;
  }

  public double countFunction(double x) {
    double res = 0;
    for (int i = 0; i < factors.length; i++) {
      res += factors[i] * Math.pow(x, i);
    }
    return res;
  }

  public double[] getFactors() {
    return factors;
  }
}
