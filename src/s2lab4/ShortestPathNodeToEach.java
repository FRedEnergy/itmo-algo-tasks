package s2lab4;

import java.io.*;
import java.util.StringTokenizer;

public class ShortestPathNodeToEach {

    static final long INF = Long.MAX_VALUE / 10 * 9;

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("path.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();
        int s = scan.nextInt() - 1;

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            long w = scan.nextLong();
            edges[i] = new Edge(origin, end, w);
        }

        nodes[s].distance = 0;

        for (int i = 0; i < n; i++) {
            for (Edge edge : edges) {
                if(edge.end.distance != -INF &&
                        edge.origin.distance != -INF &&
                        edge.origin.distance != INF &&
                        edge.origin.distance + edge.w < edge.end.distance)
                    edge.end.distance = Math.max(-INF, edge.origin.distance + edge.w);

                if(edge.origin.distance == -INF)
                    edge.end.distance = -INF;
            }
        }

        for (Node node : nodes)
            node.prevDistance = node.distance;

        for (int i = 0; i < n; i++) {

            for (Edge edge : edges) {
                if (edge.origin.distance != INF &&
                        edge.end.distance != -INF &&
                        edge.origin.distance + edge.w < edge.end.distance) {
                    edge.end.distance = -INF;
                }
            }
        }


        PrintWriter out = new PrintWriter("path.out");
        for (Node node : nodes) {
            if(node.distance == INF)
                out.println("*");
            else if(node.prevDistance != node.distance || node.distance == -INF)
                out.println("-");
            else
                out.println(node.distance);
        }
        out.close();
    }


    private static class Node {
        public final int id;
        public long prevDistance;
        public long distance = INF;


        public Node(int id) {
            this.id = id;
        }
    }

    private static class Edge {

        public Node origin;
        public Node end;
        public long w;

        public Edge(Node origin, Node end, long w) {
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
