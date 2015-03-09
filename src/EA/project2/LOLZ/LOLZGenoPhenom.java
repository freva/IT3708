package EA.project2.LOLZ;

import EA.generic.BitwiseOperations;
import EA.generic.GenericGenoPhenom;
import EA.project2.OneMax.OneMaxGenoPhenom;

public class LOLZGenoPhenom extends OneMaxGenoPhenom {
    private int z;

    public LOLZGenoPhenom(Long geno, int problemSize, int z) {
        super(geno, problemSize);
        this.z = z;
    }

    @Override
    public GenericGenoPhenom<Long, Long> crossover(GenericGenoPhenom<Long, Long> other) {
        return new LOLZGenoPhenom(BitwiseOperations.crossover(getGeno(), other.getGeno(), problemSize), problemSize, z);
    }

    @Override
    public GenericGenoPhenom<Long, Long> mutate() {
        return new LOLZGenoPhenom(BitwiseOperations.mutate(getGeno(), problemSize), problemSize, z);
    }

    @Override
    public double fitnessEvaluation() {
        int numLeadingZeroes = Long.numberOfLeadingZeros(getPhenom())-64+problemSize;
        return numLeadingZeroes > 0 ? Math.min(numLeadingZeroes, z) :
                Long.numberOfLeadingZeros(~getPhenom()&((1L << problemSize)-1)) - 64+problemSize;
    }
}
