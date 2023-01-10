package route_planner;

import java.util.*;

public class Dijkstra {

    public record DijkstraReturn(int[] distance, int[] previous) {
    }

    public static DijkstraReturn dijkstraOneToAll(Graph graph, int startNode) {
        DijkstraReturn dijkstraReturn = new DijkstraReturn(new int[graph.numberOfNodes], new int[graph.numberOfNodes]);


        PriorityQueue<Integer> Q = new PriorityQueue<>(graph.numberOfNodes, (n1, n2) -> Integer.compare(dijkstraReturn.distance[n1], dijkstraReturn.distance[n2]));
        initialize(graph, startNode, dijkstraReturn.distance, dijkstraReturn.previous, Q);

        while (!Q.isEmpty()) {

            int u = Q.poll();

            for (int i = graph.offset[u]; i < graph.offset[u + 1]; i++) {
                /* final */ int v = graph.edge[1][i];
                int alt = dijkstraReturn.distance[u] + graph.edge[2][i];
                if (alt < dijkstraReturn.distance[v]) {
                	dijkstraReturn.distance[v] = alt;
                	dijkstraReturn.previous[v] = u;
                	//update PriorityQueue
                	Q.remove(v);
                	Q.add(v);
                }
                /*
                final int distanceUV = graph.edge[2][i];
                distanceUpdate(u, v, dijkstraReturn.distance, dijkstraReturn.previous, distanceUV, Q);
                */
            }
            
        }
        return dijkstraReturn;
    }

    public static DijkstraReturn optimizedDijkstraOneToAll(Graph graph, int startNode) {
    	DijkstraReturn dijkstraReturn = new DijkstraReturn(new int[graph.numberOfNodes], new int[graph.numberOfNodes]);
    	int node = startNode;
    	Queue<Integer> frontier = new PriorityQueue<>(graph.numberOfNodes, (n1, n2) -> Integer.compare(dijkstraReturn.distance[n1], dijkstraReturn.distance[n2]));
    	Set<Integer> expanded = new HashSet<>();
    	dijkstraReturn.distance[startNode] = 0;
    	dijkstraReturn.previous[startNode] = -1;
    	frontier.add(startNode);
    	
    	while (!frontier.isEmpty()) {
    		/*
    		if (frontier.isEmpty()) {
    			System.out.println("DijkstraOneToAll failed");
    			return dijkstraReturn;
    		}
    		*/
    		node = frontier.poll();	
    		expanded.add(node);
            for (int i = graph.offset[node]; i < graph.offset[node + 1]; i++) {
            	int n = graph.edge[1][i];
            	if (!frontier.contains(n) && !expanded.contains(n)) {
            		dijkstraReturn.distance[n] = dijkstraReturn.distance[node] + graph.edge[2][i];
            		dijkstraReturn.previous[n] = node;
            		frontier.add(n);
            	}
            	else if (frontier.contains(n) && dijkstraReturn.distance[n] > dijkstraReturn.distance[node] + graph.edge[2][i]) {
            		dijkstraReturn.distance[n] = dijkstraReturn.distance[node] + graph.edge[2][i];
                	dijkstraReturn.previous[n] = node;
            		frontier.remove(n);
                	frontier.add(n);
                }
            }   	
    	}
    	return dijkstraReturn;
    }
    
    public static DijkstraReturn dijkstraOneToOne(Graph graph, int startNode, int destinationNode) {
        DijkstraReturn dijkstraReturn = new DijkstraReturn(new int[graph.numberOfNodes], new int[graph.numberOfNodes]);


        PriorityQueue<Integer> Q = new PriorityQueue<>(graph.numberOfNodes, (n1, n2) -> Integer.compare(dijkstraReturn.distance[n1], dijkstraReturn.distance[n2]));
        initialize(graph, startNode, dijkstraReturn.distance, dijkstraReturn.previous, Q);

        while (!Q.isEmpty()) {

            int u = Q.poll();

            for (int i = graph.offset[u]; i < graph.offset[u + 1]; i++) {
                /* final */ int v = graph.edge[1][i];
                int alt = dijkstraReturn.distance[u] + graph.edge[2][i];
                if (alt < dijkstraReturn.distance[v]) {
                	dijkstraReturn.distance[v] = alt;
                	dijkstraReturn.previous[v] = u;
                	//update PriorityQueue
                	Q.remove(v);
                	Q.add(v);
                }
                /*
                final int distanceUV = graph.edge[2][i];
                distanceUpdate(u, v, dijkstraReturn.distance, dijkstraReturn.previous, distanceUV, Q);
                */
            }
            if (u == destinationNode) {
                break;
            }
        }
        return dijkstraReturn;
    }
    
    public static DijkstraReturn optimizedDijkstraOneToOne(Graph graph, int startNode, int destinationNode) {
    	DijkstraReturn dijkstraReturn = new DijkstraReturn(new int[graph.numberOfNodes], new int[graph.numberOfNodes]);
    	int node = startNode;
    	Queue<Integer> frontier = new PriorityQueue<>(graph.numberOfNodes, (n1, n2) -> Integer.compare(dijkstraReturn.distance[n1], dijkstraReturn.distance[n2]));
    	Set<Integer> expanded = new HashSet<>();
    	dijkstraReturn.distance[startNode] = 0;
    	dijkstraReturn.previous[startNode] = -1;
    	frontier.add(startNode);
    	
    	do {
    		if (frontier.isEmpty()) {
    			System.out.println("DijkstraOneToOne failed");
    			return dijkstraReturn;
    		}
    		node = frontier.poll();	
    		expanded.add(node);
            for (int i = graph.offset[node]; i < graph.offset[node + 1]; i++) {
            	int n = graph.edge[1][i];
            	if (!frontier.contains(n) && !expanded.contains(n)) {
            		dijkstraReturn.distance[n] = dijkstraReturn.distance[node] + graph.edge[2][i];
            		dijkstraReturn.previous[n] = node;
            		frontier.add(n);
            	}
            	else if (frontier.contains(n) && dijkstraReturn.distance[n] > dijkstraReturn.distance[node] + graph.edge[2][i]) {
            		dijkstraReturn.distance[n] = dijkstraReturn.distance[node] + graph.edge[2][i];
                	dijkstraReturn.previous[n] = node;
            		frontier.remove(n);
                	frontier.add(n);
                }
            }   	
    	} while (!expanded.contains(destinationNode));
    	
    	return dijkstraReturn;
    }

    private static void initialize(Graph graph, int startNode, int[] distance, int[] previous, PriorityQueue<Integer> Q) {
        distance[startNode] = 0;
        
    	for (int i = 0; i < graph.numberOfNodes; i++) {
            if (i != startNode) {
                distance[i] = Integer.MAX_VALUE;
            } 
            Q.add(i);
            previous[i] = -1;
        }
        /**
        Q.add(startNode);
        **/
    }

/*
    private static void distanceUpdate(int u, int v, int[] distance, int[] previous, int distanceUV, PriorityQueue<Integer> Q) {
    	if ((distance[u] + distanceUV) < distance[v]) {
            distance[v] = distance[u] + distanceUV;
            
            //update PriorityQueue
            Q.remove(v);
            Q.add(v);
            
            previous[v] = u;
        }

    }
*/
    
    public static int[] createShortestWay(Graph graph, int destinationNode, DijkstraReturn dijkstraReturn) {
        int[] route = new int[graph.numberOfNodes];
        Arrays.fill(route, -1);
        route[graph.numberOfNodes - 1] = destinationNode;
        int u = destinationNode;
        int i = 2;
        while (dijkstraReturn.previous[u] != -1) {
            u = dijkstraReturn.previous[u];
            route[graph.numberOfNodes - i] = u;
            i++;
        }

        return route;
    }
}
