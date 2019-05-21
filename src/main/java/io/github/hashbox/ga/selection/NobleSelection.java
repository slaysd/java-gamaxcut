package io.github.hashbox.ga.selection;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Selection;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 최고의 왕만이 최고의 세대를 만든다!!
 */

public class NobleSelection extends Selection {
    private Random rand = new Random();

    @Override
    public List<Chromosome> select(List<Chromosome> chromosomes) {
        int size = chromosomes.size();

        this.left = chromosomes.get(rand.nextInt((int) (size * 0.4)));
        this.right = chromosomes.get((int)(rand.nextDouble() * size));
//        this.pickRight = chromosomes.get(rand.nextInt((int) (size * 0.95)));

        return chromosomes;
    }
}
