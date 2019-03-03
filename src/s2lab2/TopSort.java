package s2lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TopSort {

    public static final int STATE_NOT_VISITED = 0;
    public static final int STATE_VISITING = 1;
    public static final int STATE_VISITED = 2;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("./topsort.in"));

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

        LinkedList<Node> stack = new LinkedList<>();

        boolean isSuccess = true;

        for (Node node : nodes) {
            if (node != null && node.state != STATE_VISITED && !topSort(node, stack)) {
                isSuccess = false;
                break;
            }
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("./topsort.out"));
        if(!isSuccess){
            out.print("-1");
        } else {
            for (Node node : stack)
                out.print((node.id + 1) + " ");
        }
        out.close();


    }

    private static boolean topSort(Node node, Deque<Node> stack){
        boolean isSuccess = true;
        node.state = STATE_VISITING;
        for (Node adj : node.adjacent) {
            if(!isSuccess)
                return false;

            if(adj.state == STATE_NOT_VISITED)
                isSuccess = topSort(adj, stack);
            else if(adj.state == STATE_VISITING)
                return false;
        }

        node.state = STATE_VISITED;
        stack.push(node);
        return isSuccess;
    }

    private static class Node {

        public final int id;
        public List<Node> adjacent = new ArrayList<>();
        public int state = STATE_NOT_VISITED;

        public Node(int id) {
            this.id = id;
        }
    }
}
