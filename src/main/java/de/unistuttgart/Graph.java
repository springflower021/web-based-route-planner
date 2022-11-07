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

    /**
     * This method is used to read a graph with nodes and edges from a text file.
     * @param pathname path of the text file which should be converted to a graph
     * @return Graph(numberOfNodes,numberOfEdges, nodes, edges)
     */
    public static Graph readGraphFromFile (String pathname){

        /**
         * Create Scanner and read file with english notation (point instead of comma for float)
         */

        Scanner scan;

        try {
            scan = new Scanner(new File(pathname));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        scan.useLocale(Locale.ENGLISH);

        /**
         * Reading the file line by line:
         * "readingline" represents the current line of the text file as string
         */

        String readingLine = new String(scan.nextLine());

        /**
         * The first lines of the text file, which don't contain the actual graph will be skipped
         */

        while (readingLine.contains("#")||readingLine.isEmpty()){
            readingLine = scan.nextLine();
        }

        /**
         * The number of nodes and edges of the graph will be saved in an additional variable,
         * which will also be used to determine the number of iterations to save single nodes and edges.
         * The single nodes and edges will be saved with their parameters in separate ArrayLists.
         */

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

        /**
         * The collected data from the text file will be merged to a graph object
         */

        return new Graph(numberOfNodes,numberOfEdges, nodes, edges);
    }
}
