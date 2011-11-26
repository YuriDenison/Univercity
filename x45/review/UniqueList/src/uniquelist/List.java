package uniquelist;

public class List {

    public class ListElement {

        public int value;
        public ListElement next;

        ListElement(int value, ListElement next) {
            this.value = value;
            this.next = next;
        }
    }
    ListElement head;
    ListElement tail;

    List() {
        head = new ListElement(0, null);
        tail = head;
    }

    public void addToEnd(int number) throws Iteration {
        ListElement newElement = new ListElement(number, null);
        tail.next = newElement;
        tail = newElement;
    }

    public boolean isEmpty() {
        return (head.next == null);
    }

    public void addToHead(int number) throws Iteration {
        ListElement newElement = new ListElement(number, head.next);
        head.next = newElement;
    }

    public void remove(int number) throws NoRemoveElement{
        ListElement runner = head;
        while (runner.next != null) {
            if (runner.next.value == number) {
                runner.next = runner.next.next;
                return;
            }
            runner = runner.next;
        }
    }

    public void printList() {
        ListElement runner = head;
        while (runner.next != null) {
            System.out.print(runner.next.value + " ");
            runner = runner.next;
        }
    }

    public void removeFirst() {
        head.next = head.next.next;
    }

    public void deleteAll() {
        head = null;
    }

    public boolean search(int value) {
        ListElement runner = head;
        while (runner.next != null) {
            if (runner.next.value == value) {
                return true;
            }
            runner = runner.next;
        }
        return false;
    }
}
