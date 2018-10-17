package lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RadixSort {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("radixsort.in"));
        int n = scan.nextInt();
        int digits = scan.nextInt();
        int phases = scan.nextInt();
        String[] a = new String[n];

        for (int i = 0; i < n; i++)
            a[i] = scan.next();
        scan.close();

        for (int i = 0; i < phases; i++) {
            radixSort(a, digits - i - 1);
        }

        PrintWriter out = new PrintWriter("radixsort.out");
        for(int i = 0; i < a.length; i++)
            out.println(a[i]);
        out.close();

    }

    private static void radixSort(String[] a,int digitIndex){
        int[] count = new int[256];
        for(int i = 0; i < a.length; i++){
            char c = a[i].charAt(digitIndex);
            count[c]++;
        }
        count[0]--;
        for(int i = 1; i < count.length; i++)
            count[i] += count[i - 1];

        String[] temp = new String[a.length];
        for (int i = a.length - 1; i >= 0; i--) {
            char c = a[i].charAt(digitIndex);
            int targetIndex = count[c]--;
            temp[targetIndex] = a[i];
        }
        System.arraycopy(temp, 0, a, 0, a.length);
    }
}
