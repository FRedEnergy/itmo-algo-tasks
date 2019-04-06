package s2lab4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NegativeCycle {

    static final long INF = Long.MAX_VALUE / 10 * 9;

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("negcycle.in"));
        int n = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Node origin = nodes[i];
            for (int j = 0; j < n; j++) {
                Node end = nodes[j];
                int w = scan.nextInt();
                if(w == 1E9)
                    continue;

                edges.add(new Edge(origin, end, w));
            }
        }

        Node cycleEnd = null;

        for (int i = 0; i < n; i++) {
            cycleEnd = null;
            for (Edge edge : edges) {
                if(edge.origin.distance >= INF)
                    continue;

                if(edge.origin.distance + edge.w < edge.end.distance){
                    edge.end.distance = Math.max(-INF, edge.origin.distance + edge.w);
                    edge.end.parent = edge.origin;
                    cycleEnd = edge.end;
                }
            }
        }


        PrintWriter out = new PrintWriter("negcycle.out");
        if(cycleEnd == null){
            out.println("NO");
        } else {
            out.println("YES");

            for (int i = 0; i < n; i++)
                cycleEnd = cycleEnd.parent;

            List<Node> cycle = new ArrayList<>();
            Node v = cycleEnd;
            while (true){
                cycle.add(v);
                if(v == cycleEnd && cycle.size() > 1)
                    break;
                v = v.parent;
            }

            out.println(cycle.size());
            for (int i = cycle.size() - 1; i >= 0; i--) {
                out.print(cycle.get(i).id + 1 + " ");
            }
        }

        out.close();
    }

    private static class Node{

        public final int id;

        public Node parent = null;
        public long distance = 0;

        public Node(int id) {
            this.id = id;
        }
    }

    private static class Edge{
        public Node origin;
        public Node end;
        public int w;

        public Edge(Node origin, Node end, int w) {
            this.origin = origin;
            this.end = end;
            this.w = w;
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

        long nextLong(){ return Long.parseLong(next()); }
    }
}
