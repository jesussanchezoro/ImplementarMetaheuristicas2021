package mdp.localsearch;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;
import mdp.utils.RandomManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LSSwapsFI implements LocalSearch {


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
        float prevOf = sol.getOf();
        for (int i = 0; i < selected.size(); i++) {
            int s = selected.get(i);
            sol.remove(s);
            for (int j = 0; j < nonSelected.size(); j++) {
                int ns = nonSelected.get(j);
                sol.add(ns);
                if (Float.compare(sol.getOf(), prevOf) > 0) {
                    selected.remove(i);
                    nonSelected.remove(j);
                    selected.add(ns);
                    nonSelected.add(s);
                    return true;
                }
                sol.remove(ns);
            }
            sol.add(s);
        }
        return false;
    }
}
