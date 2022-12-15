package routeplanner;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int choice;
        double latitudeStart;
        double longitudeStart;
        double latitudeDestination;
        double longitudeDestination;
		double[] coords = {0.0, 0.0};
        Node sourceNode;
        Node targetNode;
        long tsmp1;
        long tsmp2;

        Dijkstra oneToAllDijkstra;
        Dijkstra oneToOneDijkstra;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please insert the name of the graph file");
        String pathname = scanner.nextLine();
        tsmp1 = System.currentTimeMillis();
        Graph graph;
        try {
            graph = new Graph(pathname);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        graph.printGraph(graph);
        tsmp2 = System.currentTimeMillis();
        System.out.println("Took: " + ((tsmp2 - tsmp1)) + "ms");

        do {
            System.out.println("///TEST MODULE///");
            System.out.println("0: Exit");
            System.out.println("1: Perform Dijkstra OneToAll: Position to Node");
            System.out.println("2: Perform Dijkstra OneToAll: Node to Node");
            System.out.println("3: Perform Dijkstra OneToOne: Position to Position");
            System.out.println("4: Perform Dijkstra OneToOne: Node to Node");
            System.out.println("5: FindNextNode");

            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart = scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart = scanner.nextDouble();
                    sourceNode = graph.findNearestNode(latitudeStart, longitudeStart);
                    System.out.println(sourceNode);
            		coords[0] = sourceNode.getLati();
            	    coords[1] = sourceNode.getLongi();
                    tsmp1 = System.currentTimeMillis();
                    oneToAllDijkstra = new Dijkstra(graph);
            		oneToAllDijkstra.executeOneToAll(sourceNode);
                    tsmp2 = System.currentTimeMillis();
                    System.out.println("Took " + ((tsmp2 - tsmp1)) + "ms");
                }
                case 2 -> {
                    System.out.println("Please insert start node");
                    sourceNode = graph.getNode(scanner.nextInt());
                    System.out.println("Please insert the destination node");
                    targetNode = graph.getNode(scanner.nextInt());
                    tsmp1 = System.currentTimeMillis();
                    oneToAllDijkstra = new Dijkstra(graph);
              		oneToAllDijkstra.executeOneToAll(sourceNode);
              		oneToAllDijkstra.printPath(targetNode);
                    tsmp2 = System.currentTimeMillis();
                    System.out.println("Took " + ((tsmp2 - tsmp1)) + "ms");
                }
                case 3 -> {
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart = scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart = scanner.nextDouble();
                    System.out.println("Please insert the latitude of the destination position");
                    latitudeDestination = scanner.nextDouble();
                    System.out.println("Please insert the longitude of the destination position");
                    longitudeDestination = scanner.nextDouble();
                    sourceNode = graph.findNearestNode(latitudeStart, longitudeStart);
                    targetNode = graph.findNearestNode(latitudeDestination, longitudeDestination);
                    oneToOneDijkstra = new Dijkstra(graph);
                    oneToOneDijkstra.executeOneToOne(sourceNode, targetNode);
                    oneToOneDijkstra.printPath(targetNode);                 
                    System.out.print("\n");
                    System.out.println("Distance: " + oneToOneDijkstra.getminimalDistance(targetNode));
                }
                case 4 -> {
                    System.out.println("Please insert start node");
                    sourceNode = graph.getNode(scanner.nextInt());
                    System.out.println("Please insert the destination node");
                    targetNode = graph.getNode(scanner.nextInt());
                    oneToOneDijkstra = new Dijkstra(graph);
                    oneToOneDijkstra.executeOneToOne(sourceNode, targetNode);
                    oneToOneDijkstra.printPath(targetNode);    
                    System.out.print("\n");
                    System.out.println("Distance: " + oneToOneDijkstra.getminimalDistance(targetNode));
                }
                case 5 -> {
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart = scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart = scanner.nextDouble();
                    try {
                    	sourceNode = graph.findNearestNode(latitudeStart, longitudeStart);
                    } catch (Exception e) {
                        System.out.println("exception while finding nearest Node");
                        return;
                    }
                    System.out.println("The next node is: " + sourceNode);
                }
            }

        } while (choice != 0);


    }
}
