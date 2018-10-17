package lab3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class HeapSort {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("sort.in"));
        int n = scan.nextInt();
        int[] a = new int[n];
        for(int i = 0; i < n; i++)
            a[i] = scan.nextInt();

        heapsort(a);

        PrintWriter out = new PrintWriter("sort.out");
        for (int i : a) out.print(i + " ");
        out.close();
    }

    private static void swap(int[] a, int i, int j){
        int t = a[j];
        a[j] = a[i];
        a[i] = t;
    }

    private static int left(int i) { return 2 * i + 1; }
    private static int right(int i) { return 2 * i + 2; }

    private static void siftDown(int[] a, int lim, int i){
        while (left(i) < lim){
            //choosing child with largest value
            int t = left(i);
            if(right(i) < lim && a[left(i)] < a[right(i)])
                t = right(i);

            //swapping child with target if if it's bigger
            if(a[i] < a[t]){
                swap(a, t, i);
                i = t;
            } else break;
        }
    }

    private static void buildHeap(int[] a){
        for (int i = a.length - 1; i >= 0; i--)
            siftDown(a, a.length, i);
    }

    private static void heapsort(int[] a) {
        buildHeap(a);
        for (int i = a.length; i > 1; i--) {
            swap(a, 0, i - 1);
            siftDown(a, i - 1, 0);
        }
    }
}
