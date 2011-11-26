package Code;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Volkman
 * Date: 29.04.2010
 * Time: 1:54:45
 */
public class Main {
    public static void main(String[] args) {
        String[] botList = Logic.getBotList();

        if(botList != null)
            new Board(8, 8).showForm(botList);
    }
}
