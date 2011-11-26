package app;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

/**
 * Author: Volkman
 * Date: Sep 9, 2010 9:45:26 PM
 */
public class Lan {
    public String[] simulate() {
        int numberOfSteps = Integer.parseInt(props.getProperty("number_of_steps"));
        String[] out = new String[numberOfSteps + 1];
        out[0] = ("Step 1: Windows Infected " + numberOfInfectedComputersWithWindows + "/" +
                numberOfComputersWithWindows + ", Linux Infected " +
                numberOfInfectedComputersWithLinux + "/" + numberOfComputersWithLinux);
        for (int i = 1; i < numberOfSteps + 1; i++) {
            String str = step();
            out[i] = ("Step " + (i + 1) + ": " + str);
        }
        for (int i = 0; i < numberOfSteps; i++)
            System.out.println(out[i]);
        return out;
    }

    public Lan() {
        String file = "src/app/lan.properties";
        props = new Properties();
        try {
            props.load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        numberOfComputers = Integer.parseInt(props.getProperty("number_of_computers"));
        devmode = Boolean.parseBoolean(props.getProperty("develop_mode"));

        rand = new Random();
        if (devmode) {
            rand.setSeed(Integer.parseInt(props.getProperty("seed")));
            System.out.println("DevMode on!");
            System.out.println("");
        }
        generateLan();
    }

    private void generateLan() {
        comp = new Computer[numberOfComputers];
        for (int i = 0; i < numberOfComputers; i++) {
            if (devmode)
                comp[i] = new Computer(rand.nextDouble());
            else
                comp[i] = new Computer();
        }
        generateConnections();
        generateInfection();

        numberOfComputersWithLinux = countLinuxComp();
        numberOfComputersWithWindows = numberOfComputers - numberOfComputersWithLinux;
    }

    private String step() {
        int[] resultOfStep = new int[numberOfComputers];
        for (int i = 0; i < numberOfComputers; i++) {
            if (!comp[i].isInfected()) {
                for (int j = 0; j < numberOfComputers; j++) {
                    if (conn[i][j] && i != j && comp[j].isInfected()) {
                        double prob = rand.nextDouble();
                        if (prob < comp[i].getProbabilityOfInfecting())
                            resultOfStep[i] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < numberOfComputers; i++) {
            if (resultOfStep[i] == 1) {
                comp[i].setInfected(true);
                numberOfInfectedComputers++;
                if (comp[i].getOs() == Computer.OperationSystem.WINDOWS)
                    numberOfInfectedComputersWithWindows++;
                else
                    numberOfInfectedComputersWithLinux++;
            }
        }

        return "Windows Infected " + numberOfInfectedComputersWithWindows + "/" + numberOfComputersWithWindows +
                ", Linux Infected " + numberOfInfectedComputersWithLinux + "/" + numberOfComputersWithLinux;
    }

    private void generateInfection() {
        numberOfInfectedComputers = Integer.parseInt(props.getProperty("number_of_infected_computers"));
        for (int i = 0; i < numberOfInfectedComputers; i++) {
            int cur = rand.nextInt(numberOfComputers);
            if (devmode) {
                System.out.println("nInfComp = " + cur);
            }
            comp[cur].setInfected(true);
            if (comp[cur].getOs() == Computer.OperationSystem.WINDOWS)
                numberOfInfectedComputersWithWindows++;
            else
                numberOfInfectedComputersWithLinux++;
        }
        numberOfInfectedComputers = numberOfInfectedComputersWithLinux + numberOfInfectedComputersWithWindows;
        if (devmode) {
            for (int i = 0; i < numberOfComputers; i++)
                System.out.println("comp[" + i + "] is " + comp[i].isInfected() + " OS = " + comp[i].getOs());
            System.out.println("");
        }
    }

    private void generateConnections() {
        double probConn = 1;
        probConn = Double.parseDouble(props.getProperty("probability_of_connection_between_computers"));

        conn = new boolean[numberOfComputers][numberOfComputers];
        for (int i = 0; i < numberOfComputers; i++) {
            for (int j = i + 1; j < numberOfComputers; j++) {
                double prob = rand.nextDouble();
                conn[i][j] = (prob < probConn);
                conn[j][i] = conn[i][j];
            }
        }
        if (devmode)
            printConn();
    }

    private void printConn() {
        for (int i = 0; i < numberOfComputers; i++) {
            for (int j = 0; j < numberOfComputers; j++) {
                int n = conn[i][j] ? 1 : 0;
                System.out.print(n + " ");
            }
            System.out.println("");
        }
    }


    private int countLinuxComp() {
        int num = 0;
        for (int i = 0; i < numberOfComputers; i++)
            if (comp[i].getOs() == Computer.OperationSystem.LINUX)
                num++;
        return num;
    }

    private Computer[] comp;
    private boolean[][] conn;       // table of connections
    private Properties props;
    private Random rand;
    private boolean devmode;

    private int numberOfComputers;              // number Of Computers
    private int numberOfInfectedComputers;               // number of infected computers at start
    private int numberOfComputersWithWindows;           // number Of Computers with Windows
    private int numberOfComputersWithLinux;           // number Of Computers with Linux
    private int numberOfInfectedComputersWithWindows;            // number Of Infected Computers with Windows
    private int numberOfInfectedComputersWithLinux;            // number Of Infected Computers with Linux
}
