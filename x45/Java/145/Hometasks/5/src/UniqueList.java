/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 19.03.2010
 * Time: 14:05:48
 * To change this template use File | Settings | File Templates.
 */
public class UniqueList {

    public class TwiceAddingException extends Exception {
        public TwiceAddingException() {
        }

        public TwiceAddingException(String msg) {
            super(msg);
        }
    }

    public class NotInitedElementException extends Exception {
        public NotInitedElementException() {
        }

        public NotInitedElementException(String msg) {
            super(msg);
        }
    }

    public class SizeListException extends Exception {
        public SizeListException() {
        }

        public SizeListException(String msg) {
            super(msg);
        }
    }

    private static final int MAX_SIZE = 500;
    private int[] value;

    public UniqueList() {
        value = new int[MAX_SIZE + 1];
    }

    public void add(int n, int a) throws TwiceAddingException, SizeListException {
        if (n < MAX_SIZE) {
            if (!contains(a)) {
                for (int i = MAX_SIZE - 1; i > n; i--) {
                    value[i + 1] = value[i];
                }
                value[n] = a;
            } else {
                throw new TwiceAddingException("This number has added to list yet.");
            }
        } else {
            throw new SizeListException("To high number of element");
        }
    }

    public void remove(int a) throws NotInitedElementException {
        if (contains(a)) {
            int n = find(a);
            if (n <= MAX_SIZE) {
                for (int i = n; i < MAX_SIZE - 1; i++) {
                    value[i] = value[i + 1];
                }
            }
        } else {
            throw new NotInitedElementException("The list doesn't contain this number");
        }
    }

    public boolean contains(int a) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if (value[i] == a)
                return true;
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < MAX_SIZE; i++) {
            value[i] = 0;
        }
    }

    public int getValue(int i) throws SizeListException {
        if (i < MAX_SIZE) {
            return value[i];
        } else {
            throw new SizeListException("To high number of element");
        }
    }

    public int find(int s) throws NotInitedElementException {
        if (contains(s)) {
            for (int i = 0; i < MAX_SIZE; i++) {
                if (value[i] == s)
                    return i;
            }
        } else {
            throw new NotInitedElementException("The list doesn't contain this number");
        }
        return 0;
    }

}

