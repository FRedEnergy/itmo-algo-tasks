package s2lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class HamiltonianPath {

    static final int MIN = -Integer.MAX_VALUE + 1;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("hamiltonian.in"));
        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < m; i++) {
            int out = scan.nextInt() - 1;
            int in = scan.nextInt() - 1;
            Node outNode = nodes[out] == null ? nodes[out] = new Node(out) : nodes[out];
            Node inNode = nodes[in] == null ? nodes[in] = new Node(in) : nodes[in];

            outNode.adjacent.add(inNode);
            inNode.inDegree++;
        }

        PrintWriter out = new PrintWriter(new FileOutputStream("hamiltonian.out"));
        out.println(isHamiltonian(nodes) ? "YES" : "NO");
        out.close();
    }

    private static void topSort(Node v, Deque<Node> s){
        v.isVisited = true;
        for (Node adj : v.adjacent)
            if(!adj.isVisited)
                topSort(adj, s);


        s.push(v);
    }

    private static boolean isHamiltonian(Node[] nodes){
        if(nodes.length == 1)
            return true;

        Node root = null;
        int rootCount = 0;
        for (Node node : nodes) {
            if(node != null && node.inDegree == 0){
                root = node;
                rootCount++;
            }
        }
        if(rootCount != 1)
            return false;

        root.d = 1;

        Deque<Node> s = new LinkedList<>();
        for (Node v : nodes)
            if (v != null && !v.isVisited)
                topSort(v, s);

        while (!s.isEmpty()){
            Node node = s.pop();
            if(node.d == MIN)
                continue;

            for (Node adj : node.adjacent)
                if(adj.d < node.d + 1)
                    adj.d = node.d + 1;
        }

        for (Node node : nodes)
            if(node != null && node.d == nodes.length)
                return true;

        return false;
    }



    private static class Node {

        public int id;
        public List<Node> adjacent = new ArrayList<>();
        public int inDegree = 0;
        public int d = MIN;

        public boolean isVisited = false;

        public Node(int id) {
            this.id = id;
        }
    }
}