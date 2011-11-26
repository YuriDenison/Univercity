import java.awt.*;
import java.applet.Applet;

public class BouncingLines extends Applet implements Runnable {

    Thread runner = null;

    final static int WIDTH  = 600;
    final static int HEIGHT = 400;

    Image    image;
    Graphics graphics;

    // bouncing lines member variables

    int[] x1;
    int[] y1;
    int[] x2;
    int[] y2;

    int dx1 = 2 + (int)( 3 * Math.random() );
    int dy1 = 2 + (int)( 3 * Math.random() );
    int dx2 = 2 + (int)( 3 * Math.random() );
    int dy2 = 2 + (int)( 3 * Math.random() );

    static int first = 0;

    final static int LINES = 50;


    public void init() {

        // create arrays to hold the line coordinates

        x1 = new int[LINES];
        y1 = new int[LINES];
        x2 = new int[LINES];
        y2 = new int[LINES];

        // initialise the first line

        x1[0] = (int)( WIDTH  * Math.random() );
        y1[0] = (int)( HEIGHT * Math.random() );
        x2[0] = (int)( WIDTH  * Math.random() );
        y2[0] = (int)( HEIGHT * Math.random() );

        // initialise all the other lines

        for ( int i = 1; i < LINES; i++ ) {

            x1[i] = x1[0];
            y1[i] = y1[0];
            x2[i] = x2[0];
            y2[i] = y2[0];
        }
        image    = createImage( WIDTH, HEIGHT );
        graphics = image.getGraphics();
    }


    public void start() {

        // user visits the page, create a new thread

        if ( runner == null ) {

            runner = new Thread( this );
            runner.start();
        }
    }


    public void stop() {

        // user leaves the page, stop the thread

        if ( runner != null && runner.isAlive() )
            runner.stop();

        runner = null;
    }


    public void run() {

        while (runner != null) {

            repaint();

            try {

                Thread.sleep( 20 );

            } catch ( InterruptedException e ) {

                // do nothing
            }
        }
    }


    public void paint( Graphics g ) {

        update( g );
    }


    public void update( Graphics g ) {

        // clear the background to white

        graphics.setColor( Color.white);
        graphics.fillRect( 0, 0, WIDTH, HEIGHT );

        // draw the lines

        graphics.setColor( Color.red );

        int line = first;

        for ( int i = 0; i < LINES; i++ ) {

            graphics.drawLine( x1[line], y1[line],
                               x2[line], y2[line] );
            line++;
            if ( line == LINES ) line = 0;
        }

        line = first;

        first--;

        if ( first < 0 ) first = LINES - 1;

        x1[first] = x1[line];
        y1[first] = y1[line];
        x2[first] = x2[line];
        y2[first] = y2[line];

        // move the "first" line

        if (x1[first] + dx2 < WIDTH)
            x1[first] += dx1;
        else
            dx1 = -(2 + (int)( 3 * Math.random() ));

        if (x1[first] + dx1 >= 0)
            x1[first] += dx1;
        else
            dx1 = 2 + (int)( 3 * Math.random() );

        if (y1[first] + dy1 < HEIGHT)
            y1[first] += dy1;
        else
            dy1 = -(2 + (int)( 3 * Math.random() ));

        if (y1[first] + dy1 >= 0)
            y1[first] += dy1;
        else
            dy1 = 2 + (int)( 3 * Math.random() );

        if (x2[first] + dx2 < WIDTH)
            x2[first] += dx2;
        else
            dx2 = -(2 + (int)( 3 * Math.random() ));

        if (x2[first] + dx2 >= 0)
            x2[first] += dx2;
        else
            dx2 = 2 + (int)( 3 * Math.random() );

        if (y2[first] + dy2 < HEIGHT)
            y2[first] += dy2;
        else
            dy2 = -(2 + (int)( 3 * Math.random() ));

        if (y2[first] + dy2 >= 0)
            y2[first] += dy2;
        else
            dy2 = 2 + (int)( 3 * Math.random() );

        // copy buffer to screen

        g.drawImage( image, 0, 0, this );
    }
}