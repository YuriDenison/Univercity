import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 08.10.2008
 * Time: 9:45:50
 * To change this template use File | Settings | File Templates.
 */
public class PointSet {
    private Point[] p;

    public Point getP(int i){
        return p[i];
    }

    public void setP(int i, double x, double y){
        p[i].set(x, y);
    }

    public Rectangle minRectangle(){
        Rectangle r = new Rectangle();
        int i;
        double minX = 1000000, minY = 1000000,
               maxX = -1000000, maxY = -100000;
        for(i=0;i < p.length;i++){
            if( p[i].getX() < minX){
                 minX = p[i].getX();
            }
            if(p[i].getY() < minY){
                 minY = p[i].getY();
            }
            if( p[i].getX() > maxX){
                 maxX = p[i].getX();
             }
            if(p[i].getY() > maxY){
                 maxY = p[i].getY();
            }
        }
        r.set1(minX, minY);
        r.set2(maxX, maxY);
        return r;
    }

    Curcle minCurcle(){
        Curcle c = new Curcle();
        c.set(0, 0);
        int i;
        double maxL = 0;
        for(i=0; i < p.length; i++){
            if((p[i].dist(c.getC())) > maxL){
            maxL = p[i].dist(c.getC());
            }
        }
        c.setR(maxL);
        return c;
    }

    public Square minSquare(){
        Square r = new Square();
        int i,j = 0;
        double minX = 1000000, minY = 1000000,
               maxX = -1000000, maxY = -100000;
        for(i=0;i < p.length;i++){
            if( p[i].getX() < minX){
                 minX = p[i].getX();
            }
            if(p[i].getY() < minY){
                 minY = p[i].getY();
            }
            if( p[i].getX() > maxX){
                 maxX = p[i].getX();
             }
            if(p[i].getY() > maxY){
                 maxY = p[i].getY();
            }
        }
        if( (maxX - minX) <= (maxY - minY) ){
            maxX = maxY - minY + minX;
        }
        else{
            maxY = maxX - minX + minY;
        }
        r.set1(minX, minY);
        r.set2(maxX, maxY);
        return r;
    }

    public void Input(){
        int i;
        for(i = 0;i <p.length;i++){
            try{
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream("src/text.txt")));
            String line = null;
            do{
                reader.readLine();
                if(line == null) break;
                double d = Double.parseDouble(line);
                if(i%2 == 0) p[i].setX(d);
                if(i%2 == 1) p[i].setY(d);
            }while(true);
            }
            catch (IOException e){
                System.out.println("I/O");
            }
            catch (NumberFormatException e){
                System.out.println("Not a number");
            }
        }
    }
}
