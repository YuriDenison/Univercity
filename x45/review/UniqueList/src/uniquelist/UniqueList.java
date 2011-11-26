package uniquelist;

public class UniqueList extends List {

    public void addToHead(int value) throws Iteration {
        if (this.search(value)) {
            throw new Iteration();
        } else {
            ListElement newElement = new ListElement(value, head.next);
            head.next = newElement;
        }
    }

    public void addToEnd(int value) throws Iteration {
        if (!this.search(value)) {
            ListElement newElement = new ListElement(value, null);
            tail.next = newElement;
            tail = newElement;
        } else {
            throw new Iteration();
        }
    }

    public void remove(int value) throws NoRemoveElement {
        if (this.search(value)) {
            ListElement runner = head;
            while (runner.next != null) {
                if (runner.next.value == value) {
                    runner.next = runner.next.next;
                    return;
                }
                runner = runner.next;
            }
        }
        else
            throw new NoRemoveElement();
    }
}
