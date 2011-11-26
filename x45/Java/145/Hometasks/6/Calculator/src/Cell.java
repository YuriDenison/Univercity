/**
 * User: Volkman
 * Date: 20.03.2010
 * Time: 19:28:50
 */
public class Cell {
    private int val;
    private Cell next;

    public Cell(){
        next = null;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public Cell getNext() {
        return next;
    }
}

