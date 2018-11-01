package lab4;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Queue {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scan = new FastScanner(new File("queue.in"));
        int n = scan.nextInt();
        QueueStruct q = new QueueStruct(n);

        PrintWriter out = new PrintWriter("queue.out");
        for (int i = 0; i < n; i++) {
            String c = scan.next();
            if(c.equals("+"))
                q.offer(scan.nextInt());
            else
                out.println(q.pop());
        }
        out.close();
    }

    public static class QueueStruct {

        public int[] data;
        public int writeIndex = 0;
        public int readIndex = 0;

        public QueueStruct(int size){
            this.data = new int[size];
        }

        public void offer(int i){
            data[++writeIndex % data.length] = i;
        }

        public int pop(){
            return data[++readIndex % data.length];
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
