/**
 * @author Yuri Denison
 * @since 26.10.11
 */
public class Main {
  public static void main(String[] args) {
    System.out.println("Task 1");
    for (double i = Task.bounds1[0]; i <= Task.bounds1[1];
         i += (Task.bounds1[1] - Task.bounds1[0]) / Task.parts) {
      double f = Task.function(4, i);
      double lagr = Lagrange.countLagrange(i, Task.nodes1);
      double a = Lagrange.countA(i, Task.nodes1);
      System.out.format("i = %f: f = %f; L = %f; delta = %f; A = %f\n", i, f, lagr,
          Math.abs(lagr - f), a);
    }
    System.out.println("\nTask 2");
    for (double i = Task.bounds2[0]; i <= Task.bounds2[1];
         i += (Task.bounds2[1] - Task.bounds2[0]) / Task.parts) {
      double f = Task.function(4, i);
      double lagr = Lagrange.countLagrange(i, Task.nodes2);
      double a = Lagrange.countA(i, Task.nodes2);
      System.out.format("i = %f: f = %f; L = %f; delta = %f; A = %f\n", i, f, lagr,
          Math.abs(lagr - f), a);
    }
  }
}
