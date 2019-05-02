package s2lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NaiveSearch {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("search1.in"));
        String p = scan.next();
        String t = scan.next();

        List<Integer> indices = new ArrayList<>();

        int n = t.length();
        int m = p.length();

        for(int i = 0; i <= n - m; i++){
            boolean isFound = true;
            for(int j = 0; j < m; j++) {
                if (t.charAt(i + j) != p.charAt(j)) {
                    isFound = false;
                    break;
                }
            }

            if(isFound)
                indices.add(i);
        }

        PrintWriter out = new PrintWriter("search1.out");
        out.println(indices.size());
        for (int index : indices)
            out.print((index + 1) + " ");
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
