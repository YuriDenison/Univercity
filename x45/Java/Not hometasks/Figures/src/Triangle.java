import static java.lang.Math.*;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 24.09.2008
 * Time: 23:53:01
 * To change this template use File | Settings | File Templates.
 */
public class Triangle implements Figure{
    public Point p1;
    public Point p2;
    public Point p3;

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
    public double getX3() {
        return p2.getX();
    }
    public double getY3() {
        return p3.getY();
    }
    public void set1(double x, double y){
        p1.set(x, y);
    }
    public void set2(double x, double y){
        p2.set(x, y);
    }
    public void set3(double x, double y){
        p3.set(x, y);
    }

    public double Lengh_1(Point p1, Point p2){
        return p1.dist(p2);
    }

    public double Lengh_2(Point p1, Point p3){
        return p1.dist(p3);
    }

    public double Lengh_3(Point p2, Point p3){
        return p2.dist(p3);
    }

    public double Perimeter(){
        return ( Lengh_1(p1,p2) + Lengh_2(p1,p3) + Lengh_3(p2,p3) );
    }

    public double Area(){
        return Math.sqrt
                (((Perimeter() / 2) - Lengh_1(p1, p2)) *
                        ((Perimeter() / 2) - Lengh_2(p1, p3)) *
                        ((Perimeter() / 2) - Lengh_3(p2, p3)));
    }

    public void MoveTo(Point p){
        p1.MoveTo(p);
        p2.MoveTo(p);
        p3.MoveTo(p);
    }

    // homothety relatively Point p1
    public void Homothety(double n){
        p2.MoveTo( p1.distX(p2)*(n-1), p1.distY(p2)*(n-1) );
        p3.MoveTo( p1.distX(p3)*(n-1), p1.distY(p3)*(n-1) );
    }
}
