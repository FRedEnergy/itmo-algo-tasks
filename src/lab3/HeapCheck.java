package lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class HeapCheck {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("isheap.in"));
        int n = scan.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = scan.nextInt();
        scan.close();

        PrintWriter out = new PrintWriter("isheap.out");
        Deque<Integer> q = new LinkedList<>();
        q.push(0);
        int maxProcessed = -1;
        while (maxProcessed < n && !q.isEmpty()) {
            List<Integer> p = new ArrayList<>(q);
            q.clear();
            for (int index : p) {
                if(index >= n) continue;

                maxProcessed = Math.max(index, maxProcessed);
                if (!validateNode(a, index)){
                    out.println("NO");
                    out.close();
                    return;
                }

                q.push(index * 2 + 1);
                q.push(index * 2 + 2);
            }
        }
        out.println("YES");
        out.close();
    }

    private static boolean validateNode(int[] a, int i){
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int k = a[i];
        if(left < a.length && !(k <= a[left]))
            return false;
        if(right < a.length && !(k <= a[right]))
            return false;
        return true;
    }

}
