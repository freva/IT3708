package EA.generic;


import java.util.ArrayList;
import java.util.Collections;

public class EA {
    private ArrayList<GenomPhenom> population = new ArrayList<>();
    private int populationSize;
    private double crossoverRate, mutationRate;

    public EA(int populationSize, double crossoverRate, double mutationRate) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }


    public void runGeneration() {

    }


    public void selectAdults(AdultSelection as, ArrayList<GenomPhenom> children) {
        switch (as) {
            case FULL:
                population = children;
                break;

            case OVER_PRODUCTION:
                Collections.sort(children);
                population = (ArrayList<GenomPhenom>) children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;

            case MIXING:
                children.addAll(population);
                Collections.sort(children);
                population = (ArrayList<GenomPhenom>) children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;
        }

    }
}
