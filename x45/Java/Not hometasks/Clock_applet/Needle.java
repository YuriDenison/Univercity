/*
 * Needle.java
 *
 * Created on 3 Октябрь 2004 г., 19:37
 */
import java.awt.*;
import java.awt.geom.*;
/**
 * Этот класс предназначен для создания стрелки часов
 *
 * @author  Стаценко Владимир
 */
public class Needle {
    
    private GeneralPath shape;
    private double length, width;
    
    /** Создает экземпляры класса <code>Needle</code>.
     *  При создании необходимо указать длину и ширину стрелки.
     *  Длина должна быть больше ширины.
     *
     * @param length длина стрелки
     * @param width длина стрелки
     *
     * @exception IncorrectParametersException - если длина меньше или
     *    равна нулю, если ширина меньше или равна нулю, если длина
     *    меньше или равна ширине.
     */
    public Needle(double length, double width)
                    throws IncorrectParametersException {
        //проверяем параметры                
        if(length <= 0 || width <=0 || length <= width)
            throw new IncorrectParametersException();
        
        this.length = length;
        this.width = width;
        
        //создаем графический объект
        shape = new GeneralPath();
        shape.moveTo(0f, (float)width/2);
        shape.lineTo((float)width/2, 0);
        shape.lineTo((float)length, (float)width/2);
        shape.lineTo((float)width/2, (float)width);
        shape.closePath();
    }
    
    /**
     *  Возвращает стрелку в виде объекта типа <code>Shape</code>.
     *  @return Shape - фигура, содержащая изображение стрелки
     */
    public Shape getNeedle() {
        return shape;
    }
    
    /**
     *  Возвращает центр вращения стрелки (точку, вокруг которой
     *  стрелка будет вращаться)
     *  @return Point2D.Double - точка, содержащая координаты центра вращения
     *  стрелки
     */
    public Point2D.Double getRotationCenter() {
        Point2D.Double rc = new Point2D.Double();
        rc.x = rc.y = width/2;
        return rc;
    }
}
