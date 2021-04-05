package mdp.constructives;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;
import mdp.utils.RandomManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GRASPEfficient implements Constructive {

    private class Candidate {
        int v;
        float g;

        public Candidate(int v, float g) {
            this.v = v;
            this.g = g;
        }
    }

    private float alpha;

    public GRASPEfficient(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public MDPSolution construct(MDPInstance instance) {
        Random rnd = RandomManager.getRandom();
        float realAlpha = (alpha < 0) ? rnd.nextFloat() : alpha;
        MDPSolution sol = new MDPSolution(instance);
        int k = instance.getK();
        int first = RandomManager.getRandom().nextInt(instance.getN());
        sol.add(first);
        int n = instance.getN();
        float gMin = 0x3f3f3f;
        float gMax = 0;
        List<Candidate> cl = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (i != first) {
                float g = greedyFunction(sol, i);
                cl.add(new Candidate(i, g));
                gMin = Math.min(gMin, g);
                gMax = Math.max(gMax, g);
            }
        }
        float th = gMax - realAlpha * (gMax - gMin);
        int[] rcl = new int[instance.getN()];
        int rclLimit = 0;
        for (int i = 0; i < k; i++) {
            rclLimit = 0;
            for (int j = 0; j < cl.size(); j++) {
                if (Float.compare(cl.get(j).g, th) >= 0) {
                    rcl[rclLimit] = j;
                    rclLimit++;
                }
            }
            int nextId = rcl[rnd.nextInt(rclLimit)];
            int next = cl.remove(nextId).v;
            sol.add(next);
            th = evaluateCL(sol, cl, realAlpha);
        }
        return sol;
    }

    private float greedyFunction(MDPSolution sol, int c) {
        float g = 0;
        MDPInstance instance = sol.getInstance();
        for (int s : sol.getSelected()) {
            g += instance.distance(c,s);
        }
        return g;
    }

    private float evaluateCL(MDPSolution sol, List<Candidate> cl, float alpha) {
        float gMin = 0x3f3f3f;
        float gMax = 0;
        for (Candidate c : cl) {
            float g = greedyFunction(sol, c.v);
            c.g = g;
            gMin = Math.min(gMin, g);
            gMax = Math.max(gMax, g);
        }
        float th = gMax - alpha * (gMax - gMin);
        return th;
    }
}
