package io.github.hashbox.ga.selection;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Selection;
import io.github.hashbox.ga.chromosome.BinaryChromosome;

import java.util.List;
import java.util.Random;

public class RouletteWheelSelection extends Selection {
    Random rand = new Random();

    private int max;
    private int min;
    private double base;
    private double total;
    private int selectionPressure;

    public RouletteWheelSelection(int selectionPressure) {
        this.selectionPressure = selectionPressure;
    }

    @Override
    public void init(List<Chromosome> chromosomes) {
        this.max = chromosomes.get(0).getScore();
        this.min = chromosomes.get(chromosomes.size()-1).getScore();
        this.base = (double)(this.max - this.min) / (this.selectionPressure - 1);
        this.total = chromosomes.stream()
                .map(chromosome -> chromosome.getScore() - this.min + this.base)
                .reduce((o1, o2) -> o1 + o2)
                .orElse(0.0);
    }

    @Override
    public List<Chromosome> select(List<Chromosome> chromosomes) {
        this.left = this.selection(chromosomes);
        this.right = this.selection(chromosomes);
        return chromosomes;
    }

    private Chromosome selection(List<Chromosome> chromosomes) {
        double point = rand.nextDouble() * this.total;
        double sum = 0;
        Chromosome result = null;
        for (Chromosome c : chromosomes) {
            sum += (c.getScore() - this.min + this.base);
            result = c;
            if (point < sum) {
                break;
            }
        }
        return result;
    }
}
