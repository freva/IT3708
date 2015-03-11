package EA.project2;

import EA.generic.GenericGenoPhenom;

public class LOLZGenoPhenom extends OneMaxGenoPhenom {
    private int z;

    public LOLZGenoPhenom(Long geno, int problemSize, int z) {
        super(geno, problemSize);
        this.z = z;
    }

    @Override
    public GenericGenoPhenom<Long, Long> crossover(GenericGenoPhenom<Long, Long> other) {
        return new LOLZGenoPhenom(binaryCrossover(getGeno(), other.getGeno(), problemSize), problemSize, z);
    }

    @Override
    public GenericGenoPhenom<Long, Long> mutate() {
        return new LOLZGenoPhenom(binaryMutation(getGeno(), problemSize), problemSize, z);
    }

    @Override
    public double fitnessEvaluation() {
        int numLeadingZeroes = Long.numberOfLeadingZeros(getPhenom())-64+problemSize;
        return (double) (numLeadingZeroes > 0 ? Math.min(numLeadingZeroes, z) :
                Long.numberOfLeadingZeros(~getPhenom()&((1L << problemSize)-1)) - 64+problemSize) / problemSize;
    }
}
