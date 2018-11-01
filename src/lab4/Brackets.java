package lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Brackets {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("brackets.in"));

        PrintWriter out = new PrintWriter("brackets.out");
        while (scan.hasNext()){
            String line = scan.next();
            char[] stack = new char[line.length() + 1];
            int stackIndex = 0;
            boolean isInvalid = false;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if(c == '(' || c == '['){
                    stack[++stackIndex] = c;
                } else {
                    char p = stack[stackIndex--];
                    if ((c == ')' && p != '(') || (c == ']' && p != '[')) {
                        isInvalid = true;
                        break;
                    }
                }
            }
            out.println((isInvalid || stackIndex != 0) ? "NO" : "YES");
        }
        scan.close();
        out.close();
    }
}
