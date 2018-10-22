package lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Garland {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("garland.in"));
        int n = scan.nextInt();
        double firstHeight = scan.nextDouble();

        double low = 0.0;
        double high = firstHeight;

        while(true){
            double mid = (high + low) / 2.0;
            if(mid == low || high == mid)
                break;

            if(isTooLow(n, firstHeight, mid))
                low = mid;
            else
                high = mid;

        }

        double result = findBulbHeight(n, firstHeight, high);

        PrintWriter out = new PrintWriter("garland.out");
        out.append(String.format("%.2f", result));
        out.close();
    }

    private static double findLowestBulb(int idx, double firstHeight, double startHeight){
        for(int i = 3; i <= idx; i++){
            double next = startHeight + 1 - firstHeight / 2F;
            firstHeight = startHeight;
            startHeight = next * 2F;
            if(startHeight < 0)
                return startHeight;
        }
        return startHeight;
    }

    private static double findBulbHeight(int idx, double firstHeight, double startHeight){
        for(int i = 3; i <= idx; i++){
            double next = startHeight + 1 - firstHeight / 2.0;
            firstHeight = startHeight;
            startHeight = next * 2.0;
        }
        return startHeight;

    }

    private static boolean isTooLow(int idx, double firstHeight, double low) {
        return findLowestBulb(idx, firstHeight, low) <= 0.0;
    }
}
