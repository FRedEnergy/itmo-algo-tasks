package s2lab4;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ShortestPathEachToEach {

    public static final int MAX = 1_000_000_000;
    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("pathsg.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();

        int[][] data = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(data[i], MAX);
            data[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            int end = scan.nextInt() - 1;
            int origin = scan.nextInt() - 1;
            int value = scan.nextInt();
            data[end][origin] = value;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(data[i][k] + data[k][j] < data[i][j])
                        data[i][j] = data[i][k] + data[k][j];
                }
            }
        }

        PrintWriter out = new PrintWriter("pathsg.out");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.print(data[i][j] + " ");
            }
            out.println();
        }
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