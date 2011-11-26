/**
 * User: Volkman
 * Date: 10.03.2010
 * Time: 15:05:08
 */
public class TrueStack implements IStack{
    private Cell head;

    public void push(int val){
        Cell element = new Cell();
        element.setVal(val);
        element.setNext(head);
        head = element;
    }

    public int pop(){
        if (head == null)
            return 0;
        int result = head.getVal();
        head = head.getNext();
        return result;
    }

    public int top(){
        if (head == null)
            return 0;
        return head.getVal();
    }

    public boolean isEmpty(){
        return head == null;
    }
}