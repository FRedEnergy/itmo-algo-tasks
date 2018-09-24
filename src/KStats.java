import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class KStats  {


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

        for(int i = 2; i < n; i++)
            a[i] = A * a[i - 2] + B * a[i - 1] + C;

        int left = 0;
        int right = a.length;
        int result = 0;
        while (true){
            int key = partition(a, left, right -1 );
            if(key == k) {
                result = a[key];
                break;
            } else if(k < key){
                right = key;
            } else {
                k -= key + 1;
                left = key + 1;
            }
        }


        PrintWriter out = new PrintWriter(new FileOutputStream("kth.out"));
        out.append(String.valueOf(result));
        out.close();
    }

    static int partition(int[] a, int l, int r){
        int v = a[(l + r) / 2];
        int i = l;
        int j = r;
        while (i <= j){
            while(a[i] < v)
                i++;
            while (a[j] > v)
                j--;
            if(i >= j)
                break;

            int t = a[i];
            a[i] = a[j];
            a[j] = t;
            i++;
            j--;
        }
        return j;
    }

//    static Random rand = new Random();
//    static int partition(int[] a, int l, int r) {
//        int k = (l + r) / 2;///(r - l > 0 ? rand.nextInt(r - l) : 0) + l;
//        int key = a[k];
//        int i = l;
//        int j = r;
//        while (i <= j) {
//            while (a[i] < key)
//                i++;
//            while (key < a[j])
//                j--;
//
//            if (i <= j) {
//                int t = a[i];
//                a[i] = a[j];
//                a[j] = t;
//                i++;
//                j--;
//            }
//        }
//        return k;
//    }


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
