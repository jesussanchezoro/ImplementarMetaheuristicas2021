package mdp.structure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MDPInstance {

    private String name;
    private int n;
    private int k;
    private float[][] distances;

    public MDPInstance(String path) {
        readInstance(path);
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public String getName() {
        return name;
    }

    public float distance(int u, int v) {
        return distances[u][v];
    }

    private void readInstance(String path) {
        this.name = path.substring(path.lastIndexOf('/'+1)).replace(".txt","");
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String[] tokens = bf.readLine().split("\\s+");
            this.n = Integer.parseInt(tokens[0]);
            this.k = Integer.parseInt(tokens[1]);
            distances = new float[n][n];
            String line = null;
            while ((line = bf.readLine()) != null) {
                tokens = line.split("\\s+");
                int v = Integer.parseInt(tokens[0]);
                int u = Integer.parseInt(tokens[1]);
                float d = Float.parseFloat(tokens[2]);
                distances[u][v] = d;
                distances[v][u] = d;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
