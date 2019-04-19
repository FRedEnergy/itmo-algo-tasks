package s2lab5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class MaxFlow {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("maxflow.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        int[][] rMatrix = new int[n][n];

        for (int i = 0; i < m; i++) {
            Node origin = nodes[scan.nextInt() - 1];
            Node end = nodes[scan.nextInt() - 1];
            int cap = scan.nextInt();

            rMatrix[origin.id][end.id] = cap;
        }

        Node start = nodes[0];
        Node sink = nodes[n - 1];

        long maxFlow = 0;

        while (bfs(nodes, rMatrix, start, sink)){
            long pathFlow = Long.MAX_VALUE;
            for(Node v = sink; v != start; v = v.parent){
                Node u = v.parent;
                pathFlow = Math.min(pathFlow, rMatrix[u.id][v.id]);
            }

            for(Node v = sink; v != start; v = v.parent){
                Node u = v.parent;
                rMatrix[u.id][v.id] -= pathFlow;
                rMatrix[v.id][u.id] += pathFlow;
            }

            maxFlow += pathFlow;
        }


        PrintWriter out = new PrintWriter("maxflow.out");
        out.println(maxFlow);
        out.close();

    }

    private static boolean bfs(Node[] nodes, int[][] matrix, Node start, Node sink){
        for (Node node : nodes) {
            node.isVisited = false;
            node.parent = null;
        }

        Deque<Node> q = new LinkedList<>();
        q.offer(start);
        start.isVisited = true;

        while (!q.isEmpty()){
            Node node = q.poll();
            for (int i = 0; i < matrix.length; i++) {
                Node v = nodes[i];
                if(v.isVisited || matrix[node.id][v.id] <= 0)
                    continue;

                q.offer(v);
                v.parent = node;
                v.isVisited = true;
            }
        }
        return sink.isVisited;
    }


    private static class Node {

        public final int id;
        public boolean isVisited;
        public Node parent = null;

        public Node(int id) {
            this.id = id;
        }
    }

}
