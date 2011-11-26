/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.04.2010
 * Time: 13:52:09
 * To change this template use File | Settings | File Templates.
 */
public class Queue<Type> {
    private Cell<Type> head;
    private Cell<Type> root = null;

    public Queue() {
        head = new Cell<Type>();
    }

    public void enqueue(Type val, int priority) {
        head.setVal(val);
        head.setPriority(priority);
        if (root == null) {
            root = head;
        }
        Cell<Type> element = new Cell<Type>();
        head.setNext(element);
        head = element;
    }

    public Type dequeue() throws EmptyQueueException {
        if (root == null)
            throw new EmptyQueueException("Empty Quenue");
        int mp = maxPriorityContains();
        Cell<Type> cur = root;
        while (cur.getNext().getPriority() != mp) {
            cur = cur.getNext();
        }
        Type result = cur.getNext().getVal();
        cur.setNext(cur.getNext().getNext());
        return result;
    }

    private int maxPriorityContains() {
        Cell<Type> cur = root;
        int maxPriority = root.getPriority();
        while (cur.getNext() != null) {
            if (cur.getPriority() > maxPriority) {
                maxPriority = cur.getPriority();
            }
            cur = cur.getNext();
        }
        return maxPriority;
    }

    public String print() {
        String str = "";
        Cell<Type> cur = root;
        while (cur != head) {
            str += cur.getVal() + " ";
			cur = cur.getNext();
        }
        return str;
    }
}
