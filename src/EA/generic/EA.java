package EA.generic;

import java.util.ArrayList;
import java.util.Collections;

public class EA {
    private ArrayList<GenericGenoPhenom> adults, children;
    private int populationSize;
    private double crossoverRate, mutationRate;
    private AdultSelection adultSelection;
    private ParentSelection parentSelection;

    public EA(AdultSelection adultSelection, ParentSelection parentSelection, int populationSize,
              double crossoverRate, double mutationRate, ArrayList<GenericGenoPhenom> children) {
        this.adultSelection = adultSelection;
        this.parentSelection = parentSelection;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.children = children;
    }


    public void runGeneration() {
        selectAdults(adultSelection);
        children = generateTheNextGeneration(parentSelection);
    }


    public ArrayList<GenericGenoPhenom> generateTheNextGeneration(ParentSelection pa) {
        ArrayList<GenericGenoPhenom> children = new ArrayList<>();

        return children;
    }


    public void selectAdults(AdultSelection as) {
        switch (as) {
            case FULL:
                adults = children;
                break;

            case OVER_PRODUCTION:
                Collections.sort(children);
                adults = (ArrayList<GenericGenoPhenom>) children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;

            case MIXING:
                children.addAll(adults);
                Collections.sort(children);
                adults = (ArrayList<GenericGenoPhenom>) children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;
        }

    }
}
