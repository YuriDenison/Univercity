package filesystem.ui;

import filesystem.IFileSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Yuri Denison
 */
public class ConsoleUI {
    private IFileSystem fileSystem;

    public ConsoleUI(IFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void start() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nInput command: ");

        String input;
        while (!(input = br.readLine().trim()).equals("exit")) {
            if (input.equals("help")) {
                System.out.println("Valid commands: create, add, delete, contains, exit");
            } else if (input.startsWith("create")) {
                String str = input.substring(input.indexOf(" "));
                if (str.trim().equals("")) {
                    System.out.println("Bad parameters. Should be: create <filePath>");
                } else {
                    boolean result = fileSystem.createNewFile(str);
                    System.out.println((result) ? "successful" : "fail!");
                }

            } else if (input.startsWith("add")) {
                String str[] = input.split(" ");
                if (str.length != 3) {
                    System.out.println("Bad parameters. Should be: add <filePath> <path in FileSystem>");
                } else {
                    boolean result = fileSystem.insertFile(str[1], str[2]);
                    System.out.println((result) ? "successful" : "fail!");
                }
            } else if (input.startsWith("contains")) {
                boolean result = fileSystem.contains(input.substring(input.indexOf(" ") + 1));
                System.out.println((result) ? "contains!" : "not contains!");
            } else if (input.startsWith("delete")) {
                boolean result = fileSystem.deleteFile(input.substring(input.indexOf(" ") + 1));
                System.out.println((result) ? "successful" : "fail!");
            } else {
                System.out.println("No such command. Type 'help' to show available commands.");
            }

            System.out.println("\nInput command: ");
        }
        System.out.println("Bye bye!");
        fileSystem.close();
    }
}

