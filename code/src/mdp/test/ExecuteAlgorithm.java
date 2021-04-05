package mdp.test;

import mdp.algorithms.AlgConstructive;
import mdp.algorithms.AlgConstructiveLS;
import mdp.algorithms.AlgConstructiveLSParallel;
import mdp.algorithms.Algorithm;
import mdp.constructives.Constructive;
import mdp.constructives.GRASP;
import mdp.constructives.GRASPEfficient;
import mdp.constructives.Greedy;
import mdp.localsearch.LSSwapsFI;
import mdp.localsearch.LSSwapsFIEfficient;
import mdp.localsearch.LocalSearch;
import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;
import mdp.utils.RandomManager;

import java.util.Random;

public class ExecuteAlgorithm {

    public static void main(String[] args) {
        RandomManager.setSeed(13);
        // Instance
        String path = "instances/GKD-b_27_n100_m30.txt";
        MDPInstance instance = new MDPInstance(path);
        // Parameters
        int nConstructions = 10;
        // Constructives
        Constructive greedy = new Greedy();
        Constructive grasp = new GRASP(-1);
        Constructive graspEfficient = new GRASPEfficient(-1);
        // Local search
        LocalSearch ls = new LSSwapsFI();
        LocalSearch lsEff = new LSSwapsFIEfficient();
        // Algorithms
        Algorithm algConst = new AlgConstructive(graspEfficient, nConstructions);
        Algorithm algConstLS = new AlgConstructiveLS(graspEfficient, lsEff, nConstructions);
        Algorithm algConstLSParallel = new AlgConstructiveLSParallel(graspEfficient, lsEff, nConstructions);
        long tIni = System.currentTimeMillis();
        MDPSolution sol = algConstLS.execute(instance);
        long tFin = System.currentTimeMillis();
        double secs = (tFin - tIni) / 1000.0;
        System.out.println(sol);
        System.out.println("TIME: "+secs);
    }
}
