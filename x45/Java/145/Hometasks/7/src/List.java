import java.util.Iterator;

/**
 * User: Volkman
 * Date: 27.03.2010
 * Time: 23:06:03
 */
public class List<Type> implements Iterable<Type> {
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

    private Cell<Type> cur;
    private Cell<Type> root;

    public List() {
        cur = new Cell<Type>();
        root = cur;
    }

    public void add(Type val) {
        cur.setVal(val);
        Cell<Type> cell = new Cell<Type>();
        cur.setNext(cell);
        cur = cell;
    }

    public void remove(Type val) {
        Cell<Type> cell = root;
        while (cell.getNext() != null) {
            if (cell.getNext().getVal() == val) {
                cell.setNext(cell.getNext().getNext());
                return;
            }
            cell = cell.getNext();
        }
    }

    public void clear() {
        root.setNext(null);
        cur = root;
    }

    public boolean contains(Type val) {
        Cell<Type> cell = root;
        while (cell.getNext() != null) {
            if (cell.getVal() == val)
                return true;
            cell = cell.getNext();
        }
        return false;
    }

    public Iterator<Type> iterator() {
        return new Iterator<Type>() {
            Cell<Type> cur1 = root;

            public boolean hasNext() {
                if (cur1 != null && cur1.getNext() != null)
                    return true;
                return false;
            }

            public void remove() {
                Cell<Type> cell = cur1.getNext();
                List.this.remove(cur1.getVal());
                cur1 = cell;
            }

            public Type next() {
                Type t = cur1.getVal();
                cur1 = cur1.getNext();
                return t;
            }
        };
    }
}
