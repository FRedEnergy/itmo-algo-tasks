package lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Queue {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("queue.in"));
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
        scan.close();
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
}
