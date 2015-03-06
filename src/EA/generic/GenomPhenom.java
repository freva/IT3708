package EA.generic;

public abstract class GenomPhenom<T1, T2> implements Comparable<T2> {
    private T2 phenom = null;

    public abstract T2 developmentalMethod();

    public T2 getPhenom() {
        if(phenom == null) phenom = developmentalMethod();
        return phenom;
    }

    public abstract GenomPhenom<T1, T2> crossover(GenomPhenom<T1, T2> other);

    public abstract GenomPhenom<T1, T2> mutate();

    public abstract double fitnessEvaluation();

    public int compareTo(Object other) {
        if(! (other instanceof GenomPhenom)) return -1;
        return (int) Math.signum(fitnessEvaluation() - ((GenomPhenom) other).fitnessEvaluation());
    }
}
