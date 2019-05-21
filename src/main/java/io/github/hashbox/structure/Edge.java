package io.github.hashbox.structure;

public class Edge {
    private int weight;
    private Node node1;
    private Node node2;

    public Edge(int weight, Node node1, Node node2) {
        this.weight = weight;
        this.node1 = node1;
        this.node2 = node2;
        node1.addEdge(this);
        node2.addEdge(this);
    }

    public int remove() {
        this.node1.removeEdge(this);
        this.node2.removeEdge(this);

        return weight;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("Edge[(%s,%s), %s]", this.node1, this.node2, this.weight);
    }
}
