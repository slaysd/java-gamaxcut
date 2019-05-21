package io.github.hashbox.ga.selection;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Selection;

import java.util.List;
import java.util.Random;

public class RandomSelection extends Selection {
    private Random rand = new Random();

    @Override
    public List<Chromosome> select(List<Chromosome> chromosomes) {
        this.left = chromosomes.get(rand.nextInt(chromosomes.size()));
        while (this.left == (this.right = chromosomes.get(rand.nextInt(chromosomes.size())))) {}

        return chromosomes;
    }

}
