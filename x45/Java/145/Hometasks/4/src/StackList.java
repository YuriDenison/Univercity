import java.util.List;

/**
 * User: Volkman
 * Date: 10.03.2010
 * Time: 0:24:17
 */
public class StackList implements IStack{
    private static final int DEFSIZE = 100;
    private Cell[] cell;

    public StackList(){
        cell = new Cell[DEFSIZE];
        for(int i = 0; i < DEFSIZE; i++)
            cell[i] = new Cell();
    }

    public void push(int val){
        int h = head();
        cell[h].setVal(val);
        cell[h].setNext(cell[head()+1]);
    }

    public int pop(){
        int h = head();
        int k = cell[h-1].getVal();
        cell[h-1].setNext(null);
        return k;
    }

    public int top(){
        return cell[head()-1].getVal();
    }

    public boolean isEmpty(){
        return cell[0].getNext() == null;
    }

    private int head(){
        int i = 0;
        while(cell[i] != null && cell[i].getNext() != null)
            i++;
        return i;
    }

}
