package s2lab4;

import java.io.*;
import java.util.*;

public class ShortestPath {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("pathmgep.in"));
        int n = scan.nextInt();
        int s = scan.nextInt() - 1;
        int f = scan.nextInt() - 1;

        int[] data = new int[n * n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                data[i * n + j] = scan.nextInt();
        long[] distance = new long[n];
        boolean[] isUsed = new boolean[n];
        Arrays.fill(distance, Long.MAX_VALUE);

        distance[s] = 0;
        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++)
                if(!isUsed[j] && (v == -1 || distance[j] < distance[v]))
                    v = j;

            isUsed[v] = true;

            if (distance[v] == Long.MAX_VALUE)
                break;


            for (int j = 0; j < n; j++) {
                if(j == v)
                    continue;

                if(isUsed[j])
                    continue;

                int edgeCost = data[v * n + j];
                if(edgeCost < 0)
                    continue;

                long dist = distance[v] + edgeCost;
                if (dist < distance[j])
                    distance[j] = dist;

            }
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("pathmgep.out"));
        long end = distance[f];
        if(end == Long.MAX_VALUE){
            out.println(-1);
        } else {
            out.println(end);
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