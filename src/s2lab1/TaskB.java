package s2lab1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class TaskB {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("input.txt"));
        int n = scan.nextInt();
        int[] mat = new int[n * n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i * n + j] = scan.nextInt();
            }
        }

        PrintWriter out = new PrintWriter("output.txt");
        for (int i = 0; i < n; i++) {
            int s = 0;
            for(int j = 0; j < n; j++)
                s += mat[i * n + j];

            int k = 0;
            for(int j = 0; j < n; j++)
                k += mat[j * n + i];

            if(s != k || mat[i * n + i] % 2 != 0){
                out.println("NO");
                out.close();
                return;
            }

        }

        out.println("YES");
        out.close();

    }
}
