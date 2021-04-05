package mdp.constructives;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;
import mdp.utils.RandomManager;

import java.util.ArrayList;
import java.util.List;

public class Greedy implements Constructive {

    @Override
    public MDPSolution construct(MDPInstance instance) {
        MDPSolution sol = new MDPSolution(instance);
        int k = instance.getK();
        int first = RandomManager.getRandom().nextInt(instance.getN());
        sol.add(first);
        List<Integer> cl = createCL(instance, first);
        for (int i = 0; i < k; i++) {
            int nextId = selectBest(sol, cl);
            int next = cl.remove(nextId);
            sol.add(next);
        }
        return sol;
    }

    private int selectBest(MDPSolution sol, List<Integer> cl) {
        int bestC = -1;
        float bestG = 0;
        for (int i = 0; i < cl.size(); i++) {
            float g = greedyFunction(sol, cl.get(i));
            if (g > bestG) {
                bestC = i;
                bestG = g;
            }
        }
        return bestC;
    }

    private float greedyFunction(MDPSolution sol, int c) {
        float g = 0;
        MDPInstance instance = sol.getInstance();
        for (int s : sol.getSelected()) {
            g += instance.distance(c,s);
        }
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

}
