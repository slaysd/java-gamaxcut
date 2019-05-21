package io.github.hashbox.ga;

import java.util.List;

public interface Replacement {
    List<Chromosome> run(List<Chromosome> population, List<Chromosome> newGeneration);
}
