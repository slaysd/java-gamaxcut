package io.github.hashbox.ga.chromosome;

import io.github.hashbox.ga.Chromosome;
import io.github.hashbox.structure.Edge;
import io.github.hashbox.structure.Graph;
import io.github.hashbox.structure.Node;

import java.util.*;

public class BinaryChromosome extends Chromosome {
    
    public BinaryChromosome(int size) {
        super(size);
    }

    public BinaryChromosome(List<Integer> chromosome) {
        super(chromosome);
    }

    public void init() {
        Random rand = new Random();
        for (int i = 0; i < this.size; i++) {
            this.chromosome.add(rand.nextInt(2));
        }
    }

    public void bitflip(int i) {
        this.chromosome.set(i, 1 - this.chromosome.get(i));
    }

    public void doLocalSearch() {
        localSearchCnt++;

        Graph g = Graph.getInstance();
        List<Integer> nxIdx = new ArrayList<>(g.getNodeSize());
        for (int i = 0; i < g.getNodeSize(); i++) {
            nxIdx.add(i);
        }
        Collections.shuffle(nxIdx);
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i : nxIdx) {
                int score = 0;
                List<Node> nodes = g.getNodes();
                for (Edge e : nodes.get(i).getEdges()) {
                    if (this.chromosome.get(nodes.indexOf(e.getNode1())) == this.chromosome.get(nodes.indexOf(e.getNode2()))) {
                        score += e.getWeight();
                    }
                    else {
                        score -= e.getWeight();
                    }
                }
                if (score > 0) {
                    this.bitflip(i);
                    improved = true;
                }
            }
            this.score = this.calcScore();
        }
    }

    @Override
    public void mutationGene(int idx) {
        this.chromosome.set(idx, 1 - this.chromosome.get(idx));
    }

}
