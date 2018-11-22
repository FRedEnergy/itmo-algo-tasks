package lab6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Height  {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("height.in"));
        int n = scan.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i, scan.nextInt(), scan.nextInt() - 1, scan.nextInt() - 1);
        }

        if(n > 0)
            calculateHeight(nodes, 0, 0);

        int h = 0;
        for (Node node : nodes)
            h = Math.max(node.height, h);

        PrintWriter out = new PrintWriter("height.out");
        out.println(h);
        out.close();
    }

    private static void calculateHeight(Node[] nodes, int i, int h){
        Node node = nodes[i];
        if(node.isVisited)
            return;

        node.height = h + 1;
        node.isVisited = true;
        if(node.idLeft != -1)
            calculateHeight(nodes, node.idLeft, node.height);
        if(node.idRight != -1)
            calculateHeight(nodes, node.idRight, node.height);
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
