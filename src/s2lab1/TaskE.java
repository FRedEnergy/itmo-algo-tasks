package s2lab1;

import java.io.*;
import java.util.*;

public class TaskE {

    public static void main(String[] args) throws IOException {
        BufferedReader scan = new BufferedReader(new FileReader("pathbge1.in"));

        String firstLine = scan.readLine();
        int n = Integer.parseInt(firstLine.split(" ")[0]);
        int m = Integer.parseInt(firstLine.split(" ")[1]);

        Node[] v = new Node[n];
        for (int i = 0; i < v.length; i++)
            v[i] = new Node(i);

        for (int i = 0; i < m; i++) {
            String line = scan.readLine();
            String[] parts = line.split(" ");
            int origin = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;
            Node originNode = v[origin];
            Node endNode = v[end];
            originNode.adjacency.add(endNode);
            endNode.adjacency.add(originNode);
        }


        List<Node> first = new ArrayList<>();
        List<Node> second = new ArrayList<>();

        List<Node> read = first;
        List<Node> write = second;

        read.add(v[0]);

        int d = 0;
        while(!read.isEmpty()){
            for (Node node : read) {
                if(node.isVisited)
                    continue;

                node.isVisited = true;
                node.distance = d;
                write.addAll(node.adjacency);
            }
            read.clear();
            read = read == first ? second : first;
            write = write == first ? second : first;
            d++;
        }


        PrintWriter out = new PrintWriter("pathbge1.out");
        for (Node node : v) {
            out.print(node.distance + " ");
        }
        out.close();
    }

    private static class Node {

        public int id;
        public int distance;
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
