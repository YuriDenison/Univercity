package tasks;

import function.Function;
import function.Polynomial;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class Task3 {
  public static Function getFunction() {
    return new Function(
        new Polynomial(new double[]{1, 0, -32, 0, 160, 0, -256, 0, 128}),
        null
    );
  }
}
