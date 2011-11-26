import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 21.05.2010
 * Time: 13:53:13
 */
public class Sort<T> {

    public void BubbleSort(ArrayList<T> mas, Comparator<T> comp) {
        boolean t = true;
        while (t) {
            t = false;
            for (int j = 0; j < mas.size() - 1; j++) {
                if (comp.compare(mas.get(j),  mas.get(j + 1)) > 0) {
                    T obj = mas.get(j);
                    mas.set(j, mas.get(j+1));
                    mas.set(j+1, obj);
                    t = true;
                }
            }
        }
    }

    public String result(ArrayList<T> mas){
        String str = "";
        for(T obj: mas){
            str += obj.toString() + " ";
        }
        return str;
    }
}
