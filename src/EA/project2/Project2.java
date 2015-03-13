package EA.project2;

import EA.generic.selection.AdultSelection;
import EA.generic.EvolutionaryAlgorithm;
import EA.generic.GenericGenoPhenom;
import EA.generic.selection.ParentSelection;

import java.util.ArrayList;


public class Project2 {
    public static void main(String[] args) {
        //Arguments: ProblemName, AdultSelection [F, O, M], ParentSelection [F, S, T, U], populationSize,
        //crossoverRate, mutationRate, crossoverSplit, (problemSize, (z | S, (local))

        AdultSelection as = parseAdultSelection(args[1]);
        ParentSelection ps = parseParentSelection(args[2]);
        int populationSize = Integer.parseInt(args[3]), probSize = Integer.parseInt(args[7]);
        double crossoverRate = Double.parseDouble(args[4]), mutationRate = Double.parseDouble(args[5]), crossoverSplit = Double.parseDouble(args[6]);

        ArrayList<GenericGenoPhenom> init = null;
        switch (args[0]) {
            //800, 0.9, 0.5, 0.5, 40
            case "OneMax":
                init = runOneMaxProblem(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, probSize);
                break;

            case "LOLZ":
                init = runLOLZProblem(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, probSize, Integer.parseInt(args[8]));
                break;

            case "SupSeq":
                init = runSupSeqProblem(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, probSize, Integer.parseInt(args[8]), Boolean.parseBoolean(args[9]));
                break;
        }


        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(as, ps, populationSize, crossoverRate, mutationRate, crossoverSplit, init);
        while (true) {
            ea.runGeneration();
            System.out.println(ea);

            if (ea.getBestNode().fitnessEvaluation() == 1) break;
        }
    }


    public static ArrayList<GenericGenoPhenom> runOneMaxProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, double xSplit, int probSize) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();
        long max = (1L<<probSize) - 1;

        for(int i=0; i<popSize; i++)
            init.add(new OneMaxGenoPhenom((long) (Math.random()*max), probSize));

        return init;
    }


    public static ArrayList<GenericGenoPhenom> runLOLZProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, double xSplit, int probSize, int z) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();
        long max = (1L<<probSize) - 1;

        for(int i=0; i<popSize; i++)
            init.add(new LOLZGenoPhenom((long) (Math.random()*max), probSize, z));

        return init;
    }


    public static ArrayList<GenericGenoPhenom> runSupSeqProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, double xSplit, int probSize, int S, boolean local) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();

        for(int i=0; i<popSize; i++) {
            Integer[] out = new Integer[probSize];
            for(int j=0; j<probSize; j++)
                out[j] = (int) (Math.random()*S);

            init.add(new SurprisingSequenceGenoPhenom(out, S, local));
        }

        return init;
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

            case "R":
                return ParentSelection.RANK;

            default:
                return ParentSelection.RANK;
        }
    }
}
