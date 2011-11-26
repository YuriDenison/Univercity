package tasks;

import function.Function;
import function.Polynomial;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class Task2 {
  public static Function getFunction() {
    return new Function(
        new Polynomial(new double[]{1.25, -1.75, -2, 1}),
        null
    );
  }
}
