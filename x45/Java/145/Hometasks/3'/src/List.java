import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 01.03.2010
 * Time: 20:04:55
 * To change this template use File | Settings | File Templates.
 */
public class List {
    private int size;
    private int[] value;

    public List(int num) {
        value = new int[num];
        size = 0;
    }

    public void add(int n, int a) {
        size++;
        for(int i = size - 1; i > n; i--){
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

    public boolean contains(int a){
        for(int i = 0; i < size; i++){
            if(value[i] == a)
                return true;
        }
        return false;
    }

    public void clear(){
        size = 0;
    }

    public int getValue(int i) {
        return value[i];
    }

    public void setValue(int i, int M) {
        this.value[i] = M;
    }

    public int getSize() {
        return size;
    }

    public int find(int s){
        for(int i = 0; i < size; i++){
            if(value[i] == s)
                return i;
        }
        return -1;
    }

}
