package s2lab1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TaskC {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("input.txt"));

        int n = scan.nextInt();
        int m = scan.nextInt();

        int[] mat = new int[n * n];

        boolean isInvalid = false;
        for (int i = 0; i < m; i++) {
            int from = scan.nextInt() - 1;
            int to = scan.nextInt() - 1;

            mat[to + from * n]++;
            mat[from + to * n]++;

            if(from != to && (mat[to + from * n] > 1 || mat[from + to * n] > 1)){
                isInvalid = true;
                break;
            } else if(from == to && mat[to + from * n] > 2){
                isInvalid = true;
                break;
            }
        }

        PrintWriter out = new PrintWriter("output.txt");
        out.println(isInvalid ? "YES" : "NO");
        out.close();

    }
}
