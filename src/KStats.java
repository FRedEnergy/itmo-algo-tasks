import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class KStats {


    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("kth.in"));
        int n = scan.nextInt();
        int k = scan.nextInt();
        int A = scan.nextInt();
        int B = scan.nextInt();
        int C = scan.nextInt();

        int[] a = new int[n];
        a[0] = scan.nextInt();
        a[1] = scan.nextInt();

        for (int i = 2; i < n; i++)
            a[i] = A * a[i - 2] + B * a[i - 1] + C;

        System.out.println(Arrays.toString(a));
        orderStatistic(a, 0, a.length, k);
        int result = a[k];
        System.out.println(result + " at " + Arrays.toString(a));

        PrintWriter out = new PrintWriter(new FileOutputStream("kth.out"));
        out.append(String.valueOf(result));
        out.close();
    }

    public static void swap(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    static int partition(int[] a, int l, int r) {
        Random rand = new Random();
        swap(a, l + (r - l > 0 ? rand.nextInt(r - l) : 0), r - 1);
        int key = a[r - 1];
        int i = r - 1;
        for(int j = l; j < r; j++)
            if(a[j] < key)
                swap(a, j, i);

        return i;
    }

    static void orderStatistic(int[] array, int left, int right, int n) {
       while(true){
           int k = partition(array, left, right);
           if(n < k)
               right = k;
           else if(n > k)
               left = k + 1;
           else
               return;
       }
    }


    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
