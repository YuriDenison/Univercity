import static java.lang.Class.forName;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 01.03.2010
 * Time: 20:28:18
 * To change this template use File | Settings | File Templates.
 */
public class HashTable{
    private StringList[] arr;
    private int len;
    private String filename;

    public int hash(String str){
        try{
            IHash obj = (IHash) forName(filename).newInstance();
            return obj.hash(str, len);
        }
        catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundException");
            System.exit(0);
        }
        catch(IllegalAccessException e1){
            System.out.println("IllegalAccessException");
            System.exit(0);
        }
        catch(InstantiationException e){
            System.out.println("InstantiationException");
            System.exit(0);
        }
        return -1;
    }

    public HashTable(int num, String filename) {
        arr = new StringList[num];
        for (int i = 0; i < arr.length; ++i)
            arr[i] = new StringList(num);
        this.len = num;
        this.filename = filename;
    }

    public void add(String str) {
        int hash = hash(str);
        arr[hash].add(arr[hash].getSize(), str);
    }

    public int showHash(String str){
        return hash(str);
    }

    public void remove(String str) {
        boolean inc = arr[hash(str)].contains(str);
        if (inc) {
            int pos = arr[hash(str)].find(str);
            arr[hash(str)].remove(pos);
        }
    }

    public boolean contains(String str) {
        return arr[hash(str)].contains(str);
    }

    public void clear() {
        for (int i = 0; i < len; i++) {
            arr[i].clear();
        }
    }
}





