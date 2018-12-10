package lab7;

import com.sun.org.apache.xml.internal.utils.IntVector;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class BalanceCheck  {

    private static Node[] nodes;
    public static void main(String[] args) throws IOException {
        BufferedReader scan = new BufferedReader(new FileReader("balance.in"));
        int n = Integer.parseInt(scan.readLine());
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            String line = scan.readLine();
            String[] parts = line.split(" ");
            nodes[i] = new Node(i, 0, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]) - 1);
        }

        PrintWriter out = new PrintWriter("balance.out");
        for (Node node : nodes) {
            out.println(getBalance(node));
        }

        out.close();
    }


    private static int getBalance(Node root){
        if(root == null)
            return 0;
        int lh = root.idLeft == -1 ? 0 : getHeight(nodes[root.idLeft]);
        int rh = root.idRight == -1 ? 0 : getHeight(nodes[root.idRight]);

        return rh - lh;
    }

    private static int getHeight(Node root){
        if(root == null)
            return 0;

        if(root.treeHeight != -1)
            return root.treeHeight;

        int h = 0;
        if(root.idRight != -1)
            h = Math.max(h, getHeight(nodes[root.idRight]));
        if(root.idLeft != -1)
            h = Math.max(h, getHeight(nodes[root.idLeft]));

        return root.treeHeight = h + 1;
    }

    private static class Node {
        int id;
        int parentId;
        int idLeft, idRight;
        int value;

        int height;

        int treeHeight = -1;
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
