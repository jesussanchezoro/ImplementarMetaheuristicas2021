package mdp.algorithms;

import mdp.constructives.Constructive;
import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;

public class AlgConstructive implements Algorithm {

    private final Constructive constructive;
    private final int nConstructions;

    public AlgConstructive(Constructive constructive, int nConstructions) {
        this.constructive = constructive;
        this.nConstructions = nConstructions;
    }

    @Override
    public MDPSolution execute(MDPInstance instance) {
        MDPSolution best = null;
        for (int i = 0; i < nConstructions; i++) {
            MDPSolution sol = constructive.construct(instance);
            if (best == null || best.getOf() < sol.getOf()) {
                best = sol;
            }
        }
        return best;
    }
}
