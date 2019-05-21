package io.github.hashbox.structure;

import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable {
    private int id;
    private Set<Edge> edges = new HashSet<>();

    public Node(int id) {
        this.id = id;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public int getId() {
        return this.id;
    }

    public Set<Edge> getEdges() { return this.edges; }

    public boolean removeEdge(Edge edge) {
        return this.edges.remove(edge);
    }

    @Override
    public String toString() {
        return String.format("Node_%s", this.id);
    }

    @Override
    public int compareTo(Object o) {
        return this.id < ((Node)o).getId() ? -1 : 1;
    }
}
