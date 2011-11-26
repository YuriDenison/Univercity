/*
 * ClockDial.java
 *
 * Created on 7 Октябрь 2004 г., 11:32
 */
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
/**
 *
 * @author  Стаценко Владимир
 */
public class ClockDial {
    
    private double diam;
    private BufferedImage dial;
    private Color dialColor;
    private Color hairLineColor;
    
    /** Создает экземпляры <code>ClockDial</code>. При создании
     *  необходимо указать диаметр циферблата.
     *
     *  @param diam диаметр циферблата.
     *
     *  @param dialColor цвет циферблата.
     *
     *  @param hairLineColor цвет черточек на циферблате.
     *
     *  @exception IncorrectParametersException - если диаметр меньше
     *  или равен нулю.
     */
    public ClockDial(double diam, Color dialColor, Color hairLineColor)
            throws IncorrectParametersException {
        //проверяем диаметр
        if(diam <= 0)
            throw new IncorrectParametersException();
        
        this.diam = diam;
        this.dialColor = dialColor;
        this.hairLineColor = hairLineColor;
        
        //создаем рисунок
        dial = new BufferedImage((int)diam, (int)diam,
                                BufferedImage.TYPE_INT_ARGB);
        
        //создаем черточку на циферблате
        Rectangle2D.Double r = new Rectangle2D.Double(-diam/40, -diam/60, diam/20, diam/30);
        
        AffineTransform at = new AffineTransform();
        //смещаем начало координат в центр циферблата
        at.translate(diam/2, diam/2);
        //сохраняем текущее преобразование
        AffineTransform prev = (AffineTransform)at.clone();
        //получаем графический контекст
        Graphics2D g2 = dial.createGraphics();
        
        //закрашиваем все заданным цветом
        g2.setBackground(dialColor);
        g2.clearRect(0, 0, (int)diam, (int)diam);
        
        //устанавливаем цвет черточек
        g2.setColor(hairLineColor);
        //рисуем 12 штрихов
        for(int i = 0; i < 12; i++)
        {
            //смещаем центр координат к краю циферблата (туда,
            //где должен быть расположен штрих)
            at.translate(-(diam/2 - r.width), 0);
            //поворачиваем штрих относительно центра циферблата
            at.rotate(Math.toRadians(30*i), diam/2 - r.width, 0);
            //устанавливаем текущее преобразование
            g2.setTransform(at);
            //рисуем штрих
            g2.fill(r);
            //восстанавливаем начальное преобразование
            at.setTransform(prev);
        }
    }
    
    /**
     * Возвращает изображение циферблата.
     *
     * @return BufferedImage рисунок, содержащий изображение циферблата
     */
    public BufferedImage getClockDial() {
        return dial;
    }
}
