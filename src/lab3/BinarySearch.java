package lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BinarySearch {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("./binsearch.in"));
        int n = scan.nextInt();
        int[] a  = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = scan.nextInt();

        int k = scan.nextInt();

        PrintWriter out = new PrintWriter(new FileOutputStream("./binsearch.out"));
        for (int i = 0; i < k; i++) {
            int target = scan.nextInt();
            int first = firstBinarySearch(a, target);
            int last = lastBinarySearch(a, target);

            if(first != -1) {
                first++;
                last++;
            }

            out.println(first + " " + last);
        }
        out.close();
        scan.close();
    }

    private static int firstBinarySearch(int[] a, int t){
        int l = 0, r = a.length - 1;
        while (l < r){
            int mid = l + (r - l) / 2;
            if(a[mid] >= t)
                r = mid;
            else
                l = mid + 1;
        }
        return a[l] == t ? l : -1;
    }

    private static int lastBinarySearch(int[] a, int t){
        int l = 0, r = a.length - 1;
        while (l < r){
            int mid = r - (r - l) / 2;
            if(a[mid] <= t)
                l = mid;
            else
                r = mid - 1;
        }
        return a[l] == t ? l : -1;
    }
}
