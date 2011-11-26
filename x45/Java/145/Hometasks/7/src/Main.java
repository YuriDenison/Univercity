/**
 * User: Volkman
 * Date: 27.03.2010
 * Time: 22:53:26
 */
public class Main {
    public static void main(String[] args) {
        Set<Integer> set = new Set<Integer>();
        set.add(2);
        set.add(3);
        set.add(5);
        Set<Integer> set1 = new Set<Integer>();
        set1.add(2);
        set1.add(4);
        set1.add(6);
        set.print();
        set1.print();

        System.out.println("");
        set.union(set1).print();
        set.intersection(set1).print();
    }
}
