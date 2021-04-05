package mdp.localsearch;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;
import mdp.utils.RandomManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LSSwapsFIEfficient implements LocalSearch {


    @Override
    public void improve(MDPSolution sol) {
        MDPInstance instance = sol.getInstance();
        List<Integer> selected = new ArrayList<>(instance.getK());
        int n = instance.getN();
        List<Integer> nonSelected = new ArrayList<>(instance.getN());
        for (int i = 0; i < n; i++) {
            if (sol.contains(i)) {
                selected.add(i);
            } else {
                nonSelected.add(i);
            }
        }
        Collections.shuffle(selected, RandomManager.getRandom());
        Collections.shuffle(nonSelected, RandomManager.getRandom());
        boolean improve = true;
        while (improve) {
            improve = tryImprove(sol, selected, nonSelected);
        }
    }

    private boolean tryImprove(MDPSolution sol, List<Integer> selected, List<Integer> nonSelected) {
        for (int i = 0; i < selected.size(); i++) {
            int s = selected.get(i);
            float distSel = distanceToSelectedExcept(sol, s, s);
            for (int j = 0; j < nonSelected.size(); j++) {
                int ns = nonSelected.get(j);
                float distNonSel = distanceToSelectedExcept(sol, ns, s);
                if (Float.compare(distNonSel, distSel) > 0) {
                    selected.remove(i);
                    nonSelected.remove(j);
                    selected.add(ns);
                    nonSelected.add(s);
                    sol.remove(s);
                    sol.add(ns);
                    return true;
                }
            }
        }
        return false;
    }

    private float distanceToSelectedExcept(MDPSolution sol, int v, int except) {
        MDPInstance instance = sol.getInstance();
        float d = 0;
        for (int s : sol.getSelected()) {
            if (s != except) {
                d += instance.distance(s, v);
            }
        }
        return d;
    }
}
