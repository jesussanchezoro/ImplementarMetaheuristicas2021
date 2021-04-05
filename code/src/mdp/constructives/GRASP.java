package mdp.constructives;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;
import mdp.utils.RandomManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GRASP implements Constructive {

    private float alpha;

    public GRASP(float alpha) {
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
        List<Integer> cl = createCL(instance, first);
        for (int i = 0; i < k; i++) {
            List<Integer> rcl = createRCL(sol, cl, realAlpha);
            int next = rcl.remove(rnd.nextInt(rcl.size()));
            sol.add(next);
            cl.remove(Integer.valueOf(next));
        }
        return sol;
    }

    private float greedyFunction(MDPSolution sol, int c) {
        sol.add(c);
        float g = sol.getOf();
        sol.remove(c);
        return g;
    }

    private List<Integer> createCL(MDPInstance instance, int first) {
        int n = instance.getN();
        List<Integer> cl = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (i != first) {
                cl.add(i);
            }
        }
        return cl;
    }

    private List<Integer> createRCL(MDPSolution sol, List<Integer> cl, float alpha) {
        float gMin = 0x3f3f3f;
        float gMax = 0;
        List<Integer> rcl = new ArrayList<>();
        for (int c : cl) {
            float g = greedyFunction(sol, c);
            gMin = Math.min(gMin, g);
            gMax = Math.max(gMax, g);
        }
        float th = gMax - alpha * (gMax - gMin);
        for (int c : cl) {
            float g = greedyFunction(sol, c);
            if (Float.compare(g, th) >= 0) {
                rcl.add(c);
            }
        }
        return rcl;
    }
}
