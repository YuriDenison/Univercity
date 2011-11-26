/*
 * CircleClock.java
 *
 * Created on 3 Октябрь 2004 г., 19:36
 */
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.Calendar;
/**
 *
 * @author  Стаценко Владимир
 */
public class CircleClock extends java.applet.Applet implements Runnable {
    
    private Needle hNeedle, mNeedle, sNeedle;
    private ClockDial dial;
    private int w, h;
    private BufferedImage bi;
    private Graphics2D big;
    private boolean stop = false;
    private Thread timer = null;
    
    private Color dialColor = Color.WHITE;
    private Color hairLineColor = Color.GREEN;
    private Color hoursColor = Color.BLACK;
    private Color minutesColor = Color.BLUE;
    private Color secondsColor = Color.RED;
    
    /** Этот метод будет вызван после загрузки апплета */
    public void init() {
        try {
            //Создаем объекты и устанавливаем начальные значения.
            Dimension dim = getSize();
            w = dim.width;
            h = dim.height;
            
            //устанавливаем цвета стрелок и циферблата
            setColors();

            //Создаем стрелки
            int radius = 0;
            if(w < h)
                radius = w/2;
            else
                radius = h/2;
            mNeedle = new Needle(radius, radius/5);     //минутная стрелка
            hNeedle = new Needle(2d/3d*radius, radius/4); //часовая стрелка
            sNeedle = new Needle(radius, radius/10);    //секундная стрелка
                
            //создаем циферблат
            dial = new ClockDial(radius*2, dialColor, hairLineColor);

            //Создаем изображение и получаем его объект Graphics2D.
            //Рисовать будем только на нем, а экран выводим уже
            //готовый рисунок (т.н. двойная буферезация).
            bi = (BufferedImage)createImage(w, h);
            big = bi.createGraphics();
            big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

            //Создаем поток, который будет периодически вызывать метод update.
            timer = new Thread(this);
            timer.start();
        }
        catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }
    
    /** Этот метод выполняет перерисовку окна апплета */
    public void update(Graphics g) {
        
        try {
            //Получаем указатель на объект Graphics2D
            Graphics2D g2 = (Graphics2D)g;

            
            grawClock();

            //Рисуем готовое изображение на экране
            g2.drawImage(bi, 0, 0, this);
            
            big.setTransform(new AffineTransform());
        }
        catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }

    private void grawClock() {
        //Узнаем текущее время
        Calendar curTime = Calendar.getInstance();
        curTime.setTimeInMillis(System.currentTimeMillis());
        double hour = curTime.get(Calendar.HOUR_OF_DAY);
        double min = curTime.get(Calendar.MINUTE);
        double sec = curTime.get(Calendar.SECOND);

        //Очищаем рисунок
        big.setBackground(Color.WHITE);
        big.clearRect(0, 0, w, h);
        
        //Рисуем циферблат
        Point luCorner = new Point(0, 0);
        if(w > h)
        {
            luCorner.x = (w - h)/2;
            luCorner.y = 0;
        }
        else
        {
            luCorner.x = 0;
            luCorner.y = (h - w)/2;
        }
        big.drawImage(dial.getClockDial(), null, luCorner.x, luCorner.y);

        //Рисуем стрелки

        big.setColor(hoursColor);
        AffineTransform hat = new AffineTransform();
        hat.translate(w/2 - hNeedle.getRotationCenter().x,
                        h/2 - hNeedle.getRotationCenter().y);
        /*Рассчитываем угол поворота часовой стелки. Каждый час на
         12-ти часовом циферблате соответствует 30-ти градусам, а 1
         минута - 0.5 градуса. Затем результат переводим в радианы. */
        if(hour >= 12)
            hour -= 12;
        double theta = (-90 + (30*hour + 0.5*min))*Math.PI/180;
        hat.rotate(theta, hNeedle.getRotationCenter().x,
                            hNeedle.getRotationCenter().y);

        big.setTransform(hat);
        big.fill(hNeedle.getNeedle());
        
        big.setColor(minutesColor);
        AffineTransform mat = new AffineTransform();
        mat.translate(w/2 - mNeedle.getRotationCenter().x,
                        h/2 - mNeedle.getRotationCenter().y);
        /*Рассчитываем угол поворота минутной стелки. Каждая минута на
         12-ти часовом циферблате соответствует 6-ти градусам, а 1
         секунда - 0.1 градуса. Затем результат переводим в радианы. */
        theta = (-90 + (6*min + 0.1*sec))*Math.PI/180;
        mat.rotate(theta, mNeedle.getRotationCenter().x, 
                            mNeedle.getRotationCenter().y);

        big.setTransform(mat);
        big.fill(mNeedle.getNeedle());
        
        big.setColor(secondsColor);
        AffineTransform sat = new AffineTransform();
        sat.translate(w/2 - sNeedle.getRotationCenter().x,
                        h/2 - sNeedle.getRotationCenter().y);
        /*Рассчитываем угол поворота сеундной стелки. Каждая секунда на
         12-ти часовом циферблате соответствует 6-ти градусам.
         Затем результат переводим в радианы. */
        theta = (-90 + (6*sec))*Math.PI/180;
        sat.rotate(theta, sNeedle.getRotationCenter().x,
                    sNeedle.getRotationCenter().y);

        big.setTransform(sat);
        big.fill(sNeedle.getNeedle());
    }
    
    //Этот метод читает параметры апплета, и устанавливает
    //цвета стрелок и циферблата.
    private void setColors() {
        try {
            /* Вообще-то, для задания цвета нужно число типа int, т.е. 4 байта,
               но int - это число со знаком (от "-2^31" до "2:31 - 1"), а нам
               нужно число с диапазоном от "0" до "2^32", т.е. те же 4 байта,
               но без знака. Для этого мы сначала создаем число типа Long, а
               затем, с помощью метода intValue() получаем тип int.
             */
            Long dc = new Long(Long.parseLong(getParameter("dialColor"), 16));
            Long hlc = new Long(Long.parseLong(getParameter("hairLineColor"), 16));
            Long hnc = new Long(Long.parseLong(getParameter("hoursNeedleColor"), 16));
            Long mnc = new Long(Long.parseLong(getParameter("minutesNeedleColor"), 16));
            Long snc = new Long(Long.parseLong(getParameter("secondsNeedleColor"), 16));
            
            dialColor = new Color(dc.intValue(), true);
            hairLineColor = new Color(hlc.intValue(), true);
            hoursColor = new Color(hnc.intValue(), true);
            minutesColor = new Color(mnc.intValue(), true);
            secondsColor = new Color(snc.intValue(), true);
        }
        catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }
    
    //Этот метод выполняется в отдельном потоке (timer).
    //Он вызывает перерисовку окна апплета каждую секунду.
    public void run() {
        while(!stop) {
            try {
                repaint();
                Thread.currentThread().sleep(1000);
            }
            catch(Exception err) {}
        }
    }
    
    //Этот метод выполняется если пользователь покинул страницу
    //с апплетом. Он останавливает поток (timer) и, соответственно,
    //перерисовку окна апплета.
    public void stop() {
        super.stop();
        stop = true;
    }
    
    //Этот метод выполняется когда пользователь попадает на страницу
    //с апплетом. Он запускает парралельный поток (timer).
    public void start() {
        super.start();
        stop = false;
        if(timer == null) {
            timer = new Thread(this);
            timer.start();
        }
        
    }
    
    //Этот метод выполняется при закрытии страницы с апплетом.
    public void destroy() {
        super.destroy();
        stop = true;
        //Ждем пока парралельный поток (timer) завершит работу.
        Thread.currentThread().yield();
    }
    
    public String[][] getParameterInfo() {
        String[][] retValue = {
            {"dialColor", "hex", "alfa, red, green, blue (4 bytes, from 00 to FF)"},
            {"hairLineColor", "hex", "alfa, red, green, blue (4 bytes, from 00 to FF)"},
            {"hoursNeedleColor", "hex", "alfa, red, green, blue (4 bytes, from 00 to FF)"},
            {"minutesNeedleColor", "hex", "alfa, red, green, blue (4 bytes, from 00 to FF)"},
            {"secondsNeedleColor", "hex", "alfa, red, green, blue (4 bytes, from 00 to FF)"}
        };
        return retValue;
    }
    
    public String getAppletInfo() {
        String retValue;
        
        retValue = "Circle Clock";
        return retValue;
    }
    
}
