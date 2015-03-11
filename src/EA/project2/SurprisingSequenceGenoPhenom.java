package EA.project2;

import EA.generic.GenericGenoPhenom;

import java.util.Arrays;
import java.util.HashSet;

public class SurprisingSequenceGenoPhenom extends GenericGenoPhenom<Integer[], Character[]> {
    private int S;
    private double fitness = -1;
    private boolean local;

    public SurprisingSequenceGenoPhenom(Integer[] geno, int S, boolean local) {
        super(geno);
        this.S = S;
        this.local = local;
    }

    @Override
    public Character[] developmentalMethod() {
        Character out[] = new Character[getGeno().length];
        for(int i=0; i<getGeno().length; i++)
            out[i] = (char) (((getGeno()[i] > 25) ? 70: 65) + getGeno()[i]);

        return out;
    }

    @Override
    public GenericGenoPhenom<Integer[], Character[]> crossover(GenericGenoPhenom<Integer[], Character[]> other) {
        Integer[] out = new Integer[getGeno().length];
        int crossoverPoint = (int) (Math.random()*getGeno().length);

        System.arraycopy(getGeno(), 0, out, 0, crossoverPoint);
        System.arraycopy(other.getGeno(), crossoverPoint, out, crossoverPoint, getGeno().length - crossoverPoint);

        return new SurprisingSequenceGenoPhenom(out, S, local);
    }

    @Override
    public GenericGenoPhenom<Integer[], Character[]> mutate() {
        Integer[] out = new Integer[getGeno().length];
        double chance = 1d/200;

        for(int i=0; i<getGeno().length; i++) {
            if(Math.random() > chance) out[i] = getGeno()[i];
            else out[i] = (int) (Math.random()*S);
        }

        return new SurprisingSequenceGenoPhenom(out, S, local);
    }

    @Override
    public double fitnessEvaluation() {
        if(fitness == -1) {
            char out[] = new char[getGeno().length];
            for (int i = 0; i < getGeno().length; i++)
                out[i] = getPhenom()[i];
            int totPos = local ? Math.floorDiv((int) Math.pow(out.length-1, 2), 4) : Math.floorDiv((out.length-1)*(out.length+1)*(2*out.length-3), 24);
            fitness = 1 - (local ? getNumOfLocalSequenceViolations(out) : getNumOfGlobalSequenceViolations(out)) / totPos;
        }

        return fitness;
    }


    public static double getNumOfGlobalSequenceViolations(char[] out) {
        HashSet<String> sequences = new HashSet<>();

        int counter = 0;
        for(int start=0; start<out.length; start++) {
            for(int length=1; start+length <= out.length; length++) {
                for(int dist=0; start+dist+length*2 <= out.length; dist++) {
                    String cand = new String(out, start, length) + dist + new String(out, start+length+dist, length);
                    if (sequences.contains(cand)) counter++;
                    else sequences.add(cand);
                }
            }
        }

        return counter;
    }


    public static double getNumOfLocalSequenceViolations(char[] out) {
        HashSet<String> sequences = new HashSet<>();

        int counter = 0;
        for(int start=0; start<out.length; start++) {
            for(int length=2; start+length <= out.length; length+=2) {
                String cand = new String(out, start, length);
                if(sequences.contains(cand)) counter++;
                else sequences.add(cand);
            }
        }

        return counter;
    }


    public String toString() {
        return Arrays.toString(getPhenom());
    }
}
