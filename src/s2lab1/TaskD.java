package s2lab1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class TaskD {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("components.in"));

        int n = scan.nextInt();
        int m = scan.nextInt();

        Node[] v = new Node[n];
        for (int i = 0; i < v.length; i++)
            v[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            int origin = scan.nextInt() - 1;
            int end = scan.nextInt() - 1;
            Node originNode = v[origin];
            Node endNode = v[end];
            originNode.adjacency.add(endNode);
            endNode.adjacency.add(originNode);
        }

        Deque<Node> workQ = new LinkedList<>();

        int left = v.length;
        int nextIslandId = 0;
        int index = 0;
        while (left > 0) {
            Node next = v[index++];
            if(next.isVisited)
                continue;

            int island = nextIslandId++;
            workQ.push(next);
            while (!workQ.isEmpty()) {
                Node root = workQ.poll();
                if (root == null || root.isVisited)
                    continue;

                root.isVisited = true;
                root.islandId = island;
                left--;
                workQ.addAll(root.adjacency);
            }
            workQ.clear();
        }

        for (Node node : v)
            if (!node.isVisited)
                node.islandId = nextIslandId++;

        PrintWriter out = new PrintWriter("components.out");
        out.println(nextIslandId);
        for (Node node : v)
            out.print(node.islandId + 1 + " ");
        out.close();
    }



    private static class Node {

        public int id;
        public int islandId;
        public boolean isVisited = false;

        public Set<Node> adjacency = new HashSet<>();

        public Node(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id == node.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
