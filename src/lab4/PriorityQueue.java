package lab4;

import java.io.*;
import java.util.*;

public class PriorityQueue {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("priorityqueue.in")));
        PQStruct q = new PQStruct(1_000_000);

        PrintWriter out = new PrintWriter("priorityqueue.out");
        Map<Integer, Integer> insertionIndices = new HashMap<>();
        int lineCounter = 1;
        String line;
        while((line = reader.readLine()) != null){
            String[] in = line.split(" ");
            if("push".equals(in[0])){
                insertionIndices.put(lineCounter, q.offer(Integer.parseInt(in[1])));
            } else if("extract-min".equals(in[0])){
                out.println(q.isEmpty() ? "*" : q.removeMin());
            } else if("decrease-key".equals(in[0])){
                int x = Integer.parseInt(in[1]);
                int y = Integer.parseInt(in[2]);
                q.replace(insertionIndices.get(x), y);
            }
            lineCounter++;
        }
        reader.close();
        out.close();
    }

    public static class PQStruct {

        public int[] data;
        public int size = 0;
        public int head = 0;
        public int tail = -1;

        public PQStruct(int size){
            this.data = new int[size];
        }

        public int offer(int val) {
            size++;
            data[++tail % data.length] = val;
            return tail;
        }

        public int removeMin(){
            int lowest = Integer.MAX_VALUE - 1;
            int lowestIndex = 0;
            for (int i = head; i <= tail; i++) {
                int val = data[i];
                if(val < lowest){
                    lowest = val;
                    lowestIndex = i;
                }
            }
            data[lowestIndex] = Integer.MAX_VALUE;
            size--;
            return lowest;
        }

        public void replace(int i, int val){
            data[i] = val;
        }

        public boolean isEmpty(){
            return size == 0;
        }
    }

}
