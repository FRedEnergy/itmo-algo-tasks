package lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BSTree {

    public static void main(String[] args) throws IOException {
        BufferedReader scan = new BufferedReader(new FileReader("bstsimple.in"));
        Tree tree = new Tree();

        PrintWriter out = new PrintWriter("bstsimple.out");
        String line;
        while((line = scan.readLine()) != null){
            String[] parts = line.split(" ");
            String action = parts[0];
            int val = Integer.parseInt(parts[1]);

            if("insert".equals(action)){
                tree.insert(val);
            } else if("delete".equals(action)){
                tree.delete(val);
            } else if("exists".equals(action)){
                out.println(tree.contains(val));
            } else if("next".equals(action)){
                Node res = new Node(val);
                int m = tree.next(tree.root, val, res);
                if(m != val)
                    out.println(m);
                else
                    out.println("none");
            } else if("prev".equals(action)){
                Node prev = new Node(val);
                int m = tree.prev(tree.root, val, prev);
                if(m != val)
                    out.println(m);
                else
                    out.println("none");
            }
        }
        out.close();

    }


    private static class Node {

        Node left, right;
        int value;

        public Node(int value) {
            this.value = value;
        }

    }

    private static class Tree {
        private Node root;

        public void insert(int x){
            if(root == null){
                root = new Node(x);
                return;
            }
            Node node = root;
            while (true){
                if(node.value == x)
                    return;

                if(x < node.value){
                    if(node.left == null)
                        node.left = new Node(x);
                    else
                        node = node.left;
                } else {
                    if(node.right == null)
                        node.right = new Node(x);
                    else
                        node = node.right;
                }
            }
        }

        public int next(Node root, int value, Node result) {
            if (root == null)
                return result.value;

            if (root.value <= value){
                next(root.right, value, result);
            } else {
                result.value = root.value;
                next(root.left, value, result);
            }

            return result.value;
        }

        public int prev(Node root, int value, Node result){
            if(root == null)
                return result.value;

            if(root.value >= value){
                prev(root.left, value, result);
            } else{
                result.value = root.value;
                prev(root.right, value, result);
            }

            return result.value;
        }

        public boolean contains(int x){
            if(root == null)
                return false;

            Node node = root;
            while (node != null){
                if(node.value == x)
                    return true;
                if(x < node.value)
                    node = node.left;
                else
                    node = node.right;
            }
            return false;
        }

        public void delete(int x){
            if(root == null)
                return;
            Node node = root, parent = null;
            while (node != null){
                if(node.value == x)
                    break;
                parent = node;
                if(x < node.value)
                    node = node.left;
                else
                    node = node.right;
            }
            if(node == null)
                return;

            if(node.right == null){
                if(parent == null)
                    root = node.left;
                else
                    if(node == parent.left)
                        parent.left = node.left;
                    else
                        parent.right = node.left;
            } else {
                Node mostLeft = node.right;
                parent = null;
                while (mostLeft.left != null) {
                    parent = mostLeft;
                    mostLeft = mostLeft.left;
                }

                if(parent != null)
                    parent.left = mostLeft.right;
                else
                    node.right = mostLeft.right;

                node.value = mostLeft.value;
            }
        }
    }
}
