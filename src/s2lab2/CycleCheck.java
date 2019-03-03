package s2lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class CycleCheck {

    public static final int STATE_NOT_VISITED = 0;
    public static final int STATE_VISITING = 1;
    public static final int STATE_VISITED = 2;

    private static Node cycleStart, cycleEnd;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("./cycle.in"));

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
        }

        for (Node node : nodes)
            if (node != null)
                visit(node);

        PrintWriter out = new PrintWriter(new FileOutputStream("./cycle.out"));

        if(cycleEnd == null)
            out.println("NO");
        else{
            out.println("YES");
            List<Integer> path = new ArrayList<>();
            path.add(cycleStart.id);
            for(Node v = cycleEnd; v != cycleStart; v = v.parent)
                path.add(v.id);

            for (int i = path.size() - 1; i >= 0; i--)
                out.print(path.get(i) + 1 + " ");


        }
        out.close();

    }

    private static void visit(Node node){
        if(cycleEnd != null)
            return;

        node.state = STATE_VISITING;

        for (Node adj : node.adjacent) {
            if(cycleEnd != null)
                return;

            if(adj.state == STATE_NOT_VISITED){
                adj.parent = node;
                visit(adj);
            } else if(adj.state == STATE_VISITING){
                cycleStart = adj;
                cycleEnd = node;
                return;
            }
        }

        node.state = STATE_VISITED;
    }

    private static class Node {

        public final int id;
        public Set<Node> adjacent = new HashSet<>();
        public int state = STATE_NOT_VISITED;

        public Node parent;

        public Node(int id) {
            this.id = id;
        }
    }
}
