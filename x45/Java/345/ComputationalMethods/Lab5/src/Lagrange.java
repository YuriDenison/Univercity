/**
 * @author Yuri Denison
 * @since 26.10.11
 */
public class Lagrange {
  private static double omega(int mode, double x, double... nodes) {
    switch (mode) {
      case 4:
        return (x - nodes[0]) * (x - nodes[1]) * (x - nodes[2]) * (x - nodes[3]);
      case 6:
        return (x - nodes[0]) * (x - nodes[1]) * (x - nodes[2]) * (x - nodes[3]) * (x - nodes[4]) * (x - nodes[5]);
      default:
        return 0;
    }
  }

  private static double derivativeOmega(int mode, double x, double... nodes) {
    switch (mode) {
      case 4:
        return nodes[1] * (-(nodes[2] * nodes[3]) + 2 * nodes[2] * x + 2 * nodes[3] * x - 3 * Math.pow(x, 2)) -
            nodes[0] * (nodes[2] * nodes[3] + nodes[1] * (nodes[2] + nodes[3] - 2 * x) - 2 * nodes[2] * x - 2 * nodes[3] * x + 3 * Math.pow(x, 2)) +
            x * (2 * nodes[2] * nodes[3] - 3 * nodes[2] * x - 3 * nodes[3] * x + 4 * Math.pow(x, 2));
      case 6:
        return (-nodes[0] + x) * (-nodes[1] + x) * (-nodes[2] + x) * (-nodes[3] + x) * (-nodes[4] + x) +
            (-nodes[0] + x) * (-nodes[1] + x) * (-nodes[2] + x) * (-nodes[3] + x) * (-nodes[5] + x) +
            (-nodes[0] + x) * (-nodes[1] + x) * (-nodes[2] + x) * (-nodes[4] + x) * (-nodes[5] + x) +
            (-nodes[0] + x) * (-nodes[1] + x) * (-nodes[3] + x) * (-nodes[4] + x) * (-nodes[5] + x) +
            (-nodes[0] + x) * (-nodes[2] + x) * (-nodes[3] + x) * (-nodes[4] + x) * (-nodes[5] + x) +
            (-nodes[1] + x) * (-nodes[2] + x) * (-nodes[3] + x) * (-nodes[4] + x) * (-nodes[5] + x);
      default:
        return 0;
    }
  }


  public static double countLagrange(double x, double[] nodes) {
    double res = 0;
    for (double node : nodes) {
      res += ((omega(nodes.length, x, nodes) != 0) ? omega(nodes.length, x, nodes) /
          ((x - node) * derivativeOmega(nodes.length, node, nodes)) *
          Task.function(nodes.length, node) : 0.0);
    }
    return res;
  }

  public static double countA(double i, double[] nodes) {
    double cur = Math.abs(omega(nodes.length, i, nodes)) / factorial(nodes.length + 1);
    switch (nodes.length) {
      case 4:
        return cur;
      case 6:
        return Math.pow(10, 7) * cur;
      default:
        return 0;
    }
  }

  private static long factorial(int i) {
    return (i > 1) ? 1 : i * factorial(i - 1);
  }
}
