package app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;


/**
 * Author: Volkman
 * Date: Sep 12, 2010 5:50:32 PM
 */
public class LanTest {
    @Before
    public void setUp() throws Exception {
        file = "src/app/lan.properties";
        properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        numberOfComputers = properties.getProperty("number_of_computers");
        numberOfInfectedComputers = properties.getProperty("number_of_infected_computers");
        numberOfSteps = properties.getProperty("number_of_steps");
        seed = properties.getProperty("seed");

        properties.setProperty("develop_mode", "true");
        properties.setProperty("seed", "1000");
        properties.setProperty("number_of_computers", "20");
        properties.setProperty("number_of_infected_computers", "2");
        properties.setProperty("number_of_steps", "100");
        properties.store(new FileOutputStream(file), "LanTest configuration");
    }

    @After
    public void tearDown() throws Exception {
        properties.setProperty("develop_mode", "false");
        properties.setProperty("seed", seed);
        properties.setProperty("number_of_computers", numberOfComputers);
        properties.setProperty("number_of_infected_computers", numberOfInfectedComputers);
        properties.setProperty("number_of_steps", numberOfSteps);
        properties.store(new FileOutputStream(file), "");
    }

    @Test
    public void testSimulate() throws Exception {
        Lan lan = new Lan();
        String[] str = lan.simulate();
        assertEquals(str[99], "Step 100: Windows Infected 6/13, Linux Infected 1/7");
        assertEquals(str[74], "Step 75: Windows Infected 5/13, Linux Infected 1/7");
        assertEquals(str[48], "Step 49: Windows Infected 4/13, Linux Infected 1/7");
        assertEquals(str[24], "Step 25: Windows Infected 2/13, Linux Infected 1/7");
        assertEquals(str[14], "Step 15: Windows Infected 1/13, Linux Infected 1/7");

    }

    private Properties properties;
    private String numberOfComputers;
    private String seed;
    private String numberOfInfectedComputers;
    private String numberOfSteps;
    private String file;
}
