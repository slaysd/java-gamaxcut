package io.github.hashbox.ga.crossover;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Crossover;
import io.github.hashbox.ga.chromosome.BinaryChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnePointCut implements Crossover {
    private Random rand = new Random();

    @Override
    public Chromosome run(Chromosome o1, Chromosome o2) {
        int point = (int) (rand.nextDouble() * o1.getSize());
        List<Integer> newGeneration = new ArrayList<>(o1.getSize());
        if (rand.nextBoolean()) {
            newGeneration.addAll(o1.getSubChromosome(0, point));
            newGeneration.addAll(o2.getSubChromosome(point, o1.getSize()));
        }
        else {
            newGeneration.addAll(o2.getSubChromosome(0, point));
            newGeneration.addAll(o1.getSubChromosome(point, o1.getSize()));
        }
        return new BinaryChromosome(newGeneration);
    }
}
