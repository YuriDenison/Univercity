/**
 * @author Yuri Denison
 * @date 16.11.11
 */
public class Task {
  public static final double X0 = 0;
  public static final double X1 = 0.4;
  public static final int N = 8;
  public static final int K1_FORMULA = 2;
  public static final int K2_FORMULA = 2;
  public static final int K3_FORMULA = 4;
  // http://goo.gl/0G47h == http://www.wolframalpha.com/input/?i=maximize+(1+%2F+(0.25+%2B+tg(0.05+%2B+x)))%2C+x+from+0+to+0.4
  public static final double MAX_FUNCTION = 3.33287;

  public static double function(double x) {
    return 1 / (0.25 + Math.tan(0.05 + x));
  }

  // real value: 0.837352
}
