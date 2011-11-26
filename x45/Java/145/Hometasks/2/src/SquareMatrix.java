/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 19.02.2010
 * Time: 17:03:13
 * To change this template use File | Settings | File Templates.
 */
public class SquareMatrix extends Matrix{

    public SquareMatrix(int size){
        this.height = size;
        this.lengh = size;
        value = new int[size][size];
    }
}
