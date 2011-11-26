/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 19.02.2010
 * Time: 16:52:09
 * To change this template use File | Settings | File Templates.
 */
public class Matrix {
    protected int lengh;
    protected int height;
    protected int[][] value;

    public Matrix(int lengh, int height){
        this.lengh = lengh;
        this.height = height;
        value = new int[lengh][height];
    }

    public Matrix() {
    }

    public void swapColumn(int a, int b){
        for(int i = 0; i < height; i++){
            value[i][a] = value[i][a] + value[i][b];
            value[i][b] = value[i][a] - value[i][b];
            value[i][a] = value[i][a] - value[i][b];
        }
    }

    public void printMatrix(){
        for (int i = 0; i < lengh; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(value[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void set(int i, int j, int val){
        value[i][j] = val;
    }

    public int getValue(int i, int j){
        return value[i][j];
    }
    public int getLength(){
        return lengh;
    }
    public int getHeight(){
        return height;
    }

}
