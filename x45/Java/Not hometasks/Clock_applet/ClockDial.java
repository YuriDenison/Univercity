/*
 * ClockDial.java
 *
 * Created on 7 ������� 2004 �., 11:32
 */
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
/**
 *
 * @author  �������� ��������
 */
public class ClockDial {
    
    private double diam;
    private BufferedImage dial;
    private Color dialColor;
    private Color hairLineColor;
    
    /** ������� ���������� <code>ClockDial</code>. ��� ��������
     *  ���������� ������� ������� ����������.
     *
     *  @param diam ������� ����������.
     *
     *  @param dialColor ���� ����������.
     *
     *  @param hairLineColor ���� �������� �� ����������.
     *
     *  @exception IncorrectParametersException - ���� ������� ������
     *  ��� ����� ����.
     */
    public ClockDial(double diam, Color dialColor, Color hairLineColor)
            throws IncorrectParametersException {
        //��������� �������
        if(diam <= 0)
            throw new IncorrectParametersException();
        
        this.diam = diam;
        this.dialColor = dialColor;
        this.hairLineColor = hairLineColor;
        
        //������� �������
        dial = new BufferedImage((int)diam, (int)diam,
                                BufferedImage.TYPE_INT_ARGB);
        
        //������� �������� �� ����������
        Rectangle2D.Double r = new Rectangle2D.Double(-diam/40, -diam/60, diam/20, diam/30);
        
        AffineTransform at = new AffineTransform();
        //������� ������ ��������� � ����� ����������
        at.translate(diam/2, diam/2);
        //��������� ������� ��������������
        AffineTransform prev = (AffineTransform)at.clone();
        //�������� ����������� ��������
        Graphics2D g2 = dial.createGraphics();
        
        //����������� ��� �������� ������
        g2.setBackground(dialColor);
        g2.clearRect(0, 0, (int)diam, (int)diam);
        
        //������������� ���� ��������
        g2.setColor(hairLineColor);
        //������ 12 �������
        for(int i = 0; i < 12; i++)
        {
            //������� ����� ��������� � ���� ���������� (����,
            //��� ������ ���� ���������� �����)
            at.translate(-(diam/2 - r.width), 0);
            //������������ ����� ������������ ������ ����������
            at.rotate(Math.toRadians(30*i), diam/2 - r.width, 0);
            //������������� ������� ��������������
            g2.setTransform(at);
            //������ �����
            g2.fill(r);
            //��������������� ��������� ��������������
            at.setTransform(prev);
        }
    }
    
    /**
     * ���������� ����������� ����������.
     *
     * @return BufferedImage �������, ���������� ����������� ����������
     */
    public BufferedImage getClockDial() {
        return dial;
    }
}
