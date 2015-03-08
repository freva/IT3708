package EA.project2.OneMax;

import EA.generic.GenericGenoPhenom;

public class GenoPhenom extends GenericGenoPhenom<Long, Long> {
    private int problemSize;

    public GenoPhenom(Long geno, int problemSize) {
        super(geno);
        this.problemSize = problemSize;
    }

    @Override
    public Long developmentalMethod() {
        return getGeno();
    }

    @Override
    public GenericGenoPhenom<Long, Long> crossover(GenericGenoPhenom<Long, Long> other) {
        int crossoverPoint = (int) (Math.random()*problemSize);
        long crossoverBitMask = (1<<crossoverPoint) - 1;

        return new GenoPhenom(other.getGeno() & ~crossoverBitMask | getGeno() & crossoverBitMask, problemSize);
    }

    @Override
    public GenericGenoPhenom<Long, Long> mutate() {
        long bitMask = 0;
        for(int i=0; i<problemSize/10; i++)
            bitMask |= 1L<<((int) (Math.random()*problemSize));

        return new GenoPhenom(getGeno() ^ bitMask, problemSize);
    }

    @Override
    public double fitnessEvaluation() {
        return Long.bitCount(getGeno());
    }


    public String toString() {
        return "Geno: " + Long.toBinaryString(getGeno());
    }
}
