import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Yuri Denison
 * @since 12.02.2010 23:20:03
 */
public class Main {
    private static long[] mas;

    public static long factorial(int a) {
        if (a == 1) {
            return 1;
        }
        return a * factorial(a - 1);
    }

    public static long fibbonachi(int n) {
        if (n == 0 || n == 1) {
            mas[n] = 1;
            return 1;
        } else {
            if (mas[n - 1] == 0) {
                if (mas[n - 2] == 0) {
                    mas[n] = fibbonachi(n - 2) + fibbonachi(n - 1);
                    return mas[n];
                } else {
                    mas[n] = mas[n - 1] + fibbonachi(n - 2);
                    return mas[n];
                }
            } else {
                if (mas[n - 2] == 0) {
                    mas[n] = fibbonachi(n - 1) + mas[n - 2];
                    return mas[n];
                } else {
                    mas[n] = mas[n - 1] + mas[n - 2];
                    return mas[n];
                }
            }
        }
    }

    public static void BubbleSort(long[] mas) {
        boolean t = true;
        while (t) {
            t = false;
            for (int j = 0; j < mas.length - 1; j++) {
                if (mas[j] > mas[j + 1]) {
                    mas[j] = mas[j] + mas[j + 1];
                    mas[j + 1] = mas[j] - mas[j + 1];
                    mas[j] = mas[j] - mas[j + 1];
                    t = true;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        System.out.println("Input N for counting Fib number: ");
        int N = Integer.parseInt(br.readLine());
        mas = new long[N + 1];
        System.out.println(N + " Fib number: " + fibbonachi(N));
        System.out.println("");

        System.out.println("Input N for counting factorial: ");
        N = Integer.parseInt(br.readLine());
        System.out.println("factorial(" + N + ") = " + factorial(N));
        System.out.println("");

        System.out.println("Input array for sorting: ");
        String[] str = br.readLine().split(" ");
        mas = new long[str.length + 1];
        for (int i = 0; i < str.length; i++) {
            mas[i] = Integer.parseInt(str[i]);
        }
        BubbleSort(mas);
        for (int i = 0; i < str.length; i++) {
            System.out.print(mas[i] + " ");
        }
    }
}
