package s2lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FastSearch{

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("search2.in"));
        String p = scan.next();
        String t = scan.next();

        List<Integer> indices = new ArrayList<>();

        int n = t.length();
        int m = p.length();

        int[] prefix = prefix(p + "#" + t);

        for(int i = 0; i < prefix.length; i++)
            if(prefix[i] == m)
                indices.add(i - 2 * m);

        PrintWriter out = new PrintWriter("search2.out");
        out.println(indices.size());
        for (int index : indices)
            out.print((index + 1) + " ");
        out.close();
    }

    private static int[] prefix(String word){
        int[] p = new int[word.length()];
        for(int i = 1; i < word.length(); i++){
            int k = p[i - 1];
            while (k > 0 && word.charAt(i) != word.charAt(k))
                k = p[k - 1];
            if(word.charAt(i) == word.charAt(k))
                k++;
            p[i] = k;
        }
        return p;
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
