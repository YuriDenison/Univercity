package priorityqueue;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Author
 */
// This is an example of priority queue class.
// High priority items have low 'prio' value, i.e. ("str1", 0) > ("str2", 1)
public class MyPriorityQueue<T> {
    private class ListElement {
        // constructor

        public ListElement() {
        }
        // yet another constructor

        public ListElement(T v, int p) {
            next = null;
            value = v;
            prio = p;
        }
        // self-explanatory

        public ListElement getNext() {
            return next;
        }
        // self-explanatory

        public void setNext(ListElement nxt) {
            next = nxt;
        }
        // self-explanatory

        public T getValue() {
            return value;
        }
        // self-explanatory

        public int getPrio() {
            return prio;
        }

        private T value;           // stored value
        private ListElement next;  // next item
        private int prio;          // item priority
    }

    // constructor

    public MyPriorityQueue() {
        head = new ListElement();  // create sentinel
    }

    // add item to queue

    public void enqueue(T val, int prio) {
        ListElement l = head;
        while (l.getNext() != null && l.getNext().getPrio() >= prio) {
            l = l.getNext();
        }
        ListElement tmp = new ListElement(val, prio);
        tmp.setNext(l.getNext());
        l.setNext(tmp);
    }

    // get item from queue with least priority

    public T dequeue() throws EmptyQueueException {
        if (head.getNext() == null)
            throw new EmptyQueueException("Queue is empty!!!");
        ListElement tmp = head.getNext();
        head.setNext(tmp.getNext());
        return tmp.getValue();
    }

    private ListElement head; // sentinel
}
