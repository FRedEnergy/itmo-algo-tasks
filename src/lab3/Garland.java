package lab3;

import java.util.Scanner;

public class Garland {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        float high = scan.nextFloat();
        float low = 0F;

        //h[i] = h[0] * i - h[0] * (i - 1) + i * (i - 1)

        while(true){
            float mid = (high + low) / 2F;
            System.out.println("High = " + high + ", low " + low + ", mid = " + mid);
            if(mid == low || high == mid)
                break;
            if(onGround(n, high, low)){
                high = mid;
                System.out.println("too low");
            } else {
                low = mid;
            }
        }


        System.out.println("High = " + high + ", low " + low);

    }

    private static float getHeight(int i, float h0, float h1){
        float n = i;
        return h1 * n - h0 * (n - 1) + n * (n - 1);
    }

    private static boolean onGround(int idx, double max, double low) {
        double mid = low;
        for (int i = 3; i <= idx; i++) {
            double prev = mid;
            mid = 2 * (mid + 1) - max; //will be zero if mid < max
            max = prev;
            if (mid < 0)
                return false;
        }
        return true;
    }
}
