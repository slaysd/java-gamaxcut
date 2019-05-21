package io.github.hashbox.ga;

import io.github.hashbox.structure.Edge;
import io.github.hashbox.structure.Graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Chromosome implements Comparable<Chromosome> {
    protected static int localSearchCnt = 0;

    protected int size;
    protected int score;
    protected List<Integer> chromosome;

    public Chromosome(int size) {
        this.size = size;
        this.chromosome = new ArrayList<>(size);
        this.init();
        this.score = this.calcScore();
    }

    public Chromosome(List<Integer> chromosome) {
        this.size = chromosome.size();
        this.chromosome = new ArrayList<>(chromosome);
        this.score = this.calcScore();
    }

    protected int calcScore() {
        Graph g = Graph.getInstance();
        int score = 0;
        for (Edge edge : g.getEdges()) {
            if (this.chromosome.get(g.getNodes().indexOf(edge.getNode1())) != this.chromosome.get(g.getNodes().indexOf(edge.getNode2()))) {
                score += edge.getWeight();
            }
        }

        return score;
    }

    public static void resetLocalSearchCnt() {
        localSearchCnt = 0;
    }

    public List<Integer> getGroupNode(int s) {
        Graph g = Graph.getInstance();
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            if (this.chromosome.get(i) == s) {
                nodes.add(g.getNodes().get(i).getId());
            }
        }
        nodes.sort(Integer::compareTo);

        return nodes;
    }

    public int getLocalSearchCnt() {
        return this.localSearchCnt;
    }

    public void doneMutation() {
        this.score = this.calcScore();
    }

    public int getScore() {
        return this.score;
    }

    public int getSize() {
        return this.size;
    }

    public List<Integer> getSubChromosome(int start, int end) {
        return this.chromosome.subList(start, end);
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }

    @Override
    public String toString() {
        return "" + this.getChromosome();
    }

    @Override
    public int compareTo(Chromosome o) {
        return Integer.compare(this.getScore(), o.getScore());
    }

    abstract public void init();

    abstract public void mutationGene(int idx);

    abstract public void doLocalSearch();

}
