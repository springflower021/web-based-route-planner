package de.unistuttgart;

import java.util.*;

public class Graph {
    // number of nodes
    private int n;
    // number of edges
    private int m;
    private List<Node> nodes;
    private List<Edge> edges;

    List<List<Edge>> adjList = new ArrayList<>();

    public Graph(int n, int m, List<Node> nodes, List<Edge> edges) {
        this.n = n;
        this.m = m;
        this.nodes = nodes;
        this.edges = edges;

        for (int i = 0; i <= n; i++) {
            adjList.add(i, new ArrayList<>());
        }

        for (Node d : nodes) {
            for (Edge e : edges) {
                if (d.id == e.src) {
                    adjList.get(d.id).add(new Edge(e.src, e.trg, e.weight));
                }
            }
        }
    }

    public static void printGraph(Graph graph) {
        int src = 0;
        int n = graph.adjList.size();

        while (src < n) {
            for (Edge edge : graph.adjList.get(src)) {
                System.out.printf("%s\t", edge);
            }

            System.out.println();
            src++;
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
