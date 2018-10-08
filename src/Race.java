import java.io.*;
import java.util.*;

public class Race {

    static FastScanner in;
    static PrintWriter out;

    public static void main(String[] args) {
        setup("race.in", "race.out");

        int n = in.nextInt();
        Map<String, List<Racer>> racers = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Racer racer = new Racer(in.next(), in.next(), i);
            List<Racer> countryRacers = racers
                    .computeIfAbsent(racer.country, t -> new ArrayList<>());
            countryRacers.add(racer);
        }
        List<String> countries = new ArrayList<>(racers.keySet());
        qsort(countries, 0, countries.size() - 1);

        for (String key : countries) {
            List<Racer> countryRacers = racers.get(key);
            out.append("=== " + key + " ===\n");
            for (int i = 0; i < countryRacers.size(); i++) {
                out.append(countryRacers.get(i).name + "\n");
            }
        }

        out.close();
    }

    static Random rand = new Random();
    static void qsort(List<String> a, int l, int r){
        int k = ((r - l) <= 0 ? 0 : rand.nextInt(r - l)) + l;
        String key = a.get(k);
        int i = l;
        int j = r;
        while(i <= j){
            while(a.get(i).compareTo(key) < 0)
                i++;
            while(key.compareTo(a.get(j)) < 0)
                j--;

            if(i <= j){
                String t = a.get(i);
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

    static class Racer{
        final String country;
        final String name;
        final int place;

        public Racer(String country, String name, int place) {
            this.country = country;
            this.name = name;
            this.place = place;
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
