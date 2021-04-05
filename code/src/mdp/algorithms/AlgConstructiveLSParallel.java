package mdp.algorithms;

import mdp.constructives.Constructive;
import mdp.localsearch.LocalSearch;
import mdp.structure.MDPInstance;
import mdp.structure.MDPSolution;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AlgConstructiveLSParallel implements Algorithm {

    private final Constructive constructive;
    private final LocalSearch ls;
    private final int nConstructions;

    public AlgConstructiveLSParallel(Constructive constructive, LocalSearch ls, int nConstructions) {
        this.constructive = constructive;
        this.nConstructions = nConstructions;
        this.ls = ls;
    }

    @Override
    public MDPSolution execute(MDPInstance instance) {
        MDPSolution best = new MDPSolution(instance);
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(nConstructions);
        for (int i = 0; i < nConstructions; i++) {
            pool.submit(() -> {
                MDPSolution sol = constructive.construct(instance);
                ls.improve(sol);
                synchronized (best) {
                    if (best.getOf() < sol.getOf()) {
                        best.copy(sol);
                    }
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
            pool.shutdown();
            pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return best;
    }
}
