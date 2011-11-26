import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author: Yuri Denison
 * @since: 28.06.11
 */

public class Main {

    public static void main(String args[]) throws IOException {
        String inputFileName = "src/default.txt";
        String outputPath = "/out/";
        try {
            inputFileName = args[0];
            outputPath = args[1];
        } catch (Exception e) {
            System.err.println("No input filename found. Default parameters will be used.");
        }
        PrintWriter out1;
        PrintWriter out2;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(inputFileName));
            out1 = new PrintWriter(new FileWriter(outputPath + "/out1.txt"));
            out2 = new PrintWriter(new FileWriter(outputPath + "/out2.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("No such file found.");
            return;
        }

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String cur;
        while ((cur = br.readLine()) != null) {
            cur = cur.trim();
            if (map.containsKey(cur)) {
                map.put(cur, map.get(cur) + 1);
            } else {
                map.put(cur, 1);
            }
        }

        Vector<String> v = new Vector<String>(map.keySet());
        Collections.sort(v);
        for (String line : v) {
            int num;
            out1.printf("%s\n", line);
            if ((num = map.get(line)) > 1) {
                out2.printf("%s - %d\n", line, num);
            }
        }

        br.close();
        out1.close();
        out2.close();
    }

}
