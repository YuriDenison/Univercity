import java.util.ArrayList;

/**
 * User: Volkman
 * Date: 28.03.2010
 * Time: 13:56:56
 */
public class Set<Type> {
    private List<Type> value;

    public Set() {
        value = new List<Type>();

    }

    public void add(Type val) {
        value.add(val);
    }

    public void remove(Type val) {
        value.remove(val);
    }

    public boolean contains(Type val) {
        return value.contains(val);
    }

    public Set<Type> intersection(Set<Type> set) {
        Set<Type> res = new Set<Type>();
        for (Type a : set.getValue()) {
            if (contains(a)) {
                res.add(a);
            }

        }
        return res;
    }

    public Set<Type> union(Set<Type> set) {
        Set<Type> res = new Set<Type>();
        for (Type a : value) {
            res.add(a);
        }
        for (Type a : set.getValue()) {
            if (!contains(a))
                res.add(a);
        }
        return res;
    }

    public String print() {
        String str = "";
        for (Type a : value) {
            System.out.print(a + " ");
            str += a + " ";
        }
        System.out.println("");
        return str;
    }

    public List<Type> getValue() {
        return value;
    }
}
