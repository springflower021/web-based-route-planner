package routeplanner;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
	    // number of nodes
		private int n;
		// number of edges
	    private int m;
	    private List<Node> nodes = new ArrayList<>();
	    private List<Edge> edges = new ArrayList<>(); 
		
	    static class Vertex {
	        int value, weight;
	        Vertex(int value, int weight)  {
	            this.value = value;
	            this.weight = weight;
	        }
	    };

		List<List<Vertex>> adjList = new ArrayList<>();
		
		 public Graph(String pathname) {
		    	try (LineNumberReader rnr = new LineNumberReader(new FileReader(pathname))) {
				String currLine;
				int currIndex = 0;
				while((currLine = rnr.readLine()) != null) {
					/*
					 * the number on the sixth line indicates the number of nodes
					 */
					if (rnr.getLineNumber() == 6) {
						this.n = Integer.parseInt(currLine);	
				//	this.n = 644199;
					}
					/*
					 * the number on the seventh line indicates the number of edges
					 */
					else if (rnr.getLineNumber() == 7) {
						this.m = Integer.parseInt(currLine);
				//		System.out.println(m);
				//	this.m = 1305996;
					}
					/*
					 * the data from the eighth line to the (7 + n)th line
					 * are stored in nodes
					 */
					if(rnr.getLineNumber() >= 8 && rnr.getLineNumber() <= 7 + n) {
						int nodeId = Integer.parseInt(currLine.substring(0, currLine.indexOf(" ")));
						currIndex = currLine.indexOf(" ") + 1;
						// System.out.println(nodeId);
						double nodeId2 = Double.parseDouble(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
						currIndex = currLine.indexOf(" ", currIndex) + 1;
						// System.out.println(nodeId2);
						double lati = Double.parseDouble(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
						currIndex = currLine.indexOf(" ", currIndex) + 1;
						// System.out.println(lati);
						double longi = Double.parseDouble(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
						currIndex = currLine.indexOf(" ", currIndex) + 1;
						// System.out.println(longi);
						int elev = Integer.parseInt(currLine.substring(currIndex));
						// System.out.println(elev);
						Node node = new Node(nodeId, nodeId2, lati, longi, elev);
						nodes.add(node);
					}
					
					/*
					 * the data from the (8 + n)th line forward
					 * are stored in edges
					 */
					else if(rnr.getLineNumber() >= 8 + n) {
						int src = Integer.parseInt(currLine.substring(0, currLine.indexOf(" ")));
						currIndex = currLine.indexOf(" ") + 1;
						int trg = Integer.parseInt(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
						currIndex = currLine.indexOf(" ", currIndex) + 1;
						int weight = Integer.parseInt(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
						currIndex = currLine.indexOf(" ", currIndex) + 1;
	                    int type = Integer.parseInt(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
						currIndex = currLine.indexOf(" ", currIndex) + 1;
	                    int maxSpeed = Integer.parseInt(currLine.substring(currIndex));;
	                    Edge edge = new Edge(src, trg, weight, type, maxSpeed);
	                    edges.add(edge);
					}	
				}
				/*
				 * create the Graph using the collected data
				 */
				/*
				for (int i = 0; i < n; i++) {
		            this.adjList.add(i, new ArrayList<>());
		        }
		        for(Node d: this.nodes) {
		        	for(Edge e: this.edges) {
		        		if(d.id == e.src) {
		                    this.adjList.get(d.id).add(new Edge(e.src, e.trg, e.weight));
		        		}
		        	}
		        }
		        */
			} catch (Exception e) {
				System.out.println("Exception...");
				e.printStackTrace();
			}
	    }
       
	/*
    public static void printGraph(Graph graph) {
        int src = 0;
        int n = graph.adjList.size();
     
        while (src < n) {
            for (Vertex edge: graph.adjList.get(src)) {
                System.out.print("Vertex:" + src + " ==> " + edge.value + 
                        " (" + edge.weight + ")\t");
            }
     
            System.out.println();
            src++;
        }
    }
    */
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public Node getNode(int id) {
		Node node = getNodes().stream().filter(s -> s.getId() == id).findAny().orElse(null);
		return node;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	public Node findNearestNode(double lat, double lon) {
		Node point= new Node(0, lat, lon);
		List<Node> newNodes = new ArrayList<>(nodes);
		/*
		 * sort the nodes with the latitude difference to the given point
		 */
		Collections.sort(newNodes, (n1, n2) -> Double.compare(Math.abs(n1.getLati() - lat), Math.abs(n2.getLati() - lat)));	
		Node closestNode = newNodes.get(0);
	    final double minDistance = getDistance(point, newNodes.get(0));
	    double currMinDistance = minDistance;
	    /*
	     * only the nodes whose latitude difference is smaller than current minDistance 
	     * will be considered.
	     */
	    List<Node> targetNodes = newNodes.stream().filter(n -> Math.abs(n.getLati() - lat) <= minDistance).collect(Collectors.toList());
	    for (int i = 0; i < targetNodes.size() && Math.abs(targetNodes.get(i).getLati() - lat) <= currMinDistance; i++) {
	    	double currDistance = getDistance(point, targetNodes.get(i));
	    	if (currDistance < currMinDistance) {
	    		currMinDistance = currDistance;
	    		closestNode = targetNodes.get(i);
	    	}
	    }
	    return closestNode;
	}
	
	/*
	 * calculate the distance between the given point to a node
	 */
	// Radius of the earth
	final int R= 6371;
	
	public double getDistance(Node point, Node node) {
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
