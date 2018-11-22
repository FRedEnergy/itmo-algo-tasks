package lab6;

import java.io.*;
import java.util.*;

public class Check {

    public static void main(String[] args) throws IOException {
        BufferedReader scan = new BufferedReader(new FileReader("check.in"));
        int n = Integer.parseInt(scan.readLine());
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            String line = scan.readLine();
            String[] parts = line.split(" ");
            nodes[i] = new Node(i, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1,
                    Integer.parseInt(parts[2]) - 1);
        }
        scan.close();

        boolean isCorrect = n <= 0 || isCorrect(nodes, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0);

        PrintWriter out = new PrintWriter("check.out");
        out.println(isCorrect ? "YES" : "NO");
        out.close();
    }


    private static boolean isCorrect(Node[] nodes, int min, int max, int i){
        Node node = nodes[i];
        if(node.isVisited)
            return true;

        node.isVisited = true;

        if(!(node.value > min && node.value < max))
            return false;

        if(node.idLeft != -1){
            Node left = nodes[node.idLeft];
            if(!isCorrect(nodes, min, node.value, left.id))
                return false;
        }

        if(node.idRight != -1){
            Node right = nodes[node.idRight];
            if(!isCorrect(nodes, node.value, max, right.id))
                return false;
        }

        return true;
    }

    private static class Node {
        int id;
        int parentId;
        int idLeft, idRight;
        int value;

        int height;
        boolean isVisited = false;

        public Node(int id, int value, int idLeft, int idRight) {
            this.id = id;
            this.idLeft = idLeft;
            this.idRight = idRight;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id == node.id &&
                    parentId == node.parentId &&
                    idLeft == node.idLeft &&
                    idRight == node.idRight &&
                    value == node.value &&
                    height == node.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, parentId, idLeft, idRight, value, height);
        }
    }
}
