import task.Task1;

/**
 * @author Yuri Denison
 * @since 19.10.11
 */
public class Main {
  public static void main(String[] args) {
    new SubtractionTable(Task1.inputX, Task1.inputY).printTable(5);

    System.out.format("start: f(%f) = %f\n\n", Task.X1,
        DirectInterpolation.startFormula(Task.X1, Task1.inputX, Task1.inputY));
    System.out.format("end: f(%f) = %f\n\n",
        Task.X2, DirectInterpolation.endFormula(Task.X2, Task1.inputX, Task1.inputY));

    System.out.format("start: f(%f) = %f\n", Task.X3,
        DirectInterpolation.startFormula(Task.X3, Task1.inputX, Task1.inputY));
    System.out.format("middle: f(%f) = %f\n", Task.X3,
        DirectInterpolation.middleFormula(Task.X3, Task1.inputX, Task1.inputY));
    System.out.format("end: f(%f) = %f\n\n", Task.X3,
        DirectInterpolation.endFormula(Task.X3, Task1.inputX, Task1.inputY));

    System.out.format("Reserve: f-1(%f) = %f\n", Task.Y,
        ReverseInterpolation.countX(Task.Y, Task1.inputX, Task1.inputY));
  }
}
