package EA;

import EA.project2.LOLZGenoPhenom;
import EA.generic.AdultSelection;
import EA.generic.EA;
import EA.generic.GenericGenoPhenom;
import EA.generic.ParentSelection;
import EA.project2.OneMaxGenoPhenom;
import EA.project2.SurprisingSequenceGenoPhenom;
import boids.simulation.objects.Boid;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        //Arguments: ProblemName, AdultSelection [F, O, M], ParentSelection [F, S, T, U], populationSize,
        //crossoverRate, mutationRate, crossoverSplit, (problemSize, (z | S, (local))
        AdultSelection as = parseAdultSelection(args[1]);
        ParentSelection ps = parseParentSelection(args[2]);
        int populationSize = Integer.parseInt(args[3]), probSize = Integer.parseInt(args[7]);
        double crossoverRate = Double.parseDouble(args[4]), mutationRate = Double.parseDouble(args[5]), crossoverSplit = Double.parseDouble(args[6]);

        switch (args[0]) {
            //1, 1, 0.3
            case "OneMax":
                int results[] = new int[100];
                for(int i=0; i<100; i++) {
                    results[i] = runOneMaxProblem(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, probSize);
                }
                System.out.println(Arrays.toString(results));
                System.out.println(Arrays.stream(results).average());
                break;

            case "LOLZ":
                runLOLZProblem(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, probSize, Integer.parseInt(args[8]));
                break;

            case "SupSeq":
                runSupSeqProblem(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, probSize, Integer.parseInt(args[8]), Boolean.parseBoolean(args[9]));
                break;
        }
    }


    public static int runOneMaxProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, double xSplit, int probSize) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();
        long max = (1L<<probSize) - 1;

        for(int i=0; i<popSize; i++)
            init.add(new OneMaxGenoPhenom((long) (Math.random()*max), probSize));

        EA ea = new EA(as, ps, popSize, xRate, mutRate, xSplit, init);

        while(true) {
            ea.runGeneration();
            //System.out.println(ea);

            if(ea.getBestNode().fitnessEvaluation() == probSize) return ea.getGeneration();
        }
    }


    public static void runLOLZProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, double xSplit, int probSize, int z) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();
        long max = (1L<<probSize) - 1;

        for(int i=0; i<popSize; i++) {
            init.add(new LOLZGenoPhenom((long) (Math.random()*max), probSize, z));
        }

        EA ea = new EA(as, ps, popSize, xRate, mutRate, xSplit, init);

        while(true) {
            ea.runGeneration();
            System.out.println(ea);

            if(ea.getBestNode().fitnessEvaluation() == probSize) break;
        }
    }


    public static void runSupSeqProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, double xSplit, int probSize, int S, boolean local) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();

        for(int i=0; i<popSize; i++) {
            Integer[] out = new Integer[probSize];
            for(int j=0; j<probSize; j++)
                out[j] = (int) (Math.random()*S);

            init.add(new SurprisingSequenceGenoPhenom(out, S, local));
        }

        EA ea = new EA(as, ps, popSize, xRate, mutRate, xSplit, init);

        while(true) {
            ea.runGeneration();
            System.out.println(ea);

            if(ea.getBestNode().fitnessEvaluation() == 1) break;
        }
    }


    private static AdultSelection parseAdultSelection(String s) {
        switch (s) {
            case "F":
                return AdultSelection.FULL;

            case "O":
                return AdultSelection.OVER_PRODUCTION;

            case "M":
                return AdultSelection.MIXING;

            default:
                return AdultSelection.MIXING;
        }
    }

    private static ParentSelection parseParentSelection(String s) {
        switch (s) {
            case "F":
                return ParentSelection.FITNESS_PROPORTIONATE;

            case "S":
                return ParentSelection.SIGMA_SCALING;

            case "T":
                return ParentSelection.TOURNAMENT;

            case "U":
                return ParentSelection.UNIFORM;

            default:
                return ParentSelection.UNIFORM;
        }
    }
}
