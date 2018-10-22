package lab2;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class KStats {

    public static int i = 0, j = 0;
    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("kth.in"));
        int n = scan.nextInt();
        int k = scan.nextInt() - 1;
        int A = scan.nextInt();
        int B = scan.nextInt();
        int C = scan.nextInt();

        int[] a = new int[n];
        a[0] = scan.nextInt();
        a[1] = scan.nextInt();

        for (int i = 2; i < n; i++)
            a[i] = A * a[i - 2] + B * a[i - 1] + C;

        int result = kElement(a, 0, a.length - 1, k);

        PrintWriter out = new PrintWriter(new FileOutputStream("kth.out"));
        out.append(String.valueOf(result));
        out.close();
    }

    private static void partition(int[] a, int l, int r) {
        int k = (l + r) / 2;
        int key = a[k];
        i = l;
        j = r;
        while(i <= j) {
            while (a[i] < key)
                i++;
            while (key < a[j])
                j--;

            if (i <= j) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                i++;
                j--;
            }
        }
    }

    private static int kElement(int[] a, int l, int r, int k) {
        if (r <= l)
            return a[l];

        partition(a, l, r);

        if (j + 1 <= k && k <= i - 1) {
            return a[j + 1];
        } else if (l <= k && k <= j) {
            return kElement(a, l, j, k);
        } else {
            return kElement(a, i, r, k);
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
