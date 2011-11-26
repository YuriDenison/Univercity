/**
 * User: Volkman
 * Date: 27.03.2010
 * Time: 22:49:48
 */
public class Cell<Type>{
    private Type val;
    private Cell<Type> next;

    public Cell(){
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
}
