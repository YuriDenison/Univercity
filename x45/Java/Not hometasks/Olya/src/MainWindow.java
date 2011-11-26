import figures.*;
import figures.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Yuri Denison
 * @date 23.12.10
 */
public class MainWindow extends JFrame {
    private IFigure figure = null;

    private JPanel buttonPanel;
    private JPanel figurePanel;
    private static final int NUMBER_OF_FIGURES = 4;
    private static final int DEFAULT_NUMBER_OF_POLYGON_SIDES = 5;

    public MainWindow() {
        super("Figure show");

        initButtonPanel();
        initFigurePanel();

        this.setLocationByPlatform(true);
        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(figurePanel, BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Инициализация панели, на которой рисуются фигуры
     */
    private void initFigurePanel() {
        figurePanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (figure != null) {
                    figure.draw(g);
                }
            }
        };
        initMoveAdapter();
    }

    /**
     * Инициализация обработчика передвижений фигуры
     */
    private void initMoveAdapter() {
        MouseAdapter adapter = new MouseAdapter() {
            private int oldX;
            private int oldY;

            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int difX = e.getX() - oldX;
                int difY = e.getY() - oldY;

                if (figure != null && figure.contains(e.getX(), e.getY())) {
                    figure.move(difX, difY);
                    oldX += difX;
                    oldY += difY;
                    figurePanel.repaint();
                }
            }
        };
        figurePanel.addMouseListener(adapter);
        figurePanel.addMouseMotionListener(adapter);
    }

    /**
     * Инициализация панели кнопок
     */
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, NUMBER_OF_FIGURES));

        buttonPanel.add(createButton("Прямоугольник", new Rectangle()));
        buttonPanel.add(createButton("Треугольник", new Triangle()));
        buttonPanel.add(createButton("Эллипс", new Ellipse()));
        buttonPanel.add(createButton("Многоугольник", new MyPolygon()));
    }

    /**
     * Создание кнопки для конкретной фигуры
     * @param name  название фигуры
     * @param fig   объект фигуры
     * @return     объект кнопки для размещения на панели
     */
    private JButton createButton(String name, final IFigure fig) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                figure = fig;
                if (fig instanceof MyPolygon) {
                    ((MyPolygon) figure).setNumberOfSides(showNumberOfSidesDialog());
                }
                figure.setColor(showColorDialog());
                showFigure();
            }
        });
        return button;
    }

    /**
     * Отображение фигуры на панели
     */
    private void showFigure() {
        figurePanel.paint(figurePanel.getGraphics());
    }

    /**
     * Отображение диалога выбора цвета
     * @return выбранный пользователем цвет
     *              синий - если пользователь ничего не выбрал
     *              черный - если пользователь вышел из диалога
     */
    private Color showColorDialog() {
        this.setMinimumSize(new Dimension(500, 500));
        try {
            return JColorChooser.showDialog(this, "Choose a color", Color.blue);
        } catch (NullPointerException e) {
            return Color.black;
        }
    }

    /**
     * Отображения диалога выбора количества сторон правильного многоугольника
     * @return выбранное число сторон (5, если пользователь ввел неверную информацию)
     */
    private int showNumberOfSidesDialog() {
        try {
            return Integer.parseInt(JOptionPane.showInputDialog("Input number of sides: "));
        } catch (NumberFormatException e) {
            return DEFAULT_NUMBER_OF_POLYGON_SIDES;
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
