package EA.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EA {
    private static final double tournamentEpsilon = 0.15;
    private static final int tournamentK = 5;

    private List<GenericGenoPhenom> adults = new ArrayList<>(), children;
    private int populationSize, generation = 1;
    private double crossoverRate, mutationRate, crossoverSplit;
    private AdultSelection adultSelection;
    private ParentSelection parentSelection;

    public EA(AdultSelection adultSelection, ParentSelection parentSelection, int populationSize,
              double crossoverRate, double mutationRate, double crossoverSplit, ArrayList<GenericGenoPhenom> children) {
        this.adultSelection = adultSelection;
        this.parentSelection = parentSelection;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate/2;
        this.mutationRate = mutationRate;
        this.crossoverSplit = crossoverSplit;
        this.children = children;
    }


    public void runGeneration() {
        selectSurvivingAdults();
        generateTheNextGeneration();

        generation++;
    }


    private void generateTheNextGeneration() {
        children = new ArrayList<>();

        int numCrossoverSplit = (int) (adults.size()*crossoverSplit);
        GenericGenoPhenom[] allowedToMate = selectParents(parentSelection, adults);

        for(int i=0; i<numCrossoverSplit; i+=2) {
            if(Math.random() < crossoverRate) {
                children.add(allowedToMate[i].crossover(allowedToMate[i + 1]));
                children.add(allowedToMate[i + 1].crossover(allowedToMate[i]));
            } else {
                children.add(allowedToMate[i]);
                children.add(allowedToMate[i+1]);
            }
        }

        for(int i=numCrossoverSplit+1; i<adults.size(); i++) {
            if(Math.random() < mutationRate) children.add(allowedToMate[i].mutate());
            else children.add(allowedToMate[i]);
        }
    }


    private void selectSurvivingAdults() {
        switch (adultSelection) {
            case FULL:
                adults = children;
                break;

            case OVER_PRODUCTION:
                children.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
                Collections.sort(children);
                adults = children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;

            case MIXING:
                children.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
                children.addAll(adults);
                Collections.sort(children);
                adults = children.subList(Math.max(children.size() - populationSize, 0), children.size());
                break;
        }
    }


    public String toString() {
        GenericGenoPhenom min = getWorstNode();
        GenericGenoPhenom max = getBestNode();

        String out = "=== Generation: " + generation + " ===\n";
        out += "Min: " + min + " | " + min.fitnessEvaluation() + "\n";
        out += "Max: " + max + " | " + max.fitnessEvaluation() + "\n";
        out += "Avg: " + adults.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble() + "\n\n";
        return out;
    }


    public static GenericGenoPhenom[] selectParents(ParentSelection ps, List<GenericGenoPhenom> candidates) {
        GenericGenoPhenom[] parents = new GenericGenoPhenom[candidates.size()];

        switch (ps) {
            case FITNESS_PROPORTIONATE:
            case SIGMA_SCALING:
            case UNIFORM:
                double cdf[] = generateCumulativeProbabilityDistribution(ps, candidates);

                for(int i=0; i<candidates.size(); i++)
                    parents[i] = candidates.get(selectRandomFromCDF(cdf));
            break;

            case TOURNAMENT:
                for(int i=0; i<candidates.size(); i++) {
                    List<GenericGenoPhenom> subSet = getSubSet(candidates, tournamentK);

                    if(Math.random() < 1-tournamentEpsilon) parents[i] = Collections.max(subSet);
                    else parents[i] = subSet.get((int) (Math.random() * subSet.size()));
                }
            break;
        }

        return parents;
    }


    private static double[] generateCumulativeProbabilityDistribution(ParentSelection ps, List<GenericGenoPhenom> parents) {
        double[] cdf = new double[parents.size()];

        switch (ps) {
            case FITNESS_PROPORTIONATE:
                double sum = parents.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).sum();

                for(int i=0; i<parents.size(); i++)
                    cdf[i] = parents.get(i).fitnessEvaluation() / sum;
            break;

            case SIGMA_SCALING:
                double avg = parents.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
                double squaredDifferences = parents.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).map(x-> Math.pow(x-avg, 2)).sum();
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
            if(cdf[i] > target) return i;

        return cdf.length-1;
    }


    private static <T> List<T> getSubSet(List<T> fullArray, int size) {
        List<T> array = new ArrayList<>();

        for (int i = 0; i < size; i++)
            array.add(fullArray.get((int) (Math.random() * fullArray.size())));

        return array;
    }


    public GenericGenoPhenom getBestNode() {
        return Collections.max(adults);
    }

    public GenericGenoPhenom getWorstNode() {
        return Collections.min(adults);
    }

    public int getGeneration() {
        return generation;
    }
}
