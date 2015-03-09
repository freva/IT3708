package EA.generic;

public class BitwiseOperations {
    public static long crossover(long num1, long num2, int maxPoint) {
        long crossoverBitMask = (1<<maxPoint) - 1;

        return num2 & ~crossoverBitMask | num1 & crossoverBitMask;
    }


    public static long mutate(long num, int maxPoint) {
        long bitMask = 0;
        for(int i=0; i<maxPoint/10; i++)
            bitMask |= 1L<<((int) (Math.random()*maxPoint));

        return num ^ bitMask;
    }
}
