package s2lab1;

import java.io.*;
import java.util.Scanner;

public class TaskA {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("input.txt"));

        int n = scan.nextInt();
        int m = scan.nextInt();

        int[] mat = new int[n * n];

        for (int i = 0; i < m; i++) {
            int from = scan.nextInt() - 1;
            int to = scan.nextInt() - 1;

            mat[to + from * n] = from == to ? 2 : 1;
        }

        PrintWriter out = new PrintWriter("output.txt");
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                out.print(mat[j + i * n] + " ");
            }
            out.println();
        }
        out.close();

    }
}
