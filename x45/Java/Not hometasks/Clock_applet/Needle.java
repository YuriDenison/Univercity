/*
 * Needle.java
 *
 * Created on 3 ������� 2004 �., 19:37
 */
import java.awt.*;
import java.awt.geom.*;
/**
 * ���� ����� ������������ ��� �������� ������� �����
 *
 * @author  �������� ��������
 */
public class Needle {
    
    private GeneralPath shape;
    private double length, width;
    
    /** ������� ���������� ������ <code>Needle</code>.
     *  ��� �������� ���������� ������� ����� � ������ �������.
     *  ����� ������ ���� ������ ������.
     *
     * @param length ����� �������
     * @param width ����� �������
     *
     * @exception IncorrectParametersException - ���� ����� ������ ���
     *    ����� ����, ���� ������ ������ ��� ����� ����, ���� �����
     *    ������ ��� ����� ������.
     */
    public Needle(double length, double width)
                    throws IncorrectParametersException {
        //��������� ���������                
        if(length <= 0 || width <=0 || length <= width)
            throw new IncorrectParametersException();
        
        this.length = length;
        this.width = width;
        
        //������� ����������� ������
        shape = new GeneralPath();
        shape.moveTo(0f, (float)width/2);
        shape.lineTo((float)width/2, 0);
        shape.lineTo((float)length, (float)width/2);
        shape.lineTo((float)width/2, (float)width);
        shape.closePath();
    }
    
    /**
     *  ���������� ������� � ���� ������� ���� <code>Shape</code>.
     *  @return Shape - ������, ���������� ����������� �������
     */
    public Shape getNeedle() {
        return shape;
    }
    
    /**
     *  ���������� ����� �������� ������� (�����, ������ �������
     *  ������� ����� ���������)
     *  @return Point2D.Double - �����, ���������� ���������� ������ ��������
     *  �������
     */
    public Point2D.Double getRotationCenter() {
        Point2D.Double rc = new Point2D.Double();
        rc.x = rc.y = width/2;
        return rc;
    }
}
