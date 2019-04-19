package s2lab5;

import java.io.*;
import java.util.*;

public class Decomposition {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("decomposition.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            int cap = scan.nextInt();
            Edge original = new Edge(i, origin, end, cap);
            Edge reverse = new Edge(i, end, origin, 0);
            original.reverse = reverse;
            reverse.reverse = original;
            origin.adjacent.add(original);
            end.adjacent.add(reverse);
        }
        Node start = nodes[0];
        Node end = nodes[nodes.length - 1];

        maxFlow(nodes, start, end);

        List<FlowDecomposition> decompositions = new ArrayList<>();
        FlowDecomposition flowPath = decomposeFlow(nodes, start);

        while (flowPath != null){
            decompositions.add(flowPath);
            flowPath = decomposeFlow(nodes, start);
        }

        PrintWriter out = new PrintWriter("decomposition.out");
        out.println(decompositions.size());
        for (FlowDecomposition pathWithFlow : decompositions) {
            long flow = pathWithFlow.minFlow;
            int size = pathWithFlow.decomposition.size();

            out.print(flow + " " + size + " ");
            for (Edge edge : pathWithFlow.decomposition) {
                out.print(edge.id + 1);
                out.print(" ");
            }
            out.println();
        }
        out.close();
    }

    private static void maxFlow(Node[] nodes, Node start, Node end){
        while (dinicCheckPathExists(nodes, start, end)){
            int[] p = new int[nodes.length];
            while (dinicTraverse(p, start, end, Long.MAX_VALUE) != 0);
        }
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
                if(edge.end.d < 0 && edge.flow < edge.cap){
                    edge.end.d = v.d + 1;
                    q[head++] = edge.end;
                }
            }
        }

        return end.d >= 0;
    }

    private static long dinicTraverse(int[] p, Node start, Node sink, long minFlow){
        if(start == sink)
            return minFlow;

        while (p[start.id] < start.adjacent.size()){
            Edge edge = start.adjacent.get(p[start.id]);
            if(edge.end.d == start.d + 1 && edge.flow < edge.cap){
                long flow = dinicTraverse(p, edge.end, sink, Math.min(minFlow, edge.cap - edge.flow));
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

    private static FlowDecomposition decomposeFlow(Node[] nodes, Node start){
        for (Node n : nodes)
            n.isVisited = false;

        LinkedList<Edge> q = new LinkedList<>();
        Node v = start;
        while (!v.isVisited){
            if(v == nodes[nodes.length - 1])
                break;

            Edge edge = null;
            for (Edge e : v.adjacent) {
                if(e.flow <= 0)
                    continue;

                edge = e;
                break;
            }

            if(edge == null)
                return null;

            q.offer(edge);
            v.isVisited = true;
            v = edge.end;
        }

        if(v.isVisited) {
            while (true){
                Edge u = q.poll();
                if(u == null || u.origin == v)
                    break;
            }
        }

        long minFlow = Long.MAX_VALUE;
        for (Edge edge : q)
            minFlow = Math.min(edge.flow, minFlow);

        for (Edge edge : q)
            edge.flow = edge.flow - minFlow;

        return new FlowDecomposition(minFlow, q);
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

        public final int id;
        public Node origin, end;
        public long cap;
        public long flow = 0;
        public Edge reverse = null;

        public Edge(int id, Node origin, Node end, int cap) {
            this.origin = origin;
            this.end = end;
            this.cap = cap;
            this.id = id;
        }
    }

    private static class FlowDecomposition{
        public final long minFlow;
        public final List<Edge> decomposition;

        public FlowDecomposition(long minFlow, List<Edge> decomposition) {
            this.minFlow = minFlow;
            this.decomposition = decomposition;
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
