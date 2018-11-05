package lab5;

import java.io.*;

public class OurSet  {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("set.in")));
        SetStruct set = new SetStruct(1 << 20);

        PrintWriter out = new PrintWriter("set.out");
        String line;
        while ((line = reader.readLine()) != null){
            String[] in = line.split(" ");
            String action = in[0];
            int value = Integer.parseInt(in[1]);
            if("insert".equals(action)){
                set.insert(value);
            } else if("exists".equals(action)){
                out.println(set.contains(value));
            } else if("delete".equals(action)){
                set.delete(value);
            }
        }
        out.close();
        reader.close();
    }

    public static class LinkedSetNode {

        public LinkedSetNode prev, next;
        public int value;

        public LinkedSetNode(int value) {
            this.value = value;
        }
    }

    public static class SetStruct {
        public LinkedSetNode[] buckets;
        public int size = 0;

        public SetStruct(int loadFactor){
            buckets = new LinkedSetNode[loadFactor];
        }

        public int getHash(int x){
            x = ((x >> 16) ^ x) * 0x45d9f3b;
            x = ((x >> 16) ^ x) * 0x45d9f3b;
            x = (x >> 16) ^ x;
            return Math.abs(x) % buckets.length;
        }

        public boolean contains(int value){
            if(size <= 0)
                return false;
            LinkedSetNode bucket = buckets[getHash(value)];
            while (bucket != null){
                if(bucket.value == value)
                    return true;
                bucket = bucket.next;
            }
            return false;
        }

        public void insert(int value){
            if(contains(value))
                return;

            int hash = getHash(value);
            LinkedSetNode bucket = buckets[hash];
            if(bucket == null){
                buckets[hash] = new LinkedSetNode(value);
            } else {
                while (true){
                    if(bucket.next == null) {
                        bucket.next = new LinkedSetNode(value);
                        bucket.next.prev = bucket;
                        break;
                    } else {
                        bucket = bucket.next;
                    }
                }
            }
            size++;
        }

        public void delete(int value){
            if(size <= 0)
                return;

            int hash = getHash(value);
            LinkedSetNode bucket = buckets[hash];
            while (bucket != null){
                if(bucket.value == value){
                    if(bucket.prev == null && bucket.next == null){
                        buckets[hash] = null;
                    } else if(bucket.prev == null){
                        buckets[hash] = bucket.next;
                        bucket.next.prev = null;
                    } else if(bucket.next == null){
                        bucket.prev.next = null;
                    } else {
                        bucket.prev.next = bucket.next;
                        bucket.next.prev = bucket.prev;
                    }
                    size--;
                    return;
                } else {
                    bucket = bucket.next;
                }
            }
        }
    }

}
