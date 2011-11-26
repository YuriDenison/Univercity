/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 09.03.2010
 * Time: 3:44:53
 * To change this template use File | Settings | File Templates.
 */

public class StackInt implements IStack{
    private static final int DEFSIZE = 100;
    private int[] array;
    private int head;

    public StackInt() {
        array = new int[DEFSIZE];
        head = 0;
    }

    public void push(int val) {
        array[head++] = val;
    }

    public int pop() {
        return array[--head];
    }

    public int top() {
        return array[head-1];
    }

    public boolean isEmpty(){
        return head == 0;
    }

}
