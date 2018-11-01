package lab4;

import java.io.*;
import java.util.StringTokenizer;

public class Stack {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("./stack.in"));
        int n = scan.nextInt();

        PrintWriter out = new PrintWriter("stack.out");
        StackStruct stack = new StackStruct(n);
        for (int i = n; i > 0; i--) {
            String c = scan.next();
            if(c.equals("+"))
                stack.push(scan.nextInt());
            else
                out.println(stack.pop());
        }
        out.close();
    }

    public static class StackStruct {
        public int[] data;
        public int index = 0;

        public StackStruct(int size){
            this.data = new int[size];
        }

        public void push(int i){
            data[++index] = i;
        }

        public int pop(){
            return data[index--];
        }

        public boolean isEmpty(){
            return index == 0;
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
