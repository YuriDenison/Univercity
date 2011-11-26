package figures;


import java.awt.*;

/**
 * @author Yuri Denison
 * @date 23.12.10
 */
public class Triangle extends Polygon implements IFigure{
    private Color color = Color.black;
    private final Point p1 = new Point(50, 50);
    private final Point p2 = new Point(200, 60);
    private final Point p3 = new Point(75, 160);


    public Triangle() {
        this.addPoint(p1.x, p1.y);
        this.addPoint(p2.x, p2.y);
        this.addPoint(p3.x, p3.y);
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
