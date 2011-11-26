package figures;

import java.awt.*;

/**
 * @author Yuri Denison
 * @date 24.12.10
 */
public class Rectangle extends Polygon implements IFigure{
    private Color color = Color.black;
    private final Point p1 = new Point(80, 80);
    private final Point p2 = new Point(80, 150);
    private final Point p3 = new Point(180, 150);
    private final Point p4 = new Point(180, 80);


    public Rectangle() {
        this.addPoint(p1.x, p1.y);
        this.addPoint(p2.x, p2.y);
        this.addPoint(p3.x, p3.y);
        this.addPoint(p4.x, p4.y);
    }

    @Override
    public void move(int x, int y) {
        this.translate(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillPolygon(this);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

}
