package route_planner;

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
        DijkstraWithTreeSet.NodeWrapper nodeWrapper;
        long tsmp1;
        long tsmp2;
        long tsmp3;
        long tsmp4;

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
            System.out.println("1: Perform Dijkstra OneToAll: Position");
            System.out.println("2: Perform Dijkstra OneToAll: Node");
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
                    //tsmp1 = System.currentTimeMillis();
                    //dijkstraReturn = Dijkstra.optimizedDijkstraOneToAll(graph, startNode);
                    //tsmp2 = System.currentTimeMillis();
                    //System.out.println("Took " + ((tsmp2 - tsmp1)) + "ms" + "Node" + startNode);
                    tsmp3 = System.currentTimeMillis();
                    DijkstraWithTreeSet.findShortestPathOneToAll(graph, startNode);
                    tsmp4 = System.currentTimeMillis();
                    System.out.println("Took " + ((tsmp4 - tsmp3)) + "ms");
                }
                case 2 -> {
                    System.out.println("Please insert start node");
                    startNode = scanner.nextInt();
                    //tsmp1 = System.currentTimeMillis();
                    //dijkstraReturn = Dijkstra.optimizedDijkstraOneToAll(graph, startNode);
                    //tsmp2 = System.currentTimeMillis();
                    //System.out.println("Took " + ((tsmp2 - tsmp1)) + "ms");
                    tsmp3 = System.currentTimeMillis();
                    DijkstraWithTreeSet.findShortestPathOneToAll(graph, startNode);
                    tsmp4 = System.currentTimeMillis();
                    System.out.println("Took " + ((tsmp4 - tsmp3)) + "ms");
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
                    destinationNode = graph.findNearestNode(latitudeDestination, longitudeDestination);
                    nodeWrapper = DijkstraWithTreeSet.findShortestPathOneToOne(graph, startNode, destinationNode);
                    System.out.print("Weg: ");
                    List<Integer> path = DijkstraWithTreeSet.createShortestPath(destinationNode, nodeWrapper);
                    if (path != null) {
                    	for (int j : DijkstraWithTreeSet.createShortestPath(destinationNode, nodeWrapper)) {                      
                    		if (j != startNode) {                            
                    			System.out.print("->" + j);                      
                    		} else {                      	
                    			System.out.print(j);                    
                    		}                      
                    	}
                    System.out.print("\n");
                    System.out.println("Distance: " + nodeWrapper.distance()[destinationNode]);
                    } else {
                    	System.out.println("No Path found");
                    }
                }
                case 4 -> {
                    System.out.println("Please insert start node");
                    startNode = scanner.nextInt();
                    System.out.println("Please insert the destination node");
                    destinationNode = scanner.nextInt();
                    //tsmp1 = System.currentTimeMillis();
                    //dijkstraReturn = Dijkstra.optimizedDijkstraOneToOne(graph, startNode, destinationNode);
                    //tsmp2 = System.currentTimeMillis();
                    //System.out.println("Took " + ((tsmp2 - tsmp1)) + "ms");
                    tsmp3 = System.currentTimeMillis();
                    nodeWrapper = DijkstraWithTreeSet.findShortestPathOneToOne(graph, startNode, destinationNode);
                    tsmp4 = System.currentTimeMillis();
                    System.out.println("Took " + ((tsmp4 - tsmp3)) + "ms");
                    System.out.print("Weg: ");
                    List<Integer> path = DijkstraWithTreeSet.createShortestPath(destinationNode, nodeWrapper);
                    if (path != null) {
                    	for (int j : DijkstraWithTreeSet.createShortestPath(destinationNode, nodeWrapper)) {                      
                    		if (j != startNode) {                            
                    			System.out.print("->" + j);                      
                    		} else {                      	
                    			System.out.print(j);                    
                    		}                      
                    	}
                    System.out.print("\n");
                    //System.out.println("Distance: " + dijkstraReturn.distance()[destinationNode]);    
                    System.out.println("Distance: " + nodeWrapper.distance()[destinationNode]);
                    } else {
                    	System.out.println("No Path found");
                    }
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