package lab5;

import java.io.*;

@SuppressWarnings("Duplicates")
public class Multimap {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("multimap.in")));
        MultiMapStruct mmap = new MultiMapStruct(1 << 20);

        PrintWriter out = new PrintWriter("multimap.out");
        String line;
        while ((line = reader.readLine()) != null){
            String[] in = line.split(" ");
            String action = in[0];
            String key = in[1];
            if("put".equals(action)){
                mmap.put(key, in[2]);
            } else if("get".equals(action)){
                SetStruct set = mmap.get(key);
                print(out, set);
            } else if("delete".equals(action)){
                mmap.deleteValue(key, in[2]);
            } else if("deleteall".equals(action)){
                mmap.delete(key);
            }
        }
        out.close();
        reader.close();
    }

    private static void print(PrintWriter out, SetStruct setStruct){
        if(setStruct == null){
            out.println(0);
            return;
        }
        out.print(setStruct.size + " ");
        for (int i = 0; i < setStruct.buckets.length; i++) {
            LinkedSetNode bucket = setStruct.buckets[i];
            while (bucket != null){
                out.print(bucket.value + " ");
                bucket = bucket.next;
            }
        }
        out.println();
    }

    public static int getStringHash(String x){
        int h = 0;
        for(int i = 0; i < x.length(); i++)
            h = 31 * h + x.charAt(i);
        return Math.abs(h);
    }

    public static class LinkedSetNode {

        public LinkedSetNode prev, next;
        public String value;

        public LinkedSetNode(String value) {
            this.value = value;
        }
    }

    public static class SetStruct {
        public LinkedSetNode[] buckets;
        public int size = 0;

        public SetStruct(int loadFactor){
            buckets = new LinkedSetNode[loadFactor];
        }

        public int getHash(String value){
            return getStringHash(value) % buckets.length;
        }

        public boolean contains(String value){
            if(size <= 0)
                return false;
            LinkedSetNode bucket = buckets[getHash(value)];
            while (bucket != null){
                if(bucket.value.equals(value))
                    return true;
                bucket = bucket.next;
            }
            return false;
        }

        public void insert(String value){
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

        public void delete(String value){
            if(size <= 0)
                return;

            int hash = getHash(value);
            LinkedSetNode bucket = buckets[hash];
            while (bucket != null){
                if(bucket.value.equals(value)){
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
    
    public static class LinkedMapNode {

        public LinkedMapNode prev, next;
        public String key;
        public SetStruct set;

        public LinkedMapNode(String key) {
            this.key = key;
            this.set = new SetStruct(1 << 8);
        }
    }
    
    public static class MultiMapStruct {
        public LinkedMapNode[] buckets;
        public int size = 0;

        public MultiMapStruct(int loadFactor){
            buckets = new LinkedMapNode[loadFactor];
        }

        public int getHash(String x){
            return getStringHash(x) % buckets.length;
        }

        public void put(String key, String value){
            int hash = getHash(key);
            LinkedMapNode bucket = buckets[hash];
            if(bucket == null) {
                buckets[hash] = new LinkedMapNode(key);
                buckets[hash].set.insert(value);
                size++;
                return;
            }
            while (true){
                if(bucket.key.equals(key)){
                    bucket.set.insert(value);
                    break;
                } else if(bucket.next == null) {
                    bucket.next = new LinkedMapNode(key);
                    bucket.next.set.insert(value);
                    bucket.next.prev = bucket;
                    size++;
                    break;
                } else {
                    bucket = bucket.next;
                }
            }
        }

        public SetStruct get(String key){
            if(size <= 0)
                return null;

            LinkedMapNode bucket = buckets[getHash(key)];
            while (bucket != null){
                if(bucket.key.equals(key))
                    return bucket.set;
                bucket = bucket.next;
            }
            return null;
        }

        public void delete(String key){
            if(size <= 0)
                return;
            int hash = getHash(key);
            LinkedMapNode bucket = buckets[hash];
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

        public void deleteValue(String key, String value){
            if(size <= 0)
                return;
            int hash = getHash(key);
            LinkedMapNode bucket = buckets[hash];
            while (bucket != null){
                if(bucket.key.equals(key)){
                    buckets[hash].set.delete(value);
                    return;
                } else {
                    bucket = bucket.next;
                }
            }
        }
    }

}
