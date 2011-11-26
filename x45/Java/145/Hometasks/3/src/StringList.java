import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 01.03.2010
 * Time: 20:04:55
 * To change this template use File | Settings | File Templates.
 */
public class StringList {
    private int size;
    private String[] value;

    public StringList(int num) {
        value = new String[num];
        this.size = 0;
    }

    public void add(int n, String a) {
        size++;
        for(int i = size; i > n; i--){
            value[i+1] = value[i];
        }
        value[n] = a;
    }

    public void remove(int n) {
        if (n <= size) {
            for (int i = n; i < size-1; i++) {
                value[i] = value[i+1];
            }
            size--;
        }
    }

    public boolean contains(String a){
        for(int i = 0; i < size; i++){
            if(value[i].equals(a))
                return true;
        }
        return false;
    }

    public void clear(){
        size = 0;
    }

    public String getValue(int i) {
        return value[i];
    }

    public void setValue(int i, String str) {
        this.value[i] = str;
    }

    public int getSize() {
        return size;
    }

    public int find(String str){
        for(int i = 0; i < size; i++){
            if(value[i].equals(str))
                return i;
        }
        return -1;
    }

}
