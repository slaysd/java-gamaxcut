package io.github.hashbox.ga;

import java.util.Collections;
import java.util.List;

public abstract class Selection {
    protected Chromosome left;
    protected Chromosome right;
    protected double total;

    public void init(List<Chromosome> chromosomes) {
        chromosomes.stream()
                .map(chromosome -> chromosome.getScore())
                .reduce((o1, o2) -> o1 + o2)
                .ifPresentOrElse(value -> this.total = value, () -> this.total = 0);
    }

    public List<Chromosome> sort(List<Chromosome> chromosomes) {
        chromosomes.sort(Chromosome::compareTo);
        Collections.reverse(chromosomes);

        return chromosomes;
    }

    public Chromosome pickLeft() {
        return this.left;
    }

    public Chromosome pickRight() {
        return this.right;
    }

    abstract public List<Chromosome> select(List<Chromosome> chromosomes);
}
