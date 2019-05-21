package io.github.hashbox.ga.crossover;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Crossover;
import io.github.hashbox.ga.chromosome.BinaryChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformCrossover implements Crossover {
    private Random rand = new Random();
    private double probability;

    public UniformCrossover(double probability) {
        this.probability = probability;
    }

    @Override
    public Chromosome run(Chromosome o1, Chromosome o2) {
        List<Integer> newGeneration = new ArrayList<>(o1.getSize());
        for (int i = 0; i < o1.getSize(); i++) {
            if (rand.nextDouble() < this.probability) {
                newGeneration.add(o1.getChromosome().get(i));
            }
            else {
                newGeneration.add(o2.getChromosome().get(i));
            }
        }
        return new BinaryChromosome(newGeneration);
    }
}
