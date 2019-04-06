package s2lab4;

import java.io.*;
import java.util.*;

public class ShortestPathFirstToEach  {

    static final long MAX = Long.MAX_VALUE;

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("pathbgep.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            int w = scan.nextInt();
            origin.edges.add(new Edge(origin, end, w));
            end.edges.add(new Edge(end, origin, w));
        }


        nodes[0].distance = 0;
        TreeSet<Node> q = new TreeSet<>(Arrays.asList(nodes));
        for (int i = 0; i < nodes.length; i++) {
            Node v = q.pollFirst();

            if (v.distance == MAX)
                break;

            for (Edge edge : v.edges) {
                long dist = v.distance + edge.w;
                if (dist < edge.end.distance) {
                    q.remove(edge.end);
                    edge.end.distance = dist;
                    q.add(edge.end);
                }
            }
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("pathbgep.out"));
        for (int i = 0; i < n; i++)
            out.print(nodes[i].distance + " ");

        out.close();
    }

    private static class Node implements Comparable<Node>{
        public final int id;

        public long distance = MAX;

        public List<Edge> edges = new ArrayList<>();

        public Node(int id) {
            this.id = id;
        }

        @Override
        public int compareTo(Node o) {
            if(distance == o.distance)
                return Integer.compare(id, o.id);
            return Long.compare(distance, o.distance);
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
    }
}
