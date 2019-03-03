package s2lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Cond {

    public static final int STATE_NOT_VISITED = 0;
    public static final int STATE_VISITING = 1;
    public static final int STATE_VISITED = 2;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("./cond.in"));

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
            toNode.inverse.add(fromNode);
        }

        Deque<Node> stack = new LinkedList<>();
        for (Node node : nodes)
            if(!node.isVisited)
                topSort(node, stack);

        int island = 0;
        for (Node node : stack) {
            if(node.state == STATE_VISITED)
                continue;

            island++;
            visit(node, island);
        }


        PrintWriter out = new PrintWriter(new FileOutputStream("./cond.out"));
        out.println(island);
        for (Node node : nodes)
            out.print(node.k + " ");
        out.close();

    }

    private static void topSort(Node v, Deque<Node> s){
        v.isVisited = true;
        for (Node adj : v.adjacent)
            if(!adj.isVisited)
                topSort(adj, s);


        s.push(v);
    }

    private static void visit(Node v, int island){
        v.state = STATE_VISITING;
        v.k = island;
        for (Node adj : v.inverse)
            if(adj.state == STATE_NOT_VISITED)
                visit(adj, island);

        v.state = STATE_VISITED;
    }

    private static class Node {

        public final int id;
        public Set<Node> adjacent = new HashSet<>();
        public Set<Node> inverse = new HashSet<>();
        public int state = STATE_NOT_VISITED;

        public boolean isVisited = false;

        int k = -1;

        public Node(int id) {
            this.id = id;
        }
    }
}
