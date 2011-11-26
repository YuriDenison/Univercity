import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 02.03.2010
 * Time: 18:22:18
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("1. Input bracket sequence: ");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        String s = br.readLine();
        Stack stack = new Stack(s.length());
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    stack.add(1);
                    break;
                case ')':
                    if (stack.isEmpty()) {
                        System.out.println("Sequence is incorrect");
                        flag = true;
                    } else
                        stack.pop();
                    break;
            }
            if (flag)
                break;
        }
        if (!stack.isEmpty() && !flag)
            System.out.println("Sequence is incorrect");
        if (stack.isEmpty() && !flag)
            System.out.println("Sequence is correct");

        System.out.println("");
        System.out.println("Now we will test a hashtable: ");
        final String initMessage = "\"Set the size of hashtable before this operation. " +
                "See 'help' for desired function";
        StringList start = null;
        int len = 0;
        HashTable ht = null;
        Boolean init = false;
        System.out.println("Enter command or enter 'help' to show a list of commands:");
        String line = br.readLine();
        while (!line.equals("quit")) {
            if (line.equals("help")) {
                System.out.println("'len' - set the size of hashtable");
                System.out.println("'init' - initialize new hashtable");
                System.out.println("'add' - add the element to hashtable");
                System.out.println("'contains' - check whether there is an element in a hash table");
                System.out.println("'show hash' - to show hash of the element");
                System.out.println("'rehash' - rehash all elements in hashtable with another hashing function");
                System.out.println("'remove' - remove the element from the hashtable");
                System.out.println("'clear' - clear the hashtable");
                System.out.println("'quit' - quit the program");
                System.out.println("");
            }
            if (line.equals("len")) {
                if (!init) {
                    System.out.println("Enter the expected size of the hashtable: ");
                    len = Integer.parseInt(br.readLine());
                    if (len <= 0) {
                        System.out.println("Size must be positive number!!");
                        len = 0;
                    }
                } else {
                    System.out.println("Do you want remove curent hashtable and set size for new one? (Yes/No)");
                    String answer = br.readLine();
                    if (answer.equals("Yes")) {
                        ht.clear();

                        System.out.println("The hashtable has cleared." +
                                "Now enter the expected size of new hashtable: ");
                        len = Integer.parseInt(br.readLine());
                        if (len <= 0) {
                            System.out.println("Size must be positive number!!");
                            len = 0;
                        }

                    } else {
                        System.out.println("Hashtable hasn't removed.");
                    }
                }
                System.out.println("");
            }

            if (line.equals("init")) {
                if (len == 0) {
                    System.out.println("Set the size of hashtable before this operation. " +
                            "See 'help' for desired function");
                } else {
                    System.out.println("Chose one of the default hash functions (1..3) or " +
                            "enter the filename with another one: ");
                    String type = (br.readLine());
                    if(Integer.parseInt(type) == 1){
                        type = "Hash1";
                    }
                    if(Integer.parseInt(type) == 2){
                        type = "Hash2";
                    }
                    if(Integer.parseInt(type) == 3){
                        type = "Hash3";
                    }
                    init = true;
                    start = new StringList(len);
                    ht = new HashTable(len, type);
                    for (int i = 0; i < start.getSize(); i++) {
                        ht.add(start.getValue(i));
                    }
                }
                System.out.println("Initialization completed.");
                System.out.println("");
            }

            if (line.equals("add")) {
                if (init) {
                    System.out.println("Enter string you want to add to hashtable: ");
                    String st = br.readLine();
                    if (!ht.contains(st)) {
                        ht.add(st);
                        start.add(start.getSize(), st);
                        System.out.println("Adding completed");
                    } else {
                        System.out.println("This element has already added to hashtable");
                    }
                } else {
                    System.out.println(initMessage);
                }
                System.out.println("");
            }

            if (line.equals("contains")) {
                if (init) {
                    System.out.println("Enter string you want to find to hashtable: ");
                    String st = br.readLine();
                    if (ht.contains(st))
                        System.out.println("There is '" + st + "' in hashtable");
                    else
                        System.out.println("There is not '" + st + "' in hashtable");
                } else {
                    System.out.println(initMessage);
                }
                System.out.println("");
            }

            if (line.equals("show hash")) {
                if (init) {
                    System.out.println("Enter string, which hash you want to see: ");
                    String st = br.readLine();
                    if (!ht.contains(st))
                        System.out.println("There is not '" + st + "' in hashtable");
                    else
                        System.out.println("The hash of '" + st + "' is " + ht.showHash(st));
                } else {
                    System.out.println(initMessage);
                }
                System.out.println("");
            }

            if (line.equals("rehash")) {
                if (!init) {
                    System.out.println(initMessage);
                } else {
                    ht.clear();
                    System.out.println("Chose one of the default hash functions (1..3) or " +
                            "enter the filename with another one: ");
                    String type = (br.readLine());
                    if(Integer.parseInt(type) == 1){
                        type = "Hash1";
                    }
                    if(Integer.parseInt(type) == 2){
                        type = "Hash2";
                    }
                    if(Integer.parseInt(type) == 3){
                        type = "Hash3";
                    }
                    ht = new HashTable(len, type);
                    for (int i = 0; i < start.getSize(); i++) {
                        ht.add(start.getValue(i));
                    }
                    System.out.println("Rehashing completed.");
                }
                System.out.println("");
            }

            if (line.equals("remove")) {
                if (init) {
                    System.out.println("Enter string you want to remove from hashtable: ");
                    String st = br.readLine();
                    if(!ht.contains(st))
                        System.out.println("There is not '" + st + "' in hashtable");
                    else{
                        ht.remove(st);
                        start.remove(start.find(st));
                    }
                } else {
                    System.out.println(initMessage);
                }
                System.out.println("");
            }

            if (line.equals("clear")) {
                if (init) {
                    System.out.println("Do you really want to clear hashtable? (Yes/no)");
                    String answer = br.readLine();
                    if (answer.equals("Yes")) {
                        ht.clear();
                        start.clear();
                        System.out.println("Clearing completed");
                    } else {
                        System.out.println("Clearing hasn't completed");
                    }

                } else {
                    System.out.println(initMessage);
                }
                System.out.println("");
            }

            System.out.println("Enter command:");
            line = br.readLine();
        }

    }
}
