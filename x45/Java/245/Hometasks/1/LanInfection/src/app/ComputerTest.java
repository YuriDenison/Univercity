package app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: Volkman
 * Date: Sep 12, 2010 8:38:20 PM
 */
public class ComputerTest {
    Computer comp = new Computer(0.89);

    @Test
    public void testGetOs() throws Exception {
        assertEquals(comp.getOs(), Computer.OperationSystem.LINUX);
    }

    @Test
    public void testGetProbInf() throws Exception {
        assertEquals(comp.getProbabilityOfInfecting(), 1.0E-4, 0.0001);
    }

    @Test
    public void testIsInfected() throws Exception {
        assertEquals(comp.isInfected(), false);
    }

    @Test
    public void testSetInfected() throws Exception {
        comp.setInfected(true);
        assertEquals(comp.isInfected(), true);
        comp.setInfected(false);
        assertEquals(comp.isInfected(), false);
    }
}
