package s2lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MST {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("./spantree.in"));
        int n = scan.nextInt();

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(scan.nextInt(), scan.nextInt());

        nodes[0].m = 0;
        nodes[0].isVisited = true;

        for (int i = 1; i < n; i++) {
            Node node = nodes[i];
            node.m = getDistance(nodes[0], node);
        }

        for (int i = 1; i < n - 1; i++) {
            Node v = null;
            for (int j = 0; j < n; j++) {
                Node g = nodes[j];
                if (!g.isVisited && (v == null || g.m < v.m))
                    v = g;
            }

            v.isVisited = true;

            for (int j = 0; j < n; j++) {
                Node g = nodes[j];
                if(g == v || g.isVisited)
                    continue;

                double d = getDistance(v, g);

                if (d < g.m)
                    g.m = d;
            }
        }

        double s = 0;
        for (Node node : nodes) {
            s += node.m;
        }
        PrintWriter out = new PrintWriter("./spantree.out");
        out.println(s);
        out.close();
    }

    private static double getDistance(Node v, Node g) {
        int dx = g.x - v.x;
        int dy = g.y - v.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static class Node {

        public final int x, y;

        double m = Double.MAX_VALUE;

        boolean isVisited = false;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
