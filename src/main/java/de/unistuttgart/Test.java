package de.unistuttgart;


import java.util.*;

public class Test {

    public static void main(String[] args) {
        int choice;
        double latitudeStart;
        double longitudeStart;
        double latitudeDestination;
        double longitudeDestination;
        int startNode;
        int destinationNode;
        Dijkstra.DijkstraReturn dijkstraReturn;
        long tsmp1;
        long tsmp2;

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

        tsmp2 = System.currentTimeMillis();
        System.out.println("Took: " + ((tsmp2 - tsmp1)) + "ms");

        do {
            System.out.println("///TEST MODULE///");
            System.out.println("0: Exit");
            System.out.println("1: Perform Dijkstra OneToAll: Position to Position");
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
                    startNode = graph.findNearestNode(latitudeStart, longitudeStart);
                    tsmp1 = System.currentTimeMillis();
                    dijkstraReturn = Dijkstra.dijkstraOneToAll(graph, startNode);
                    tsmp2 = System.currentTimeMillis();
                    System.out.println("Took " + ((tsmp2 - tsmp1)) + "ms");
                }
                case 2 -> {
                    System.out.println("Please insert start node");
                    startNode = scanner.nextInt();
                    System.out.println("Please insert the destination node");
                    destinationNode = scanner.nextInt();
                    tsmp1 = System.currentTimeMillis();
                    dijkstraReturn = Dijkstra.dijkstraOneToAll(graph, startNode);
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
                    startNode = graph.findNearestNode(latitudeStart, longitudeStart);
                    destinationNode = graph.findNearestNode(latitudeDestination, latitudeStart);
                    dijkstraReturn = Dijkstra.dijkstraOneToOne(graph, startNode, destinationNode);
                    System.out.print("Weg: ");
                    for (int j : Dijkstra.createShortestWay(graph, destinationNode, dijkstraReturn)) {
                        if (j != -1) {
                            System.out.print("->" + j);
                        }
                    }
                    System.out.print("\n");
                    System.out.println("Distance: " + dijkstraReturn.distance()[destinationNode]);
                }
                case 4 -> {
                    System.out.println("Please insert start node");
                    startNode = scanner.nextInt();
                    System.out.println("Please insert the destination node");
                    destinationNode = scanner.nextInt();
                    dijkstraReturn = Dijkstra.dijkstraOneToOne(graph, startNode, destinationNode);
                    System.out.print("Weg: ");
                    for (int j : Dijkstra.createShortestWay(graph, destinationNode, dijkstraReturn)) {
                        if (j != -1) {
                            System.out.print("->" + j);
                        }
                    }
                    System.out.print("\n");
                    System.out.println("Distance: " + dijkstraReturn.distance()[destinationNode]);
                }
                case 5 -> {
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart = scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart = scanner.nextDouble();
                    try {
                        startNode = graph.findNearestNode(latitudeStart, longitudeStart);
                    } catch (Exception e) {
                        System.out.println("exception while finding nearest Node");
                        return;
                    }
                    System.out.println("The next node is: " + startNode);
                }
            }

        } while (choice != 0);


    }
}
