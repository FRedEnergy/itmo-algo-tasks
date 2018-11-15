package lab6;

import java.io.*;
import java.util.*;

public class Quack {

    private static final int LIM = 65536;

    Deque<Integer> q = new LinkedList<>();
    Map<String, Integer> registry = new HashMap<>();
    Map<String, Integer> labels = new HashMap<>();


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("quack.in")));

        List<String> input = new ArrayList<>();
        String inputLine;
        while ((inputLine = reader.readLine()) != null)
            input.add(inputLine);

        Quack quack = new Quack();
        quack.compile(input);
        quack.run(input);
    }

    private void compile(List<String> input){
        //resolving labels
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if(line.isEmpty())
                continue;

            if(':' == line.charAt(0))
                labels.put(line.substring(1), i);
        }
    }

    private int get(){
        if(q.isEmpty())
            return 0;
        return q.removeFirst() % LIM;
    }

    private void put(int x){
        q.addLast(x % LIM);
    }

    private void setReg(String reg, int x){
        registry.put(reg, x);
    }

    private int getReg(String reg){
        return registry.get(reg);
    }

    private void run(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if("+".equals(line)){
                put(get() + get());
            } else if("-".equals(line)){
                put(get() - get());
            } else if("*".equals(line)){
                put(get() * get());
            } else if("/".equals(line)){
                int x = get();
                int y = get();
                put(y == 0 ? 0 : x / y);
            } else if("%".equals(line)) {
                int x = get();
                int y = get();
                put(y == 0 ? 0 : x % y);
            } else if(line.startsWith(">")){
                setReg(line.substring(1), get());
            } else if(line.startsWith("<")){
                put(getReg(line.substring(1)));
            } else if(line.startsWith("P")){
                System.out.println(line.length() > 1 ? getReg(line.substring(1)) : get());
            } else if(line.startsWith("C")){
                int x = line.length() <= 1 ? get() : getReg(line.substring(1));
                char c = (char) (x % 256);
                System.out.print(c);
            } else if(line.startsWith("J")){
                i = labels.get(line.substring(1));
            } else if(line.startsWith("Z")){
                int reg = getReg(String.valueOf(line.charAt(1)));
                if(reg == 0)
                    i = labels.get(line.substring(2));
            } else if(line.startsWith("G")){
                int regFirst = getReg(String.valueOf(line.charAt(1)));
                int regSecond = getReg(String.valueOf(line.charAt(2)));
                if(regFirst > regSecond)
                    i = labels.get(line.substring(3));
            } else if("Q".equals(line)){
                break;
            } else if(!line.startsWith(":")){
                put(Integer.parseInt(line));
            }
        }
    }

}
