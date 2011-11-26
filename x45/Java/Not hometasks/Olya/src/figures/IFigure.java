package figures;

import java.awt.*;

/**
 * @author Yuri Denison
 * @date 23.12.10
 */
public interface IFigure {
    /**
     * ����������� ������ �� �������� ������
     * @param x ������� �� ��� X
     * @param y ������� �� ��� Y
     */
    public void move(int x, int y);

    /**
     * ��������� ������
     * @param graphics ��������������� Graphics
     */
    public void draw(Graphics graphics);

    /**
     * ������ ��� ����� ������
     * @param color ����� ����
     */
    public void setColor(Color color);

    /**
     * �������� ���������� ����� ������ ������
     * @param x ���������� �� ��� X
     * @param y ���������� �� ��� Y
     * @return true ���� ����������, false ���� ���
     */
    public boolean contains(int x, int y);
}
