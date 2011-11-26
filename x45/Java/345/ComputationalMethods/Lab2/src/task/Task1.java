package task;

import manyvariablefunction.ManyVariableFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuri Denison
 * @since 27.09.11
 */
public class Task1 {
  public static List<ManyVariableFunction> getFunctions(final double a, final double k) {
    List<ManyVariableFunction> functionList = new ArrayList<ManyVariableFunction>();
    ManyVariableFunction f1 = new ManyVariableFunction() {
      @Override
      public double countFunction(double... values) {
        return (Math.sin(values[0] + k * values[1]) - values[0] + Math.pow(values[1], 2));
      }

      @Override
      public ManyVariableFunction getPartialDerivative(final int pos) {
        return new ManyVariableFunction() {
          @Override
          public double countFunction(double... values) {
            switch (pos) {
              case 0:
                return Math.cos(values[0] + k * values[1]) * Math.sin(1) - 1;
              case 1:
                return Math.cos(values[0] + k * values[1]) * Math.sin(k) + 2 * values[1];
              default:
                throw new ArrayIndexOutOfBoundsException();
            }
          }

          @Override
          public ManyVariableFunction getPartialDerivative(int pos) {
            return null;
          }
        };
      }
    };
    ManyVariableFunction f2 = new ManyVariableFunction() {
      @Override
      public double countFunction(double... values) {
        return Math.pow(values[1] + 0.1, 2) + Math.pow(values[0], 2) - a;
      }

      @Override
      public ManyVariableFunction getPartialDerivative(final int pos) {
        return new ManyVariableFunction() {
          @Override
          public double countFunction(double... values) {
            switch (pos) {
              case 0:
                return 2 * values[0];
              case 1:
                return 2 * (values[1] + 0.1);
              default:
                throw new IndexOutOfBoundsException();
            }
          }

          @Override
          public ManyVariableFunction getPartialDerivative(int pos) {
            return null;
          }
        };
      }
    };

    functionList.add(f1);
    functionList.add(f2);

    return functionList;
  }

  public static double[] getAArray() {
    return new double[]{0.6, 0.7, 0.8, 0.9, 1.0, 1.1};
  }

  public static double[] getKArray() {
    return new double[]{-0.5, -0.4, -0.3, -0.2, -0.1};
  }
}
