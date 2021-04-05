package performance;

import java.util.*;

public class SearchDataStructure {

    final static int N = 100000;
    final static int NOP = 100000;

    public static void main(String[] args) {
        Random rnd = new Random(13);

        List<Integer> ds = new ArrayList<>(N);
//        List<Integer> ds = new LinkedList<>();
//        Set<Integer> ds = new HashSet<>(N);
//        int[] ds = new int[N];

        // Fill DS
        for (int i = 0; i < N; i++) {
            ds.add(i+1);
//            ds[i] = i;
        }

        // Search in DS
        long tIni = System.currentTimeMillis();
        for (int i = 0; i < NOP; i++) {
            int elemSearch = rnd.nextInt(N);
            ds.contains(elemSearch);
//            contains(ds, elemSearch);
        }
        long tFin = System.currentTimeMillis();
        double secs = (tFin - tIni) / 1000.0;

        System.out.println(secs);

    }

    public static boolean contains(int[] ds, int elemSearch) {
        for (int d : ds) {
            if (d == elemSearch) {
                return true;
            }
        }
        return false;
    }
}
