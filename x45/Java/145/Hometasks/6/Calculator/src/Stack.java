/**
 * User: Volkman
 * Date: 20.03.2010
 * Time: 19:27:21
 */
public class Stack{
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
