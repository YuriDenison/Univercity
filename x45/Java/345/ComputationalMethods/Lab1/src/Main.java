import function.Function;
import tasks.Task1;
import tasks.Task2;
import tasks.Task3;

/**
 * @author Yuri Denison
 * @since 14.09.11
 */
public class Main {


  public static void main(String[] args) {
    Function function1 = Task1.getFunction();
    Function function2 = Task2.getFunction();
    Function function3 = Task3.getFunction();

    NewtonMethod.getSolutions(function1, -1, 1);
    NewtonMethod.getSolutions(function2, -5, 5);
    NewtonMethod.getSolutions(function3, -5, 5);
  }
}
