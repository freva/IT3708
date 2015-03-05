package EA.generic;


import java.util.ArrayList;
import java.util.Collections;

public class EA {
    private ArrayList<Genotype> population = new ArrayList<>();
    private int populationSize = 0;


    public void runGeneration() {

    }


    public void selectAdults(AdultSelection as, ArrayList<Genotype> children) {
        switch (as) {
            case FULL:
                population = children;
                break;

            case OVER_PRODUCTION:
                Collections.sort(children);
                population = (ArrayList<Genotype>) children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;

            case MIXING:
                children.addAll(population);
                Collections.sort(children);
                population = (ArrayList<Genotype>) children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;
        }

    }
}
