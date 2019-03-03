package s2lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class BiPartite {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("./bipartite.in"));

        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            int from = scan.nextInt() - 1;
            int to = scan.nextInt() - 1;
            Node fromNode = nodes[from];
            Node toNode = nodes[to];
            fromNode.adjacent.add(toNode);
            toNode.adjacent.add(fromNode);
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("./bipartite.out"));
        out.println(isBipartite(nodes) ? "YES" : "NO");
        out.close();

    }

    private static boolean isBipartite(Node[] nodes){
        for (Node node : nodes) {
            if(node.isVisited)
                continue;

            Deque<Node> q = new LinkedList<>();
            q.add(node);
            node.i = 0;

            while (!q.isEmpty()){
                Node source = q.poll();

                for (Node adj : source.adjacent) {
                    int k = (source.i + 1) % 2;
                    if(adj.isVisited && k != adj.i)
                        return false;
                    else if(adj.isVisited)
                        continue;

                    adj.i = k;
                    adj.isVisited = true;

                    q.push(adj);
                }
            }

        }
        return true;
    }

    private static class Node {

        public final int id;
        public Set<Node> adjacent = new HashSet<>();

        public boolean isVisited = false;

        public int i = -1;

        public Node(int id) {
            this.id = id;
        }
    }
}
