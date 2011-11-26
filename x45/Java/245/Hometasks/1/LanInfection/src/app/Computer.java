package app;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

/**
 * Author: Volkman
 * Date: Sep 9, 2010 7:41:55 PM
 */
public class Computer {
    public static enum OperationSystem {
        WINDOWS, LINUX
    }

    public Computer() {
        Random rand = new Random();
        init(rand.nextDouble());
    }

    protected Computer(double probOs) {
        init(probOs);
    }

    private void init(double probOs) {
        isInfected = false;
        String file = "src/app/lan.properties";
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Boolean.parseBoolean(props.getProperty("develop_mode"))) {
            System.out.println("OS random: " + probOs);
        }
        if (probOs < Double.parseDouble(props.getProperty("probability_of_Windows_on_computer"))) {
            os = OperationSystem.WINDOWS;
            probabilityOfInfecting = Double.parseDouble(props.getProperty("probability_of_infection_on_Windows"));
        } else {
            os = OperationSystem.LINUX;
            probabilityOfInfecting = Double.parseDouble(props.getProperty("probability_of_infection_on_Linux"));
        }
    }

    public OperationSystem getOs() {
        return os;
    }

    public double getProbabilityOfInfecting() {
        return probabilityOfInfecting;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }

    private OperationSystem os;
    private double probabilityOfInfecting;              // probability Of Infecting
    private boolean isInfected;
}
