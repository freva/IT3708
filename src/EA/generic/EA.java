package EA.generic;

import java.util.ArrayList;
import java.util.Collections;


public class EA {
    private static final double tournamentEpsilon = 0.1;
    private static final int tournamentK = 5;

    private ArrayList<GenericGenoPhenom> adults, children;
    private int populationSize, generation = 1;
    private double crossoverRate, mutationRate;
    private AdultSelection adultSelection;
    private ParentSelection parentSelection;

    public EA(AdultSelection adultSelection, ParentSelection parentSelection, int populationSize,
              double crossoverRate, double mutationRate, ArrayList<GenericGenoPhenom> children) {
        this.adultSelection = adultSelection;
        this.parentSelection = parentSelection;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate/2;
        this.mutationRate = mutationRate;
        this.children = children;
    }


    public void runGeneration() {
        selectSurvivingAdults();
        generateTheNextGeneration();

        generation++;
    }


    private void generateTheNextGeneration() {
        children = new ArrayList<>();

        int numToCrossover = 2 * (int) (adults.size()*crossoverRate);
        ArrayList<GenericGenoPhenom> toCrossover = selectParents(parentSelection, adults, numToCrossover);
        for(int i=0; i<numToCrossover; i+=2) {
            children.add(toCrossover.get(i).crossover(toCrossover.get(i+1)));
            children.add(toCrossover.get(i+1).crossover(toCrossover.get(i)));
        }

        int numToMutate = (int) (adults.size()*mutationRate);
        ArrayList<GenericGenoPhenom> toMutate = selectParents(parentSelection, adults, numToMutate);
        for(int i=0; i<numToMutate; i++) {
            children.add(toMutate.get(i).mutate());
        }
    }


    private void selectSurvivingAdults() {
        switch (adultSelection) {
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


    public static ArrayList<GenericGenoPhenom> selectParents(ParentSelection ps, ArrayList<GenericGenoPhenom> candidates, int num) {
        ArrayList<GenericGenoPhenom> parents = new ArrayList<>();

        switch (ps) {
            case FITNESS_PROPORTIONATE:
            case SIGMA_SCALING:
            case UNIFORM:
                double cdf[] = generateCumulativeProbabilityDistribution(ps, candidates);

                for(int i=0; i<num; i++)
                    parents.add(candidates.get(selectRandomFromCDF(cdf)));
            break;

            case TOURNAMENT:
                for(int i=0; i<num; i++) {
                    ArrayList<GenericGenoPhenom> subSet = getSubSet(candidates, tournamentK);

                    if(Math.random() > 1-tournamentEpsilon) parents.add(Collections.max(subSet));
                    else parents.add(subSet.get((int) (Math.random()*subSet.size())));
                }
            break;
        }


        return parents;
    }


    private static double[] generateCumulativeProbabilityDistribution(ParentSelection ps, ArrayList<GenericGenoPhenom> parents) {
        double[] cdf = new double[parents.size()];

        switch (ps) {
            case FITNESS_PROPORTIONATE:
                double sum = parents.stream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).sum();

                for(int i=0; i<parents.size(); i++)
                    cdf[i] = parents.get(i).fitnessEvaluation() / sum;
            break;

            case SIGMA_SCALING:
                double avg = parents.stream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
                double squaredDifferences = parents.stream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).map(x-> Math.pow(x-avg, 2)).sum();
                double std = Math.sqrt(squaredDifferences/(parents.size() - 1));

                for(int i=0; i<parents.size(); i++)
                    cdf[i] = (1 + (parents.get(i).fitnessEvaluation() - avg)/(2 * std)) / parents.size();
            break;

            case UNIFORM:
            default:
                for(int i=0; i<parents.size(); i++)
                    cdf[i] = 1.0 / parents.size();
                break;
        }

        for(int i=1; i<cdf.length; i++)
            cdf[i] += cdf[i-1];

        return cdf;
    }


    private static int selectRandomFromCDF(double[] cdf) {
        double target = Math.random();

        for(int i=0; i<cdf.length; i++)
            if(cdf[i] < target) return i;

        return cdf.length-1;
    }


    private static <T> ArrayList<T> getSubSet(ArrayList<T> fullArray, int size) {
        ArrayList<T> array = new ArrayList<>();

        for (int i = 0; i < size; i++)
            array.add(fullArray.get((int) (Math.random()*fullArray.size())));

        return array;
    }
}
