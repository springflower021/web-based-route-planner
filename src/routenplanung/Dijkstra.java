package routenplanung;
import java.util.*;

public class Dijkstra {
/*	private List<Node> nodes;
	private List<Edge> edges;
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	private Map<Node, Node> predecessors;
	private Map<Node, Integer> cost;
	
	public Dijkstra(Graph graph) {	
		this.nodes = new ArrayList<Node>(graph.getNodes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}
	
	public void execute(Node source) {
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		cost = new HashMap<Node, Integer>();
		predecessors = new HashMap<Node, Node>();
		
	    cost.put(source, 0);
	    unSettledNodes.add(source);
	    while (unSettledNodes.size() > 0) {
	      Node node = getNodeWithLowestCost(unSettledNodes);
	      settledNodes.add(node);
	      unSettledNodes.remove(node);
	      findMinimalCosts(node);
	    }
	
	}

	private void findMinimalCosts(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
	
	    for (Node target : adjacentNodes) {
		      if (getminimalCost(target) > getminimalCost(node) + getCost(node, target)) {
		    	  cost.put(target, getminimalCost(node) + getCost(node, target));
		    	  predecessors.put(target, node);
		    	  unSettledNodes.add(target);
		      }
		    }
		
	}

	private int getCost(Node node, Node target) {
		for (Edge edge : edges) {
			if (edge.getSource() == node.getId()
					&& edge.getTarget() == target.getId()) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Node> getNeighbors(Node node) {	
		List<Node> neighbors = new ArrayList<Node>();
		for (Edge edge : edges) {
			
			if (edge.getSource() == node.getId()
					&& !isSettled(edge.getTarget())) {
				for (Node n : nodes) {
					if (n.getId() == edge.getTarget()) {
						neighbors.add(n);				
					}
				}
			}
		}
		return neighbors;
	}

	private Node getNodeWithLowestCost(Set<Node> nodes) {
		Node minimum = null;
		
	    for (Node node : nodes) {
		      if (minimum == null) {
		        minimum = node;
		      } else {
		        if (getminimalCost(node) < getminimalCost(minimum)) {
		          minimum = node;
		        }
		      }
		    }
		 return minimum;		
		
	}

	private boolean isSettled(int Id) {
		for (Node s : settledNodes) {
			if (s.getId() == Id) {
				return true;
			}
		}
		return false;
	}

	private int getminimalCost(Node target) {
		Integer d = cost.get(target);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	
	public LinkedList<Node> getPath(Node target) {
		LinkedList<Node> path = new LinkedList<Node>();
		Node step = target;
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		Collections.reverse(path);
		return path;
	}
*/
    private List<Node> shortestPath = new LinkedList<>();
    
    private Integer distance = Integer.MAX_VALUE;
	
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
	    source.setDistance(0);

	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();

	    unsettledNodes.add(source);

	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry < Node, Integer> adjacencyPair: 
	          currentNode.getAdjacentNodes().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            Integer edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    return graph;
	}

	private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
		// TODO Auto-generated method stub
	    Node lowestDistanceNode = null;
	    int lowestDistance = Integer.MAX_VALUE;
	    for (Node node: unsettledNodes) {
	        int nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	
	private static void CalculateMinimumDistance(Node evaluationNode,
			  Integer edgeWeigh, Node sourceNode) {
			    Integer sourceDistance = sourceNode.getDistance();
			    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			        evaluationNode.setDistance(sourceDistance + edgeWeigh);
			        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			        shortestPath.add(sourceNode);
			        evaluationNode.setShortestPath(shortestPath);
			    }
			}
}
