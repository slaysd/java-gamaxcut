package io.github.hashbox.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Graph {
    private static Graph _instance = null;

    private List<Node> nodes;
    private List<Edge> edges;

    private Graph() { }

    private Graph(int nV, int nE) {
        this.nodes = new ArrayList<>(nV);
        this.edges = new ArrayList<>(nE);
    }

    public static Graph getInstance() {
        return _instance;
    }

    public static Graph getInstance(int nV, int nE) {
        _instance = new Graph(nV, nE);

        return _instance;
    }

    public void addNode(Node node) {
        if (!this.nodes.contains(node)) {
            this.nodes.add(node);
        }
    }

    public void addEdge(Edge edge) {
        if (!this.edges.contains(edge)) {
            this.edges.add(edge);
        }
    }

    public Optional<Node> getNode(int id) {
        return this.nodes.stream()
                .filter(node -> node.getId() == id)
                .findAny();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getNodeSize() {
        return this.nodes.size();
    }

    public int getEdgeSize() {
        return this.edges.size();
    }

    @Override
    public String toString() {
        return String.format("V: %s, E: %s", this.nodes, this.edges);
    }
}
