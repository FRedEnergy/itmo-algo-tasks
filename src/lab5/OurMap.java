package lab5;

import java.io.*;

public class OurMap {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("map.in")));
        MapStruct map = new MapStruct(1 << 20);

        PrintWriter out = new PrintWriter("map.out");
        String line;
        while ((line = reader.readLine()) != null){
            String[] in = line.split(" ");
            String action = in[0];
            String key = in[1];
            if("put".equals(action)){
                map.put(key, in[2]);
            } else if("get".equals(action)){
                String value = map.get(key);
                out.println(value != null ? value : "none");
            } else if("delete".equals(action)){
                map.delete(key);
            }
        }
        out.close();
        reader.close();
    }


    public static class LinkedNode {

        public LinkedNode prev, next;
        public String key;
        public String value;

        public LinkedNode(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class MapStruct {
        public LinkedNode[] buckets;
        public int size = 0;

        public MapStruct(int loadFactor){
            buckets = new LinkedNode[loadFactor];
        }

        public int getHash(String x){
            return Math.abs(x.hashCode()) % buckets.length;
        }

        public boolean contains(String key){
            if(size <= 0)
                return false;
            LinkedNode bucket = buckets[getHash(key)];
            while (bucket != null){
                if(bucket.key.equals(key))
                    return true;
                bucket = bucket.next;
            }
            return false;
        }

        public void put(String key, String value){
            int hash = getHash(key);
            LinkedNode bucket = buckets[hash];
            if(bucket == null) {
                buckets[hash] = new LinkedNode(key, value);
                size++;
                return;
            }
            while (true){
                if(bucket.key.equals(key)){
                    bucket.value = value;
                    break;
                } else if(bucket.next == null) {
                    bucket.next = new LinkedNode(key, value);
                    bucket.next.prev = bucket;
                    size++;
                    break;
                } else {
                    bucket = bucket.next;
                }
            }
        }

        public String get(String key){
            if(size <= 0)
                return null;

            LinkedNode bucket = buckets[getHash(key)];
            while (bucket != null){
                if(bucket.key.equals(key))
                    return bucket.value;
                bucket = bucket.next;
            }
            return null;
        }

        public void delete(String key){
            if(size <= 0)
                return;
            int hash = getHash(key);
            LinkedNode bucket = buckets[hash];
            while (bucket != null){
                if(bucket.key.equals(key)){
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
