package lab4;

public class PriorityQueue {

    public static void main(String[] args) {

        PQStruct q = new PQStruct(256);
        q.offer(3);
        int i = q.offer(4);

        q.offer(2);
        System.out.println(q.removeMax());
        q.replace(i, 1);

        System.out.println(q.removeMax());
        System.out.println(q.removeMax());
        System.out.println(q.removeMax());
    }

    public static class PQStruct {

        public int[] heap;

        public PQStruct(int size){
            this.heap = new int[size];
        }

        public int offer(int val){
            return 0;
        }

        public int removeMax(){
            return 0;
        }

        public void replace(int i, int val){

        }
    }
}
