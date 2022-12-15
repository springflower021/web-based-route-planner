package routeplanner;

import java.util.*;
import java.util.Map.Entry;

public class Dijkstra {
	
    private List<Node> nodes;
	private List<Edge> edges;
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	private Map<Node, Node> predecessors;
	private Map<Node, Integer> distance;
	
	public Dijkstra(Graph graph) {	
		this.nodes = new ArrayList<Node>(graph.getNodes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}
	
	public void executeOneToOne(Node source, Node target) {
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Integer>();
		predecessors = new HashMap<Node, Node>();
		
	    distance.put(source, 0);
	    unSettledNodes.add(source);
	    while (!isSettled(target.getId()) && unSettledNodes.size() > 0) {
	      Node node = getNodeWithLowestCost(unSettledNodes);
	      settledNodes.add(node);
	      unSettledNodes.remove(node);
	      findMinimalDistances(node);
	    }
	
	}
	
	public void executeOneToAll(Node source) {
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Integer>();
		predecessors = new HashMap<Node, Node>();
		
	    distance.put(source, 0);
	    unSettledNodes.add(source);
	    while (unSettledNodes.size() > 0) {
	      Node node = getNodeWithLowestCost(unSettledNodes);
	      settledNodes.add(node);
	      unSettledNodes.remove(node);
	      findMinimalDistances(node);
	    }
	
	}

	private void findMinimalDistances(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
	
	    for (Node target : adjacentNodes) {
		      if (getminimalDistance(target) > getminimalDistance(node) + getCost(node, target)) {
		    	  distance.put(target, getminimalDistance(node) + getCost(node, target));
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
		        if (getminimalDistance(node) < getminimalDistance(minimum)) {
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

	public int getminimalDistance(Node target) {
		Integer d = distance.get(target);
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
	
	public void printPath(Node target) {
		LinkedList<Node> path = getPath(target);
		if (!(path == null)) {
			System.out.println("Path:");
			for(Node n: path) {
				if (n.equals(path.get(0))) {
					System.out.print(n.getId());
					}
			    else {
			    	System.out.println("-->" + n.getId());
			    	}
			}
		} else {
			System.out.println("the path is illegal.");
        }
	}
}
