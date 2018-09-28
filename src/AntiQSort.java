import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class AntiQSort {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("antiqs.in"));
        int n = scan.nextInt();
        scan.close();

        int[] a = new int[n];
        if(n > 0) a[0] = 1;
        if(n > 1) a[1] = 2;

        for(int i = 3; i <= a.length; i++){
            int key = (i - 1) / 2;
            int t = a[key];
            a[key] = i;
            a[i - 1] = t;
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("antiqs.out"));
        for (int i = 0; i < a.length; i++)
            out.append(a[i] + " ");
        out.close();

    }
}
