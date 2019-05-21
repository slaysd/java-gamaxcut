package io.github.hashbox.ga.replacement;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Replacement;

import java.util.Collections;
import java.util.List;

public class HighAndLowReplacement implements Replacement {
    private static final double REPLACEMENT_RATE = 1.0;

    @Override
    public List<Chromosome> run(List<Chromosome> population, List<Chromosome> newGeneration) {
        population.sort(Chromosome::compareTo);
        newGeneration.sort(Chromosome::compareTo);
        Collections.reverse(newGeneration);

        int target = (int) (newGeneration.size() * REPLACEMENT_RATE);
        for (int i = 0; i < target; i++) {
            population.set(i, newGeneration.get(i));
        }

        return population;
    }
}
