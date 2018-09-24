import java.util.Scanner;

public class AntiQSort {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();


    }

    static void qsort(int[] a, int l, int r){
        int k = (l + r) / 2;
        int key = a[k];
        int i = l;
        int j = r;
        while(i <= j){
            while(a[i] < key)
                i++;
            while(key < a[j])
                j--;

            if(i <= j){
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                i++;
                j--;
            }
        }

        if(l < j)
            qsort(a, l, j);
        if(i < r)
            qsort(a, i, r);
    }
}
