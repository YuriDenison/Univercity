import manyvariablefunction.ManyVariableFunction;
import task.Task1;

import java.util.List;

/**
 * @author Yuri Denison
 * @since 27.09.11
 */
public class Main {
  public static void main(String[] args) {
    double[] aArray = Task1.getAArray();
    double[] kArray = Task1.getKArray();

    for (double a : aArray) {
      for (double k : kArray) {
        List<ManyVariableFunction> functions = Task1.getFunctions(a, k);
        System.out.println("New Function!! a = " + a + ", k = " + k);
        List<Pair> res = NewtonMethod.getSolutions(functions.get(0), functions.get(1), 0, 2);
        System.out.println("Root: x = " + res.get(0).getX() + ", y = " + res.get(0).getY());
        for (int i = 1; i < res.size(); i++) {
          Pair cur = res.get(i);
          if (Math.abs(cur.getX() - res.get(i - 1).getX()) > 0.0001 &&
              Math.abs(cur.getY() - res.get(i - 1).getY()) > 0.0001) {
            System.out.println("Root: x = " + cur.getX() + ", y = " + cur.getY());
          }
        }
        System.out.println("");
      }
    }
  }
}
