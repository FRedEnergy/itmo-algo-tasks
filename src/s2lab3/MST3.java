package s2lab3;

import java.io.*;
import java.util.*;

public class MST3 {

    static Random random = new Random();

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("./spantree3.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();
        int[] p = new int[n];
        for (int i = 0; i < n; i++)
            p[i] = i;

        Node[] nodes = new Node[n];
        Edge[] edges = new Edge[m];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            edges[i] = new Edge(origin, end, scan.nextInt());
        }

        long sum = 0;

        Arrays.sort(edges);
        for (int i = 0; i < m; i++) {
            Edge edge = edges[i];

            if(getSubset(p, edge.origin.id) == getSubset(p, edge.end.id))
                continue;

            sum += edge.w;

            uniteSubset(p, edge.origin.id, edge.end.id);
        }

        PrintWriter out = new PrintWriter("./spantree3.out");
        out.println(sum);
        out.close();
    }

    public static int getSubset(int[] parent, int v){
        if(v == parent[v])
            return v;
        return parent[v] = getSubset(parent, parent[v]);
    }

    public static void uniteSubset(int[] parent, int a, int b){
        a = getSubset(parent, a);
        b = getSubset(parent, b);

        if(a == b)
            return;

        if(random.nextBoolean()){
            int t = a;
            a = b;
            b = t;
        }

        parent[a] = b;
    }

    private static class Node {

        public final int id;
        public int tree;

        public Node(int id) {
            this.id = id;
            this.tree = id;
        }
    }

    private static class Edge implements Comparable<Edge>{

        public Node origin, end;
        public long w;

        public Edge(Node origin, Node end, long w) {
            this.origin = origin;
            this.end = end;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return Float.compare(w, o.w);
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
