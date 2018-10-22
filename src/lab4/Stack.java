package lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Stack {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream("stack.in"));
        int n = scan.nextInt();

        PrintWriter out = new PrintWriter("stack.out");
        StackStruct stack = new StackStruct(n);
        for (int i = n; i > 0; i--) {
            String c = scan.next();
            if(c.equals("+"))
                stack.push(scan.nextInt());
            else
                out.println(stack.pop());
        }
        scan.close();
        out.close();
    }

    public static class StackStruct {
        public int[] data;
        public int index = 0;

        public StackStruct(int size){
            this.data = new int[size];
        }

        public void push(int i){
            data[++index] = i;
        }

        public int pop(){
            return data[index--];
        }
    }
}
