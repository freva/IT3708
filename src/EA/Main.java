package EA;

import EA.generic.AdultSelection;
import EA.generic.EA;
import EA.generic.GenericGenoPhenom;
import EA.generic.ParentSelection;
import EA.project2.OneMax.GenoPhenom;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        //Arguments: ProblemName, AdultSelection [F, O, M], ParentSelection [F, S, T, U], populationSize, crossoverRate, mutationRate, (problemSize, (z))
        AdultSelection as = parseAdultSelection(args[1]);
        ParentSelection ps = parseParentSelection(args[2]);
        int populationSize = Integer.parseInt(args[3]);
        double crossoverRate = Double.parseDouble(args[4]), mutationRate = Double.parseDouble(args[5]);


        switch (args[0]) {
            case "OneMax":
                runOneMaxProblem(as, ps, populationSize, crossoverRate, mutationRate, Integer.parseInt(args[6]));
                break;
        }
    }


    public static void runOneMaxProblem(AdultSelection as, ParentSelection ps, int popSize, double xRate, double mutRate, int probSize) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();
        long max = (1<<probSize) - 1;

        for(int i=1; i<11; i++) {
            init.add(new GenoPhenom((long) (Math.random()*max), probSize));
        }

        EA ea = new EA(as, ps, popSize, xRate, mutRate, init);

        while(true) {
            ea.runGeneration();
            System.out.println(ea);

            if(ea.getBestNode().fitnessEvaluation() == probSize) break;
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
