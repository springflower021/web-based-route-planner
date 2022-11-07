package de.unistuttgart;

import java.io.File;
import java.io.FileNotFoundException;
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


    public static Graph readGraphFromFile (String pathname){

        Scanner scan;

        try {
            scan = new Scanner(new File(pathname));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        scan.useLocale(Locale.ENGLISH);

        String readingLine = new String(scan.nextLine());

        while (readingLine.contains("#")||readingLine.isEmpty()){
            readingLine = scan.nextLine();
        }

        int numberOfNodes = Integer.parseInt(readingLine);
        readingLine = scan.nextLine();

        int numberOfEdges = Integer.parseInt(readingLine);

        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        for (int i=0; i<numberOfNodes; i++){

            System.out.println("Node");

            int readId = scan.nextInt();
            int readId2 = scan.nextInt();
            double readLati = scan.nextDouble();
            double readLongi = scan.nextDouble();
            int readElev = scan.nextInt();

            Node temporaryNode = new Node(readId,readLati,readLongi);
            nodes.add(i,temporaryNode);
        }
        for (int j=0; j<numberOfEdges; j++){

            System.out.println("Edge");

            int readSrc = scan.nextInt();
            int readTrg = scan.nextInt();
            int readWight = scan.nextInt();
            int readType = scan.nextInt();
            int readMaxSpeed = scan.nextInt();

            Edge temporaryEdge = new Edge(readSrc,readTrg,readWight);
            edges.add(j,temporaryEdge);
        }
        return new Graph(numberOfNodes,numberOfEdges, nodes, edges);
    }
}
