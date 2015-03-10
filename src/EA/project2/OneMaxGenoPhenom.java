package EA.project2;

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
        return new OneMaxGenoPhenom(binaryCrossover(getGeno(), other.getGeno(), problemSize), problemSize);
    }

    @Override
    public GenericGenoPhenom<Long, Long> mutate() {
        return new OneMaxGenoPhenom(binaryMutation(getGeno(), problemSize), problemSize);
    }

    @Override
    public double fitnessEvaluation() {
        return Long.bitCount(getGeno());
    }


    public String toString() {
        return String.format(formatStr, Long.toBinaryString(getGeno())).replace(' ', '0');
    }


    public static long binaryCrossover(long num1, long num2, int maxPoint) {
        int crossoverPoint = 1 + (int) (Math.random()*maxPoint - 2);
        long crossoverBitMask = (1<<crossoverPoint) - 1;

        return num2 & ~crossoverBitMask | num1 & crossoverBitMask;
    }

    public static long binaryMutation(long num, int maxPoint) {
        long bitMask = 0;
        double chance = 1d/100;
        for(int i = 0; i< maxPoint; i++) {
            if (Math.random() < chance)
                bitMask |= 1L << i;
        }

        return num ^ bitMask;
    }
}
