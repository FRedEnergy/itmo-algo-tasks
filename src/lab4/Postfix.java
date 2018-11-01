package lab4;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Postfix {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("postfix.in"));
        String line = scan.nextLine();
        String[] input = line.split(" ");

        int[] numbers = new int[input.length];
        int index = 0;
        for (int i = 0; i < input.length; i++) {
            String arg = input[i];
            if("*".equals(arg)){
                int a = numbers[--index];
                int b = numbers[--index];
                numbers[index++] = a * b;
            } else if("-".equals(arg)){
                int a = numbers[--index];
                int b = numbers[--index];
                numbers[index++] = b - a;
            } else if("+".equals(arg)){
                int a = numbers[--index];
                int b = numbers[--index];
                numbers[index++] = a + b;
            } else {
                numbers[index++] = Integer.parseInt(arg);
            }
        }

        PrintWriter out = new PrintWriter("postfix.out");
        out.println(numbers[0]);
        out.close();
    }
}
