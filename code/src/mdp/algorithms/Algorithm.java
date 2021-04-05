package mdp.algorithms;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;

public interface Algorithm {

    public MDPSolution execute(MDPInstance instance);
}
