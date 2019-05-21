package io.github.hashbox.ga.replacement;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Replacement;

import java.util.Collections;
import java.util.List;

public class BestReplacement implements Replacement {
    @Override
    public List<Chromosome> run(List<Chromosome> population, List<Chromosome> newGeneration) {
        int P = population.size();
        population.addAll(newGeneration);
        population.sort(Chromosome::compareTo);
        Collections.reverse(population);

        return population.subList(0, P);
    }
}
