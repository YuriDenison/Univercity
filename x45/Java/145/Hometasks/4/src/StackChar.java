/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 09.03.2010
 * Time: 3:44:53
 * To change this template use File | Settings | File Templates.
 */

public class StackChar{
    private static final int DEFSIZE = 100;
    private char[] array;
    private int   head;

    public StackChar() {
        array = new char[DEFSIZE];
        head = 0;
    }
    public void push(char val) {
        array[head++] = val;
    }
    public int pop() {
        return array[--head];
    }
    public char top() {
        return array[head-1];
    }

    public boolean isEmpty(){
        return head == 0;
    }
}
