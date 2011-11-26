package figures;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Yuri Denison
 * @date 24.12.10
 */
public class Ellipse extends Ellipse2D.Float implements IFigure{
    private Color color = Color.black;

    public Ellipse() {
        super(100, 100, 200, 100);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(color);
        ((Graphics2D) graphics).fill(this);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean contains(int x, int y) {
        return contains((float) x, (float) y);
    }
}
