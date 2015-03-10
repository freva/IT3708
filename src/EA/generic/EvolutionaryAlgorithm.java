package EA.generic;

import EA.generic.selection.AdultSelection;
import EA.generic.selection.ParentSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EvolutionaryAlgorithm {
    private List<GenericGenoPhenom> adults = new ArrayList<>(), children;
    private int populationSize, generation = 1;
    private double crossoverRate, mutationRate, crossoverSplit;
    private AdultSelection adultSelection;
    private ParentSelection parentSelection;

    public EvolutionaryAlgorithm(AdultSelection adultSelection, ParentSelection parentSelection, int populationSize,
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
        adults = adultSelection.selectSurvivingAdults(adults, children, populationSize);
        generateTheNextGeneration();

        generation++;
    }


    private void generateTheNextGeneration() {
        children = new ArrayList<>();

        int numToGenerate = (adultSelection == AdultSelection.OVER_PRODUCTION) ? adults.size()*2 : adults.size();
        int numCrossoverSplit = (int) (numToGenerate*crossoverSplit);
        GenericGenoPhenom[] allowedToMate = parentSelection.selectParents(adults, numToGenerate);

        for(int i=0; i<numCrossoverSplit; i+=2) {
            if(Math.random() < crossoverRate) {
                children.add(allowedToMate[i].crossover(allowedToMate[i + 1]));
                children.add(allowedToMate[i + 1].crossover(allowedToMate[i]));
            } else {
                children.add(allowedToMate[i]);
                children.add(allowedToMate[i+1]);
            }
        }

        for(int i=numCrossoverSplit+1; i<numToGenerate; i++) {
            if(Math.random() < mutationRate) children.add(allowedToMate[i].mutate());
            else children.add(allowedToMate[i]);
        }
    }


    public String toString() {
        GenericGenoPhenom max = getBestNode();

        double avg = adults.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).average().getAsDouble();
        double squaredDifferences = adults.parallelStream().mapToDouble(GenericGenoPhenom::fitnessEvaluation).map(x-> Math.pow(x-avg, 2)).sum();
        double std = Math.sqrt(squaredDifferences/(adults.size() - 1));

        String out = "Gen: " + generation + " | Avg: " + String.format("%.2f", avg) + " | Std: " + String.format("%.2f", std);
        out += " | Max: " + String.format("%.2f", max.fitnessEvaluation()) + "\n" + max + "\n";
        return out;
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
