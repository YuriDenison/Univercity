/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.04.2010
 * Time: 13:52:47
 * To change this template use File | Settings | File Templates.
 */
public class Cell<Type> {
    private Type val;
    private int priority;
    private Cell<Type> next;

    public Cell() {
        next = null;
    }

    public void setVal(Type val) {
        this.val = val;

    }

    public void setNext(Cell<Type> next) {
        this.next = next;
    }

    public Type getVal() {
        return val;
    }

    public Cell<Type> getNext() {
        return next;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
