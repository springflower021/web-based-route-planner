package routenplanung;

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
 
        for(Node d: nodes) {
        	for(Edge e:edges) {
        		if(d.id == e.src) {
                    adjList.get(d.id).add(new Edge(e.src, e.trg, e.weight));
        		}
        	}
        }
    }
       
    public static void printGraph(Graph graph) {
        int src = 0;
        int n = graph.adjList.size();
     
        while (src < n) {
            for (Edge edge: graph.adjList.get(src)) {
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
	
	public double getDistance(Node point, Node node) {
		final int R= 6371; // Radius of the earth
		double lat1 = point.getLati();
		double lat2 = node.getLati();
		double lon1 = point.getLongi();
		double lon2 = node.getLongi();
		
	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double Distance = R * c * 1000;
		return Distance;	
	}

}
