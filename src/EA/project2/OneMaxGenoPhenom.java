package EA.project2;

import EA.generic.BitwiseOperations;
import EA.generic.GenericGenoPhenom;

public class OneMaxGenoPhenom extends GenericGenoPhenom<Long, Long> {
    private String formatStr;
    protected int problemSize;

    public OneMaxGenoPhenom(Long geno, int problemSize) {
        super(geno);
        this.problemSize = problemSize;
        formatStr = "%" + problemSize + "s";
    }

    @Override
    public Long developmentalMethod() {
        return getGeno();
    }

    @Override
    public GenericGenoPhenom<Long, Long> crossover(GenericGenoPhenom<Long, Long> other) {
        return new OneMaxGenoPhenom(BitwiseOperations.crossover(getGeno(), other.getGeno(), problemSize), problemSize);
    }

    @Override
    public GenericGenoPhenom<Long, Long> mutate() {
        return new OneMaxGenoPhenom(BitwiseOperations.mutate(getGeno(), problemSize), problemSize);
    }

    @Override
    public double fitnessEvaluation() {
        return Long.bitCount(getGeno());
    }


    public String toString() {
        return String.format(formatStr, Long.toBinaryString(getGeno())).replace(' ', '0');
    }
}
