package lab2;

import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

public class Sort {

    static FastScanner in;
    static PrintWriter out;

    public static void main(String[] args) {
        setup("sort.in", "sort.out");

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = in.nextInt();

        qsort(a, 0, n - 1);

        for (int i = 0; i < a.length; i++)
            out.append(a[i] + " ");

        out.close();
    }

    static Random rand = new Random();
    static void qsort(int[] a, int l, int r){
        int k = rand.nextInt(r - l) + l;
        int key = a[k];
        int i = l;
        int j = r;
        while(i <= j){
            while(a[i] < key)
                i++;
            while(key < a[j])
                j--;

            if(i <= j){
                swap(a, i, j);
                i++;
                j--;
            }
        }

        if(l < j)
            qsort(a, l, j);
        if(i < r)
            qsort(a, i, r);
    }

    static void swap(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void setup(String inFile, String outFile) {
        try {
            in = new FastScanner(new File(inFile));
            out = new PrintWriter(new File(outFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        out.close();
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
