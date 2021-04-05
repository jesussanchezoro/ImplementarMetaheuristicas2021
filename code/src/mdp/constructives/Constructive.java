package mdp.constructives;

import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;

public interface Constructive {

    public MDPSolution construct(MDPInstance instance);
}
