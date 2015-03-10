package EA.generic.selection;


import EA.generic.GenericGenoPhenom;

import java.util.List;
import java.util.Collections;

public enum AdultSelection {
    FULL, OVER_PRODUCTION, MIXING;


    public List<GenericGenoPhenom> selectSurvivingAdults(List<GenericGenoPhenom> adults, List<GenericGenoPhenom> children, int populationSize) {
        switch (this) {
            default:
            case FULL:
                return children;

            case OVER_PRODUCTION:
                children.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
                Collections.sort(children);
                return children.subList(Math.max(children.size() - populationSize, 0), children.size());

            case MIXING:
                children.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
                children.addAll(adults);
                Collections.sort(children);
                return children.subList(Math.max(children.size() - populationSize, 0), children.size());
        }
    }
}
