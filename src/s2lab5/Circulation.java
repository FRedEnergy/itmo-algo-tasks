package s2lab5;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Circulation {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("circulation.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            int min = scan.nextInt();
            int cap = scan.nextInt();
            Edge original = addEdge(origin, end, min, cap, false);
            edges[i] = original;
        }

        Node start = new Node(nodes.length);
        Node end = new Node(nodes.length + 1);

        List<Node> nextGraph = new ArrayList<>(Arrays.asList(nodes));
        nextGraph.add(start);
        nextGraph.add(end);

        for (Edge e : edges) {
            e.g = addEdge(start, e.end, 0, e.min, true);
            e.h = addEdge(e.origin, e.end, 0, e.max - e.min, true);
            e.k = addEdge(e.origin, end, 0, e.min, true);
        }

        nodes = nextGraph.toArray(new Node[0]);

        maxFlow(nodes, start, end);

        boolean hasCirculation = true;
        for (Edge e : start.adjacent) {
            if(e.flow < e.max)
                hasCirculation = false;
        }

        PrintWriter out = new PrintWriter("circulation.out");
        out.println(hasCirculation ? "YES" : "NO");

        if(hasCirculation)
            for (Edge edge : edges)
                out.println(edge.h.flow + edge.min);
        out.close();

    }

    private static Edge addEdge(Node origin, Node end, long min, long cap, boolean addToVertices) {
        Edge original = new Edge(origin, end, min, cap);
        Edge reverse = new Edge(end, origin, 0,0);
        original.reverse = reverse;
        reverse.reverse = original;
        if(addToVertices) {
            origin.adjacent.add(original);
            end.adjacent.add(reverse);
        }
        return original;
    }

    private static long maxFlow(Node[] nodes, Node start, Node end){
        long maxFlow = 0;
        while (dinicCheckPathExists(nodes, start, end)){
            int[] p = new int[nodes.length];
            while (true){
                long flow = dinicTraverse(p, start, end, Long.MAX_VALUE);
                maxFlow += flow;
                if(flow == 0)
                    break;
            }
        }
        return maxFlow;
    }

    private static boolean dinicCheckPathExists(Node[] nodes, Node start, Node end){
        for (Node node : nodes)
            node.d = -1;
        start.d = 0;

        int head = 0;
        Node[] q = new Node[nodes.length];
        q[head++] = start;

        for (int i = 0; i < head; i++) {
            Node v = q[i];
            for (Edge edge : v.adjacent) {
                if(edge.end.d < 0 && edge.flow < edge.max){
                    edge.end.d = v.d + 1;
                    q[head++] = edge.end;
                }
            }
        }

        return end.d >= 0;
    }

    private static long dinicTraverse(int[] p, Node start, Node sink, long minFlow){
        if(start == sink || minFlow == 0)
            return minFlow;

        while (p[start.id] < start.adjacent.size()){
            Edge edge = start.adjacent.get(p[start.id]);
            if(edge.end.d == start.d + 1 && edge.flow < edge.max){
                long flow = dinicTraverse(p, edge.end, sink, Math.min(minFlow, edge.max - edge.flow));
                if(flow > 0){
                    edge.flow += flow;
                    edge.reverse.flow -= flow;
                    return flow;
                }
            }
            p[start.id]++;
        }
        return 0;
    }

    public static class Node {

        public final int id;
        public int d;
        public boolean isVisited = false;

        public List<Edge> adjacent = new ArrayList<>();

        public Node(int id) {
            this.id = id;
        }
    }

    public static class Edge {

        public Node origin, end;
        public long min, max;
        public long flow = 0;
        public Edge reverse = null;

        public Edge g, h, k;

        public Edge(Node origin, Node end, long min, long cap) {
            this.origin = origin;
            this.end = end;
            this.min = min;
            this.max = cap;
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
