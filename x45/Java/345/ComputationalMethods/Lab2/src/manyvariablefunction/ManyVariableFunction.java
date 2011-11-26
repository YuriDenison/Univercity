package manyvariablefunction;

/**
 * @author Yuri Denison
 * @since 27.09.11
 */

public interface ManyVariableFunction {
  public double countFunction(double... values);

  public ManyVariableFunction getPartialDerivative(int pos);
}
