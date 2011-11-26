package figures;

import java.awt.*;

/**
 * @author Yuri Denison
 * @date 23.12.10
 */
public interface IFigure {
    /**
     * Перемещение фигуры на заданный вектор
     * @param x перенос по оси X
     * @param y перенос по оси Y
     */
    public void move(int x, int y);

    /**
     * Отрисовка фигуры
     * @param graphics соответствующая Graphics
     */
    public void draw(Graphics graphics);

    /**
     * Сеттер для цвета фигуры
     * @param color Новый цвет
     */
    public void setColor(Color color);

    /**
     * Проверка содержания точки внутри фигуры
     * @param x координата по оси X
     * @param y координата по оси Y
     * @return true если содержится, false если нет
     */
    public boolean contains(int x, int y);
}
