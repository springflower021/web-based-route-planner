package de.unistuttgart;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    public static int [] dijkstra(Graph graph,int startNode, int destinationNode) {
        return createShortestWay(graph,destinationNode,dijkstraFunction(graph,startNode));
    }


    private static int[] dijkstraFunction(Graph graph, int startNode) {
        int[] distance = new int[graph.numberOfNodes];
        int[] previous = new int[graph.numberOfNodes];
        PriorityQueue<Integer> Q = new PriorityQueue <>(graph.numberOfNodes);

        initialize(graph, startNode, distance, previous,Q);

        while (!Q.isEmpty()){

            int u = Q.peek();
            for (int j =0; j<graph.numberOfNodes; j++) {
                if(!Q.contains(j)){
                    continue;
                }
                if (distance[j] < distance[u]) {
                    u = j;
                }
            }
            Q.remove(u);

            for(int i=graph.offset[u]; i<graph.offset[u+1]; i++) {
                final int v = graph.edge [1][i];
                final int distanceUV = graph.edge [2][i];
                if (Q.contains(v)) {
                    distanceUpdate(u, v, distance, previous, distanceUV);
                }
            }
        }
        return previous;
    }

    private static void initialize(Graph graph, int startNode, int[] distance, int[] previous,PriorityQueue <Integer> Q) {
        for (int i = 0; i < graph.numberOfNodes; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            Q.add(i);
        }
        distance[startNode] = 0;
    }

    private static void distanceUpdate(int u, int v, int[] distance, int[] previous, int distanceUV){
        if((distance[u] + distanceUV) < distance[v] ){
            distance[v] = distance[u] + distanceUV;
            previous[v] = u;
        }
    }

    private static int[] createShortestWay(Graph graph, int destinationNode, int [] previous) {
        int[] route = new int [graph.numberOfNodes];
        Arrays.fill(route, -1);
        route[graph.numberOfNodes-1]=destinationNode;
        int u = destinationNode;
        int i = 2;
        while (previous[u] != -1){
            u = previous[u];
            route[graph.numberOfNodes-i] = u; i++;
        }

        return route;
    }
}
