package io.github.hashbox.ga.mutation;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.ga.Mutation;

import java.util.Random;

public class UniformMutation implements Mutation {
    private double mutationRate;
    private Random rand = new Random();

    public UniformMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public Chromosome run(Chromosome chromosome) {
        int cnt = 0;
        for (int idx = 0; idx < chromosome.getSize(); idx++) {
            if (rand.nextDouble() < this.mutationRate) {
                chromosome.mutationGene(idx);
            }
        }
//        for (int idx = rand.nextInt(chromosome.getSize()); idx < chromosome.getSize(); idx++) {
//            if (rand.nextDouble() < MUTATION_RATE) {
//                chromosome.mutationGene(idx);
//                cnt++;
//            }
//            if (cnt > chromosome.getSize() * 0.1) break;
//        }
//        while (true) {
//            if (rand.nextDouble() < MUTATION_RATE) {
//                chromosome.mutationGene(rand.nextInt(chromosome.getSize()));
//                cnt++;
//            }
//            if (cnt > chromosome.getSize() * 0.2) break;
//        }
        chromosome.doneMutation();
        return chromosome;
    }
}
