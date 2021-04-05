package mdp.localsearch;

import mdp.structure.MDPSolution;

public interface LocalSearch {

    public void improve(MDPSolution sol);
}
