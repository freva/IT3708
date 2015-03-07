package EA;

import EA.generic.AdultSelection;
import EA.generic.EA;
import EA.generic.GenericGenoPhenom;
import EA.generic.ParentSelection;
import EA.project2.OneMax.GenoPhenom;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();
        long max = (1<<20) - 1;

        for(int i=1; i<11; i++) {
            init.add(new GenoPhenom((long) (Math.random()*max), 20));
        }

        EA ea = new EA(AdultSelection.MIXING, ParentSelection.TOURNAMENT, 20, 0.3, 0.4, init);
        boolean isFinished = false;

        while(!isFinished) {
            isFinished = ea.runGeneration();
            System.out.println(ea);
        }
    }
}
