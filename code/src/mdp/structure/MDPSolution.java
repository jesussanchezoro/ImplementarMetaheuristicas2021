package mdp.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MDPSolution {

    private MDPInstance instance;
    private Set<Integer> selected;
    private float of;
    private boolean isUpdated;

    public MDPSolution(MDPInstance instance) {
        this.instance = instance;
        selected = new HashSet<>(instance.getN());
        isUpdated = false;
    }

    public void copy(MDPSolution sol) {
        this.instance = sol.instance;
        this.selected = new HashSet<>(sol.selected);
        this.of = sol.of;
        this.isUpdated = sol.isUpdated;
    }

    public void add(int v) {
        updateAdd(v);
//        isUpdated = false;
        selected.add(v);
    }

    public void updateAdd(int v) {
        float d = distanceToSelected(v);
        of += d;
        isUpdated = true;
    }

    public void remove(int v) {
        selected.remove(v);
//        isUpdated = false;
        updateRemove(v);
    }

    public void updateRemove(int v) {
        float d = distanceToSelected(v);
        of -= d;
        isUpdated = true;
    }

    public float getOf() {
        if (!isUpdated) {
            evaluate();
            isUpdated = true;
        }
        return of;
    }

    public MDPInstance getInstance() {
        return instance;
    }

    public Iterable<Integer> getSelected() {
        return selected;
    }

    public boolean contains(int v) {
        return selected.contains(v);
    }

    public float distanceToSelected(int v) {
        float d = 0;
        for (int s : selected) {
            d += instance.distance(v, s);
        }
        return d;
    }

    public void evaluate() {
        of = 0;
        for (int s1 : selected) {
            for (int s2 : selected) {
                if (s1 != s2) {
                    of += instance.distance(s1, s2);
                }
            }
        }
        of /= 2.0;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("SELECTED: ").append(selected.toString()).append("\n");
        stb.append("OF: ").append(getOf());
        return stb.toString();
    }
}
