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
    IHash h;

    public int hash(String str){
        return h.hash(str, len);
    }

    public HashTable(int num, IHash h) {
        arr = new StringList[num];
        for (int i = 0; i < num; ++i)
            arr[i] = new StringList(num);
        this.len = num;
        this.h = h;
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

    public StringList returnList(){
        StringList stl = new StringList(len);
        System.out.println(stl.getSize());
        for(int i = 0; i < len; i++){
            for(int j = 0; j < arr[i].getSize(); j++){
                stl.add(stl.getSize(), arr[i].getValue(j));
                System.out.println(stl.getSize());
            }
        }
        return stl;
    }
}





