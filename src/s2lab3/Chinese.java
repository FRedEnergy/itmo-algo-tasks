package s2lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Chinese {

    public static final int STATE_NOT_VISITED = 0;
    public static final int STATE_VISITING = 1;
    public static final int STATE_VISITED = 2;


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("chinese.in"));

        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] nodes = new Node[n];
        Edge[] edges = new Edge[m];

        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            int w = scan.nextInt();
            edges[i] = new Edge(origin, end, w);
        }

        PrintWriter out = new PrintWriter("chinese.out");
        if(!validateWalkable(nodes[0], nodes, edges)){
            out.println("NO");
            out.close();
            return;
        }

        out.println("YES");
        out.println(findMST(nodes[0], nodes, edges));
        out.close();
    }

    private static void buildNewAdjacentList(Node[] nodes, Edge[] edges, boolean isReverse) {
        for (Node node : nodes) {
            node.state = STATE_NOT_VISITED;
            if(!node.edges.isEmpty())
                node.edges.clear();
        }

        for (Edge edge : edges){
            if(isReverse)
                edge.end.edges.add(new Edge(edge.end, edge.origin, edge.w));
            else
                edge.origin.edges.add(new Edge(edge.origin, edge.end, edge.w));
        }
    }

    private static boolean validateWalkable(Node root, Node[] nodes, Edge[] edges){
        buildNewAdjacentList(nodes, edges, false);
        visitedCount = 0;
        dfs(root);

        return visitedCount == nodes.length;
    }

    private static int visitedCount = 0;
    private static void dfs(Node vert){
        visitedCount++;

        vert.state = STATE_VISITING;
        for (Edge edge : vert.edges)
            if(edge.end.state == STATE_NOT_VISITED)
                dfs(edge.end);
        vert.state = STATE_VISITED;
    }

    private static long findMST(Node root, Node[] nodes, Edge[] edges){
        long result = 0;

        for (Node node : nodes)
            node.minEdgeWeight = Integer.MAX_VALUE;

        for (Edge edge : edges)
            if(edge.w < edge.end.minEdgeWeight)
                edge.end.minEdgeWeight = edge.w;

        root.minEdgeWeight = 0;

        for (Node n : nodes)
            result += n.minEdgeWeight;

        List<Edge> zeroEdges = new ArrayList<>();
        for (Edge edge : edges)
            if(edge.w == edge.end.minEdgeWeight)
                zeroEdges.add(new Edge(edge.origin, edge.end, 0));

        Edge[] zeroEdgesArr = zeroEdges.toArray(new Edge[0]);
        if(validateWalkable(root, nodes, zeroEdgesArr))
            return result;

        List<Node> comps = new ArrayList<>();
        int compRoot = doCondensation(nodes, zeroEdgesArr, root, comps);
        List<Edge> compEdges = new ArrayList<>();
        for (Edge edge : edges) {
            Node origin = edge.origin;
            Node end = edge.end;
            if(origin.comp != end.comp)
                compEdges.add(new Edge(comps.get(origin.comp), comps.get(end.comp), edge.w - end.minEdgeWeight));
        }

        result += findMST(comps.get(compRoot), comps.toArray(new Node[0]), compEdges.toArray(new Edge[0]));
        return result;
    }

    private static int doCondensation(Node[] nodes, Edge[] edges, Node root, List<Node> comps){
        Deque<Node> ordered = new LinkedList<>();
        doTopsort(nodes, edges, root, ordered);
        buildNewAdjacentList(nodes, edges, true);

        int compId = 0;
        while (!ordered.isEmpty()){
            Node top = ordered.pop();
            if(top.state == STATE_NOT_VISITED){
                comps.add(new Node(compId));
                top.comp = compId++;
                condensationStep(top);
            }
        }

        return root.comp;
    }

    private static void condensationStep(Node v){
        v.state = STATE_VISITING;
        for (Edge edge : v.edges) {
            if(edge.end.state == STATE_NOT_VISITED) {
                edge.end.comp = v.comp;
                condensationStep(edge.end);
            }
        }
        v.state = STATE_VISITED;
    }

    private static void doTopsort(Node[] nodes, Edge[] edges, Node root, Deque<Node> s){
        buildNewAdjacentList(nodes, edges, false);

        topSortStep(root, s);
        for (Node node : nodes)
            if(node.state == STATE_NOT_VISITED)
                topSortStep(node, s);
    }

    private static void topSortStep(Node v, Deque<Node> s){
        v.state = STATE_VISITING;
        for (Edge adj : v.edges)
            if(adj.end.state == STATE_NOT_VISITED)
                topSortStep(adj.end, s);
        v.state = STATE_VISITED;
        s.push(v);
    }


    public static class Node {

        public final int id;

        public int state = STATE_NOT_VISITED;
        public int comp;
        public List<Edge> edges = new ArrayList<>();
        public int minEdgeWeight;

        public Node(int id) {
            this.id = id;
        }
    }

    public static class Edge {

        public Node origin;
        public Node end;
        public int w;

        public Edge(Node origin, Node end, int w) {
            this.origin = origin;
            this.end = end;
            this.w = w;
        }
    }


}
