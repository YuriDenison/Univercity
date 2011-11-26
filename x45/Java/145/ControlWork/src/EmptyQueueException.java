/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.04.2010
 * Time: 14:31:57
 * To change this template use File | Settings | File Templates.
 */
public class EmptyQueueException extends Exception {
    public EmptyQueueException() {
    }

    public EmptyQueueException(String msg) {
        super(msg);
    }
}
