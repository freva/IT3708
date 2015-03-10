package EA.generic.selection;

import EA.generic.GenericGenoPhenom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum ParentSelection {
    FITNESS_PROPORTIONATE, SIGMA_SCALING, TOURNAMENT, RANK;


    private static final double tournamentEpsilon = 0.15;
    private static final int tournamentK = 5;

    public GenericGenoPhenom[] selectParents(List<GenericGenoPhenom> candidates, int numToGenerate) {
        GenericGenoPhenom[] parents = new GenericGenoPhenom[numToGenerate];

        switch (this) {
            case FITNESS_PROPORTIONATE:
            case SIGMA_SCALING:
            case RANK:
                double cdf[] = generateCumulativeProbabilityDistribution(this, candidates);

                for(int i=0; i<numToGenerate; i++)
                    parents[i] = candidates.get(selectRandomFromCDF(cdf));
                break;

            case TOURNAMENT:
                for(int i=0; i<numToGenerate; i++) {
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

            case RANK:
                Collections.sort(parents);
                double totSum = parents.size()*(parents.size()+1)/2;
                for(int i=1; i<parents.size()+1; i++)
                    cdf[i-1] = i/totSum;
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
}
