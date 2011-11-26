/**
 * @author Yuri Denison
 * @since 26.10.11
 */
public class Task {
  public static double function(int mode, double x) {
    switch (mode) {
      case 4:
        return Math.cos(x);
      case 6:
        return Math.cos(10 * x);
      default:
        return 0;
    }
  }

  public final static double[] bounds1 = new double[]{0, Math.PI / 2};
  public final static double[] nodes1 = new double[]{
      Math.PI / 6,
      Math.PI / 4,
      Math.PI / 3,
      Math.PI / 2};

  public final static double[] bounds2 = new double[]{-1, 1};
  public final static double[] nodes2 = new double[]{
      -0.87, -0.69, -0.11, 0.31, 0.58, 0.97};

  public final static int parts = 20;
}
