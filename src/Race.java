
import java.io.*;
import java.util.*;

public class Race {

    static FastScanner in;
    static PrintWriter out;

    public static void main(String[] args) {
        setup("race.in", "race.out");


        int n = in.nextInt();
        List<Racer> racers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Racer racer = new Racer(in.next(), in.next(), i);
            racers.add(racer);
        }
        qsort(racers, 0, racers.size() - 1);

        String prevCountry = null;
        for (Racer racer : racers) {
            if(!Objects.equals(racer.country, prevCountry)) {
                prevCountry = racer.country;
                out.append("=== " + prevCountry + " ===\n");
            }
            out.append(racer.name + "\n");
        }

        out.close();
    }

    static Random rand = new Random();
    static void qsort(List<Racer> a, int l, int r){
        int k = ((r - l) <= 0 ? 0 : rand.nextInt(r - l)) + l;
        Racer key = a.get(k);
        int i = l;
        int j = r;
        while(i <= j){
            while(a.get(i).compareTo(key) < 0)
                i++;
            while(key.compareTo(a.get(j)) < 0)
                j--;

            if(i <= j){
                Racer t = a.get(i);
                a.set(i, a.get(j));
                a.set(j, t);
                i++;
                j--;
            }
        }

        if(l < j)
            qsort(a, l, j);
        if(i < r)
            qsort(a, i, r);
    }

    static class Racer implements Comparable<Racer>{
        final String country;
        final String name;
        final int place;

        public Racer(String country, String name, int place) {
            this.country = country;
            this.name = name;
            this.place = place;
        }

        @Override
        public int compareTo(Racer o) {
            int l = country.compareTo(o.country);
            if(l != 0)
                return l;
            return Integer.compare(place, o.place);
        }
    }

    public static void setup(String inFile, String outFile) {
        try {
            in = new FastScanner(new File(inFile));
            out = new PrintWriter(new File(outFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }



}
