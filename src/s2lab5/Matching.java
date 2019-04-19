package s2lab5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Matching {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("matching.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();
        int k = scan.nextInt();

        int total = n + m + 2;
        Node[] nodes = new Node[total];
        for (int i = 0; i < total; i++)
            nodes[i] = new Node(i);
        int[][] rMatrix = new int[total][total];

        Node start = nodes[nodes.length - 2];
        Node end = nodes[nodes.length - 1];
        for (int i = 0; i < n; i++)
            rMatrix[start.id][i] = 1;
        for (int i = 0; i < m; i++)
            rMatrix[n + i][end.id] = 1;


        for (int i = 0; i < k; i++) {
            Node left = nodes[scan.nextInt() - 1];
            Node right = nodes[scan.nextInt() - 1 + n];
            rMatrix[left.id][right.id] = 1;
        }

        long maxFlow = 0;

        while (bfs(nodes, rMatrix, start, end)){
            long pathFlow = Long.MAX_VALUE;
            for(Node v = end; v != start; v = v.parent){
                Node u = v.parent;
                pathFlow = Math.min(pathFlow, rMatrix[u.id][v.id]);
            }

            for(Node v = end; v != start; v = v.parent){
                Node u = v.parent;
                rMatrix[u.id][v.id] -= pathFlow;
                rMatrix[v.id][u.id] += pathFlow;
            }

            maxFlow += pathFlow;
        }


        PrintWriter out = new PrintWriter("matching.out");
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
        public boolean isVisited = false;
        public Node parent = null;

        public Node(int id) {
            this.id = id;
        }
    }
}
