package EA.generic;

public class BitwiseOperations {
    public static long crossover(long num1, long num2, int maxPoint) {
        int crossoverPoint = 1 + (int) (Math.random()*maxPoint - 2);
        long crossoverBitMask = (1<<crossoverPoint) - 1;

        return num2 & ~crossoverBitMask | num1 & crossoverBitMask;
    }


    public static long mutate(long num, int maxPoint) {
        long bitMask = 0;
        double chance = 1d/500;
        for(int i = 0; i< maxPoint; i++) {
            if (Math.random() < chance)
                bitMask |= 1L << i;
        }

        return num ^ bitMask;
    }
}
