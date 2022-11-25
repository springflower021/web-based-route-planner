package de.unistuttgart;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.IntToDoubleFunction;

public class Dijkstra {
    public static int[] dijkstra(Graph graph, int startNode, int destinationNode) {
        Timestamp tstamp1 = new Timestamp(System.currentTimeMillis());
        int[] shortestWay = createShortestWay(graph, destinationNode, dijkstraFunction(graph, startNode));
        Timestamp tstamp2 = new Timestamp(System.currentTimeMillis());
        System.out.println("\nAusführungszeit Dijkstra: "+ (tstamp2.getTime()-tstamp1.getTime()) + " ms");
        return shortestWay;
    }


    private static int[] dijkstraFunction(Graph graph, int startNode) {
        int[] distance = new int[graph.numberOfNodes];
        int[] previous = new int[graph.numberOfNodes];
        int status = 0;

        System.out.println("Ausführung Dijkstra:");

        PriorityQueue<Integer> Q = new PriorityQueue<>(graph.numberOfNodes, (n1, n2) -> Integer.compare(distance[n1], distance[n2]));
        initialize(graph, startNode, distance, previous, Q);

        while (!Q.isEmpty()) {

            int u = Q.poll();
            if(Q.size()%100 == 0) {
                status = 100 - (Q.size() * 100) / graph.numberOfNodes;
                System.out.print("\r" + status + " %");
            }
            for (int i = graph.offset[u]; i < graph.offset[u + 1]; i++) {
                final int v = graph.edge[1][i];
                final int distanceUV = graph.edge[2][i];
                if (Q.contains(v)) {
                    distanceUpdate(u, v, distance, previous, distanceUV, Q);
                }
            }
        }
        return previous;
    }

    private static void initialize(Graph graph, int startNode, int[] distance, int[] previous, PriorityQueue<Integer> Q) {
        for (int i = 0; i < graph.numberOfNodes; i++) {
            if (i == startNode) {
                distance[startNode] = 0;
            } else {
                distance[i] = Integer.MAX_VALUE;
            }
            previous[i] = -1;
            Q.add(i);
        }

    }

    private static void distanceUpdate(int u, int v, int[] distance, int[] previous, int distanceUV, PriorityQueue<Integer> Q) {
        if ((distance[u] + distanceUV) < distance[v]) {
            distance[v] = distance[u] + distanceUV;
            Q.remove(v);
            Q.add(v);
            previous[v] = u;
        }

    }

    private static int[] createShortestWay(Graph graph, int destinationNode, int[] previous) {
        int[] route = new int[graph.numberOfNodes];
        Arrays.fill(route, -1);
        route[graph.numberOfNodes - 1] = destinationNode;
        int u = destinationNode;
        int i = 2;
        while (previous[u] != -1) {
            u = previous[u];
            route[graph.numberOfNodes - i] = u;
            i++;
        }

        return route;
    }
}
