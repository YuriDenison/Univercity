package figures;

import java.awt.*;

/**
 * @author Yuri Denison
 * @date 23.12.10
 */
public class MyPolygon extends Polygon implements IFigure{
    private Point center = new Point(100, 100);
    private int radius = 50;
    private Color color = Color.black;
    private int sides = 5;


    public MyPolygon() {
        initPolygon();
    }

    private void initPolygon() {
        this.reset();
        for(int i = 0; i < sides; i++) {
            this.addPoint((int) (center.x + radius * Math.cos(i * 2 * Math.PI / sides)),
                           (int) (center.y + radius * Math.sin(i * 2 * Math.PI / sides)));
        }
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


    public void setNumberOfSides(int sides) {
        this.sides = sides;
        initPolygon();
    }
}
