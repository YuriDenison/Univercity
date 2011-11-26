/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 24.09.2008
 * Time: 23:54:59
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle implements Figure{
    private Point p1;
    private Point p2;

    public double getX1() {
        return p1.getX();
    }
    public double getY1() {
        return p1.getY();
    }
    public double getX2() {
        return p2.getX();
    }
    public double getY2() {
        return p2.getY();
    }
    public void set1(double x, double y){
        p1.set(x, y);
    }
    public void set2(double x, double y){
        p2.set(x, y);
    }

    public double Area(){
        return (p1.distX(p2) * p1.distY(p2));
    }

    public double Perimeter(){
        return (2 * (p1.distX(p2) + p1.distY(p2)) );
    }

    public void MoveTo(Point p){
        p1.MoveTo(p);
        p2.MoveTo(p);
    }

    public double Lengh_1(){
        return p1.distX(p2);
    }
    public double Lengh_2(){
        return p1.distY(p2);
    }

    // homothety relatively Point p1
    public void Homothety(double n){
        p2.MoveTo( p1.distX(p2)*(n-1), p1.distY(p2)*(n-1) );
    }
}