/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 27.02.2010
 * Time: 16:44:47
 * To change this template use File | Settings | File Templates.
 */
public class Stack {
    private List list;

    public Stack(int num){
        list = new List(num);
    }

    public void add(int num){
        list.add(list.getSize(), num);
    }

    public void pop(){
        list.remove(list.getSize());
    }

    public int top(){
        return list.getValue(list.getSize());
    }

    public boolean isEmpty(){
        return list.getSize() == 0;
    }
}
