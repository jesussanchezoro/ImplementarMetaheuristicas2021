package mdp.algorithms;

import mdp.constructives.Constructive;
import mdp.localsearch.LocalSearch;
import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;

public class AlgConstructiveLS implements Algorithm {

    private final Constructive constructive;
    private final LocalSearch ls;
    private final int nConstructions;

    public AlgConstructiveLS(Constructive constructive, LocalSearch ls, int nConstructions) {
        this.constructive = constructive;
        this.nConstructions = nConstructions;
        this.ls = ls;
    }

    @Override
    public MDPSolution execute(MDPInstance instance) {
        MDPSolution best = null;
        for (int i = 0; i < nConstructions; i++) {
            MDPSolution sol = constructive.construct(instance);
//            System.out.print(sol.getOf()+"\t");
            ls.improve(sol);
//            System.out.print(sol.getOf()+"\t");
            if (best == null || best.getOf() < sol.getOf()) {
                best = sol;
            }
//            System.out.println(best.getOf());
        }
        return best;
    }
}
