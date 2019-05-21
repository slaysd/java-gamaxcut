package io.github.hashbox.ga.replacement;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Replacement;

import java.util.Collections;
import java.util.List;

public class RandomReplacement implements Replacement {

    @Override
    public List<Chromosome> run(List<Chromosome> population, List<Chromosome> newGeneration) {
        int size = population.size();
        population.addAll(newGeneration);
        Collections.shuffle(population);
        return population.subList(0, size);
    }
}
