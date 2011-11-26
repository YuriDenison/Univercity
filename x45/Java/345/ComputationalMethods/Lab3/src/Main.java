import task.Task1;

/**
 * @author Yuri Denison
 * @since 17.10.11
 */
public class Main {
  public static void main(String[] args) {
    SubtractionTable table = new SubtractionTable(Task1.inputX, Task1.inputY);
    table.printTable(5);
  }
}
