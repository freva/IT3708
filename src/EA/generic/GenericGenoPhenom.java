package EA.generic;

public abstract class GenericGenoPhenom<T1, T2> implements Comparable<GenericGenoPhenom<T1, T2>> {
    private T1 geno;
    private T2 phenom = null;

    public GenericGenoPhenom(T1 geno) {
        this.geno = geno;
    }

    public abstract T2 developmentalMethod();

    public abstract GenericGenoPhenom<T1, T2> crossover(GenericGenoPhenom<T1, T2> other);

    public abstract GenericGenoPhenom<T1, T2> mutate();

    public abstract double fitnessEvaluation();


    public T1 getGeno() {
        return geno;
    }

    public T2 getPhenom() {
        if(phenom == null) phenom = developmentalMethod();
        return phenom;
    }

    public int compareTo(GenericGenoPhenom<T1, T2> other) {
        if(other == null) return -1;
        return (int) Math.signum(fitnessEvaluation() - other.fitnessEvaluation());
    }
}
