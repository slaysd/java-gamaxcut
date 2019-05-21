package io.github.hashbox.ga.crossover;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Crossover;
import io.github.hashbox.ga.chromosome.BinaryChromosome;
import io.github.hashbox.structure.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiPointCut implements Crossover {
    private int nPoint;
    private Random rand;

    public MultiPointCut(int nPoint) {
        this.nPoint =  nPoint;
        this.rand = new Random();
    }

    @Override
    public Chromosome run(Chromosome o1, Chromosome o2) {
        Graph g = Graph.getInstance();
        List<Integer> newGeneration = new ArrayList<>(g.getNodeSize());
        List<Integer> points = new ArrayList<>(this.nPoint);
        int size = g.getNodeSize();

        for (int i = 0; i < this.nPoint; i++) {
            points.add((int) (rand.nextDouble() * size));
        }
        points.add(g.getNodeSize());
        points.sort(Integer::compareTo);

        int prev = 0;
        int cnt = 0;
        for (int point : points) {
            if (cnt % 2 == 0) {
                newGeneration.addAll(o1.getSubChromosome(prev, point));
            }
            else {
                newGeneration.addAll(o2.getSubChromosome(prev, point));
            }
            prev = point;
            cnt++;
        }


        return new BinaryChromosome(newGeneration);
    }
}
