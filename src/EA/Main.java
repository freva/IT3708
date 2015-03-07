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

        for(int i=1; i<11; i++) {
            init.add(new GenoPhenom((1L<<i) - 1, 20));
        }

        EA ea = new EA(AdultSelection.MIXING, ParentSelection.FITNESS_PROPORTIONATE, 20, 0.3, 0.4, init);

        for(int i=0; i<200; i++) {
            ea.runGeneration();
            System.out.println(ea);
        }
    }
}
