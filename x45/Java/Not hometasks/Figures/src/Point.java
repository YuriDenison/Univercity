import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 24.09.2008
 * Time: 22:36:43
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    private double x;
    private double y;

    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    public double distX(Point p1){
        return Math.abs(x - p1.x);
    }

    public double distY(Point p1){
        return Math.abs(y - p1.y);
    }

    public double dist(Point p){
        return Math.sqrt( (x - p.x)*(x - p.x) + (y - p.y)*(y - p.y));
    }

    public void MoveTo(Point p){
        x = x + p.x;
        y = y + p.y;
    }

    public void MoveTo(double a, double b){
        x = x + a;
        y = y + b;
    }
}
