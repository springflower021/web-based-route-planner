package de.unistuttgart;

import java.util.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Node> nodes = Arrays.asList(new Node(0), new Node(1), new Node(2),
				new Node(3), new Node(4));
		List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 9), new Edge(0, 2, 8), new Edge(0, 4, 7),
                new Edge(2, 0, 6), new Edge(2, 1, 4), new Edge(2, 4, 4),
                new Edge(3, 2, 3), new Edge(4, 1, 2), new Edge(4, 3, 1));

        Graph graph = new Graph(5, 9, nodes, edges);
 
        Graph.printGraph(graph);

	}	
}
