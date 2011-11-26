/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 24.09.2008
 * Time: 22:42:58
 * To change this template use File | Settings | File Templates.
 */
public class Curcle implements Figure{
    public Point center;
    public double rad;

    public double getX() {
        return center.getX();
    }
    public double getY() {
        return center.getY();
    }
    public Point getC(){
        return center;
    }
    public double getR(){
        return rad;
    }
    public void set(double x, double y){
        center.set(x, y);
    }
    public void setR(double r){
        this.rad = r;
    }

    public double Area(){
        return (Math.PI * rad * rad);
    }

    public double Perimeter(){
        return (2 * Math.PI * rad);
    }

    public void MoveTo(Point p){
        center.MoveTo(p);
    }

    public double Lengh_side_square(double rad){
        return rad * Math.sqrt(2);
    }

    public void Homothety(double n){
        rad = rad * n;
    }

}
