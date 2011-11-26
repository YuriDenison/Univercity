import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.04.2010
 * Time: 14:19:07
 * To change this template use File | Settings | File Templates.
 */
public class QueueTest {
    Queue<Integer> queue = new Queue<Integer>();

    @Test
    public void testEnqueue() throws Exception {
        queue.enqueue(10, 1);
        queue.enqueue(11, 2);
        queue.enqueue(12, 3);
        queue.enqueue(13, 2);
        assertEquals("10 11 12 13 ", queue.print());
    }

    @Test
    public void testDequeue() throws Exception {
        queue.enqueue(10, 1);
        queue.enqueue(11, 2);
        queue.enqueue(12, 3);
        queue.enqueue(13, 2);
        assertEquals(12, (int) queue.dequeue());
        assertEquals("10 11 13 ", queue.print());
    }
}
