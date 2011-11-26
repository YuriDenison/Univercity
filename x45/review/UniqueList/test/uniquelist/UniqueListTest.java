/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniquelist;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UniqueListTest {

    public UniqueListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addToHead method, of class UniqueList.
     */
    @Test
    public void testAddToHead() throws Exception {
        try {
            System.out.println("addToHead");
            UniqueList instance = new UniqueList();
            instance.addToHead(7);
            instance.addToHead(7);
            instance.addToHead(5);
            boolean expResult = true;
            boolean result = instance.search(5);
            assertEquals(result, expResult);
        } catch (Exception e) {
            System.out.print("число уже есть!");
        }
    }

    /**
     * Test of addToEnd method, of class UniqueList.
     */
    @Test
    public void testAddToEnd() throws Exception {
        try {
            System.out.println("addToEnd");
            UniqueList instance = new UniqueList();
            instance.addToEnd(10);
            instance.addToEnd(10);
            instance.addToEnd(5);
            boolean expResult = true;
            boolean result = instance.search(5);
            assertEquals(result, expResult);
        } catch (Exception e) {
            System.out.print("число уже есть!");
        }
    }

    /**
     * Test of remove method, of class UniqueList.
     */
    @Test
    public void testRemove() throws Exception {
        try {
            System.out.println("remove");
            UniqueList instance = new UniqueList();
            instance.addToHead(5);
            instance.remove(5);
            boolean expResult = false;
            boolean result = instance.search(5);
            instance.remove(10);
        } catch (Exception e) {
            System.out.print("удаляешь то чего нету!!");
        }
    }

    @Test
    public void testRemove2() throws Exception {
        try {
            System.out.println("remove");
            UniqueList instance = new UniqueList();
            instance.addToHead(5);
            instance.remove(5);
            boolean expResult = false;
            boolean result = instance.search(5);
            assertEquals(result, expResult);
        } catch (Exception e) {
            System.out.print("удаляешь то чего нету!!");
        }
    }
}
