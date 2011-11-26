import manyvariablefunction.ManyVariableFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuri Denison
 * @since 27.09.11
 */
public class NewtonMethod {
  private static final int SEGMENTS_NUMBER = 200;
  private static final double MIN_DIFF = Math.pow(10, -5);
  private static final double MIN_VALUE = 0.01;

  public static List<Pair> getSolutions(
      final ManyVariableFunction f,
      final ManyVariableFunction g,
      double left, double right) {
    List<Pair> initialApproximations = countInitialApproximations(f, g, left, right);
    List<Pair> results = new ArrayList<Pair>();

    ManyVariableFunction deltaX = new ManyVariableFunction() {
      @Override
      public double countFunction(double... values) {
        return (g.countFunction(values) * f.getPartialDerivative(1).countFunction(values) -
            f.countFunction(values) * g.getPartialDerivative(1).countFunction(values)) /
            (f.getPartialDerivative(0).countFunction(values) * g.getPartialDerivative(1).countFunction(values) -
                f.getPartialDerivative(1).countFunction(values) * g.getPartialDerivative(0).countFunction(values));
      }

      @Override
      public ManyVariableFunction getPartialDerivative(int pos) {
        return null;
      }
    };
    ManyVariableFunction deltaY = new ManyVariableFunction() {
      @Override
      public double countFunction(double... values) {
        return (f.countFunction(values) * g.getPartialDerivative(0).countFunction(values) -
            g.countFunction(values) * f.getPartialDerivative(0).countFunction(values)) /
            (f.getPartialDerivative(0).countFunction(values) * g.getPartialDerivative(1).countFunction(values) -
                f.getPartialDerivative(1).countFunction(values) * g.getPartialDerivative(0).countFunction(values));
      }

      @Override
      public ManyVariableFunction getPartialDerivative(int pos) {
        return null;
      }
    };


    for (Pair coordinates : initialApproximations) {
      int step = 0;
      Pair cur = coordinates;
      System.out.println("  Step (" + step + "): xN = " + cur.getX() + ", yN = " + cur.getY() +
          ", f = " + f.countFunction(cur.getX(), cur.getY()) +
          ", g = " + g.countFunction(cur.getX(), cur.getY()));
      Pair next = new Pair(
          cur.getX() + deltaX.countFunction(cur.getX(), cur.getY()),
          cur.getY() + deltaY.countFunction(cur.getX(), cur.getY())
      );
      while (!(Math.abs(cur.getX() - next.getX()) < MIN_DIFF &&
          Math.abs(cur.getY() - next.getY()) < MIN_DIFF)) {
        cur = new Pair(
            cur.getX() + deltaX.countFunction(cur.getX(), cur.getY()),
            cur.getY() + deltaY.countFunction(cur.getX(), cur.getY())
        );
        step++;
        System.out.println("  Step (" + step + "): xN = " + cur.getX() + ", yN = " + cur.getY() +
            ", f = " + f.countFunction(cur.getX(), cur.getY()) +
            ", g = " + g.countFunction(cur.getX(), cur.getY()));
        cur.setX(next.getX());
        cur.setY(next.getY());
        next = new Pair(
            cur.getX() + deltaX.countFunction(cur.getX(), cur.getY()),
            cur.getY() + deltaY.countFunction(cur.getX(), cur.getY())
        );
      }
      results.add(next);
      System.out.println();
    }
    return results;
  }

  public static List<Pair> countInitialApproximations(
      ManyVariableFunction function1,
      ManyVariableFunction function2,
      double left, double right) {
    List<Pair> initialApproximations = new ArrayList<Pair>();

    double step = (right - left) / SEGMENTS_NUMBER;
    double curX = left;
    for (int j = 0; j < SEGMENTS_NUMBER; j++) {
      double curY = left;
      for (int i = 0; i < SEGMENTS_NUMBER; i++) {
        double r1 = function1.countFunction(curX, curY);
        double r2 = function2.countFunction(curX, curY);
        if (Math.abs(r1) < MIN_VALUE && Math.abs(r2) < MIN_VALUE) {
          initialApproximations.add(new Pair(curX, curY));
        }
        curY += step;
      }
      curX += step;
    }
    return initialApproximations;
  }
}
