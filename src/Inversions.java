import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.System.arraycopy;

public class Inversions {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("inversions.in"));
        int n = scan.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = scan.nextInt();
        scan.close();

        int count = countMergeSort(a, new int[n], 0, a.length - 1);

        PrintWriter p = new PrintWriter(new FileOutputStream("inversions.out"));
        p.append(String.valueOf(count));
        p.close();

    }

    static int countMergeSort(int[] a, int[] b, int left, int right){
        if(right <= left) return 0;

        int mid = (right + left) / 2;
        return countMergeSort(a, b, left, mid) +
            countMergeSort(a, b, mid + 1, right) +
            countMerge(a, b, left, mid + 1, right);
    }

    static int countMerge(int[] a, int b[], int left, int mid, int right){
        int l = left;
        int m = mid;
        int bl = left;
        int count = 0;

        while (l <= mid - 1 && m <= right){
            if(a[l] <= a[m]) {
                b[bl++] = a[l++];
            } else {
                b[bl++] = a[m++];
                count += mid - l;
            }
        }

        while (l <= mid - 1)
            b[bl++] = a[l++];

        while (m <= right)
            b[bl++] = a[m++];

        arraycopy(b, left, a, left, max(0, right + 1 - left));

        return count;
    }
}
