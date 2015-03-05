package EA.generic;

import java.util.ArrayList;

public abstract class Genotype<T> {

    public abstract ArrayList<T> developmentalMethod();

    public abstract Genotype<T> crossover(Genotype<T> other);

    public abstract Genotype<T> mutate();
}
