/**
 * User: Volkman
 * Date: 27.03.2010
 * Time: 22:50:57
 */
public class Stack<Type> {
    public class EmptyStackException extends Exception {
        public EmptyStackException() {
        }

        public EmptyStackException(String msg) {
            super(msg);
        }
    }

    private class Cell<Type> {
        private Type val;
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
    }

    private Cell<Type> head;

    public void push(Type val) {
        Cell<Type> element = new Cell<Type>();
        element.setVal(val);
        element.setNext(head);
        head = element;
    }

    public Type pop() throws EmptyStackException {
        if (head == null)
            throw new EmptyStackException("Stack is Empty");
        Type result = head.getVal();
        head = head.getNext();
        return result;
    }

    public Type top() throws EmptyStackException {
        if (head == null)
            throw new EmptyStackException("Stack is Empty");
        return head.getVal();
    }

    public boolean isEmpty() {
        return head == null;
    }
}
