/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 08.10.2008
 * Time: 9:40:44
 * To change this template use File | Settings | File Templates.
 */
public class StaticCurcle extends Curcle{
    private final double rad = 1;
    public boolean CheckSquare(Square s){
        if(s.Lengh_1() >= 1){
            return true;
        }
        else{
            return false;
        }
    }
}
