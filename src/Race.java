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
        countries.sort(Comparator.naturalOrder());

        for (String key : countries) {
            List<Racer> countryRacers = racers.get(key);
            qsort(countryRacers, 0, countryRacers.size() - 1);
            out.append("=== " + key + " ===\n");
            for (int i = 0; i < countryRacers.size(); i++) {
                out.append(countryRacers.get(i).name + "\n");
            }
        }

        out.close();
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

    static int getKey(List<Racer> racers, int i){
        return racers.get(i).place;
    }

    static Random rand = new Random();
    static void qsort(List<Racer> a, int l, int r){
        int k = ((r - l) <= 0 ? 0 : rand.nextInt(r - l)) + l;
        int key = getKey(a, k);
        int i = l;
        int j = r;
        while(i <= j){
            while(getKey(a, i) < key)
                i++;
            while(key < getKey(a, j))
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

}
