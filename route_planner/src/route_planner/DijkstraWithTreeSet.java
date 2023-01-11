package route_planner;

//import com.google.common.graph.ValueGraph;
import java.util.*;

import route_planner.Dijkstra.DijkstraReturn;


public class DijkstraWithTreeSet {

	  /**
	   * Finds the shortest path from {@code source} to {@code target}.
	   *
	   * @param graph the graph
	   * @param source the source node
	   * @param target the target node
	   * @param <N> the node type
	   * @return the shortest path; or {@code null} if no path was found
	   */
	  
	  public record NodeWrapper(int[] distance, int[] previous) {
      }
	  
	/*
	  public static List<Integer> findShortestPathOneToOne(
			  Graph graph, int source, int target) {
	    Map<Integer, NodeWrapperForTreeSet> nodeWrappers = new HashMap<>();
	    TreeSet<NodeWrapperForTreeSet> queue = new TreeSet<>((n1, n2) -> Integer.compare(n1.getTotalDistance(), n2.getTotalDistance()));
	    Set<Integer> shortestPathFound = new HashSet<>();
	    

	    // Add source to queue
	    NodeWrapperForTreeSet sourceWrapper = new NodeWrapperForTreeSet(source, 0, null);
	    nodeWrappers.put(source, sourceWrapper);
	    queue.add(sourceWrapper);

	    while (!queue.isEmpty()) {
	      NodeWrapperForTreeSet nodeWrapper = queue.pollFirst();
	      System.out.print(nodeWrapper.getNode());
	      int node = nodeWrapper.getNode();
	      shortestPathFound.add(node);

	      // Have we reached the target? --> Build and return the path
	      if (node == target) {
	    	  return buildPath(nodeWrapper);
	      }

	      // Iterate over all neighbors
	      Set<Integer> neighbors = graph.adjacentNodes(node);
	      for (int neighbor : neighbors) {
	        // Ignore neighbor if shortest path already found
	        if (shortestPathFound.contains(neighbor)) {
	          continue;
	        }

	        // Calculate total distance from start to neighbor via current node
	        int distance = 0;
	        if (graph.edgeValue(node, neighbor) >= 0) {
	        	distance = graph.edgeValue(node, neighbor);
	        }
	        int totalDistance = nodeWrapper.getTotalDistance() + distance;

	        // Neighbor not yet discovered?
	        NodeWrapperForTreeSet neighborWrapper = nodeWrappers.get(neighbor);
	        if (neighborWrapper == null) {
	          neighborWrapper = new NodeWrapperForTreeSet(neighbor, totalDistance, nodeWrapper);
	          nodeWrappers.put(neighbor, neighborWrapper);
	          queue.add(neighborWrapper);
	        }

	        // Neighbor discovered, but total distance via current node is shorter?
	        // --> Update total distance and predecessor
	        else if (totalDistance < neighborWrapper.getTotalDistance()) {
	          // The position in the TreeSet won't change automatically;
	          // we have to remove and reinsert the node.
	          // Because TreeSet uses compareTo() to identity a node to remove,
	          // we have to remove it *before* we change the total distance!
	          queue.remove(neighborWrapper);
	          neighborWrapper.setTotalDistance(totalDistance);
	          neighborWrapper.setPredecessor(nodeWrapper);
	          queue.add(neighborWrapper);
	        }
	      }
	    }
	  
	 // All nodes were visited but the target was not found
	    return null;
	 
	  }
	  
	  
	  
	  public static List<Integer> findShortestPathOneToAll(
			  Graph graph, int source) {
		    Map<Integer, NodeWrapperForTreeSet> nodeWrappers = new HashMap<>(graph.numberOfNodes);
		    TreeSet<NodeWrapperForTreeSet> queue = new TreeSet<>((n1, n2) -> Integer.compare(n1.getTotalDistance(), n2.getTotalDistance()));
		    Set<Integer> shortestPathFound = new HashSet<>(graph.numberOfNodes);
		    int node = source;
		    
		    // Add source to queue
		    NodeWrapperForTreeSet sourceWrapper = new NodeWrapperForTreeSet(source, 0, null);
		    nodeWrappers.put(source, sourceWrapper);
		    queue.add(sourceWrapper);
		    NodeWrapperForTreeSet nodeWrapper = null;

		    while (!queue.isEmpty()) {
		      nodeWrapper = queue.pollFirst();
		      node = nodeWrapper.getNode();
		      shortestPathFound.add(node);

		      // Iterate over all neighbors
		      Set<Integer> neighbors = graph.adjacentNodes(node);
		      for (int neighbor : neighbors) {
		        // Ignore neighbor if shortest path already found
		        if (shortestPathFound.contains(neighbor)) {
		          continue;
		        }

		        // Calculate total distance from start to neighbor via current node
		        int distance = 0;
		        if (graph.edgeValue(node, neighbor) >= 0) {
		        	distance = graph.edgeValue(node, neighbor);
		        }
		        int totalDistance = nodeWrapper.getTotalDistance() + distance;

		        // Neighbor not yet discovered?
		        NodeWrapperForTreeSet neighborWrapper = nodeWrappers.get(neighbor);
		        if (neighborWrapper == null) {
		          neighborWrapper = new NodeWrapperForTreeSet(neighbor, totalDistance, nodeWrapper);
		          nodeWrappers.put(neighbor, neighborWrapper);
		          queue.add(neighborWrapper);
		        }

		        // Neighbor discovered, but total distance via current node is shorter?
		        // --> Update total distance and predecessor
		        else if (totalDistance < neighborWrapper.getTotalDistance()) {
		          // The position in the TreeSet won't change automatically;
		          // we have to remove and reinsert the node.
		          // Because TreeSet uses compareTo() to identity a node to remove,
		          // we have to remove it *before* we change the total distance!
		          queue.remove(neighborWrapper);
		          neighborWrapper.setTotalDistance(totalDistance);
		          neighborWrapper.setPredecessor(nodeWrapper);
		          queue.add(neighborWrapper);
		        }
		      }
		    }

		    return buildPath(nodeWrapper);
		 
	  }
	  */
	  
	  public static NodeWrapper findShortestPathOneToOne(
			  Graph graph, int source, int target) {
		  int node = source;
		  NodeWrapper nodeWrapper = new NodeWrapper(new int[graph.numberOfNodes], new int[graph.numberOfNodes]);
	      TreeSet<Integer> queue = new TreeSet<>((n1, n2) -> {
	    		    	int compare = Integer.compare(nodeWrapper.distance[n1], nodeWrapper.distance[n2]);
	    		    	if (compare == 0) {
	    		    		compare = Integer.compare(n1, n2);
	    		    	}
	    		    	return compare;
	    		    });
	      Set<Integer> shortestPathFound = new HashSet<>();
	      
		  // Add source to queue
		  nodeWrapper.distance[source] = 0;
	      nodeWrapper.previous[source] = -1;
		  queue.add(source);
		  
		  
		  while (!queue.isEmpty()) {
			  node = queue.pollFirst();
		      shortestPathFound.add(node);
		      // Have we reached the target? --> Build and return the path
		      if (node == target) {
		    	  return nodeWrapper;
		      }


		      // Iterate over all neighbors
		      for (int i = graph.offset[node]; i < graph.offset[node + 1]; i++) {
		    	  int neighbor = graph.edge[1][i];
		          // Ignore neighbor if shortest path already found
		    	  if (shortestPathFound.contains(neighbor)) {
		          continue;		        
		    	  }
		    		
		    	  // Calculate total distance from start to neighbor via current node		        			    	
		    	  int distance = graph.edge[2][i];		        		    	 
		    	  int totalDistance = nodeWrapper.distance[node] + distance;   	  
		    	  
		          // Neighbor not yet discovered?	    	 
		    	  if (!queue.contains(neighbor)) {
		    		  nodeWrapper.distance[neighbor] = totalDistance;
		    		  nodeWrapper.previous[neighbor] = node;
		              queue.add(neighbor);  
		    	  }

		          // Neighbor discovered, but total distance via current node is shorter?
		          // --> Update total distance and predecessor
		          else if (totalDistance < nodeWrapper.distance[neighbor]) {
		          // The position in the TreeSet won't change automatically;
		          // we have to remove and reinsert the node.
		          // Because TreeSet uses compareTo() to identity a node to remove,
		          // we have to remove it *before* we change the total distance!
		          queue.remove(neighbor);
		          nodeWrapper.distance[neighbor] = totalDistance;
		          nodeWrapper.previous[neighbor] = node;
		          queue.add(neighbor);
		        }
		      }
		    }
		  
		  // All nodes were visited but the target was not found
		  
		  return null;
	  }
	  
	  public static NodeWrapper findShortestPathOneToAll(
			  Graph graph, int source) {
		  int node = source;
		  NodeWrapper nodeWrapper = new NodeWrapper(new int[graph.numberOfNodes], new int[graph.numberOfNodes]);
	      TreeSet<Integer> queue = new TreeSet<>((n1, n2) -> {
		    	int compare = Integer.compare(nodeWrapper.distance[n1], nodeWrapper.distance[n2]);
		    	if (compare == 0) {
		    		compare = Integer.compare(n1, n2);
		    	}
		    	return compare;
		    });
	      Set<Integer> shortestPathFound = new HashSet<>();
	      
		  // Add source to queue
		  nodeWrapper.distance[source] = 0;
	      nodeWrapper.previous[source] = -1;
		  queue.add(source);

		  while (!queue.isEmpty()) {
			  node = queue.pollFirst();
		      shortestPathFound.add(node);

		      // Iterate over all neighbors
		      for (int i = graph.offset[node]; i < graph.offset[node + 1]; i++) {
		    	  int neighbor = graph.edge[1][i];
		          // Ignore neighbor if shortest path already found
		    	  if (shortestPathFound.contains(neighbor)) {
		          continue;		        
		    	  }
		    		
		    	  // Calculate total distance from start to neighbor via current node		        			    	
		    	  int distance = graph.edge[2][i];		        		    	 
		    	  int totalDistance = nodeWrapper.distance[node] + distance;   	  
		    	  
		          // Neighbor not yet discovered?	    	 
		    	  if (!queue.contains(neighbor)) {
		    		  nodeWrapper.distance[neighbor] = totalDistance;
		    		  nodeWrapper.previous[neighbor] = node;
		              queue.add(neighbor);  
		    	  }

		          // Neighbor discovered, but total distance via current node is shorter?
		          // --> Update total distance and predecessor
		          else if (totalDistance < nodeWrapper.distance[neighbor]) {
		          // The position in the TreeSet won't change automatically;
		          // we have to remove and reinsert the node.
		          // Because TreeSet uses compareTo() to identity a node to remove,
		          // we have to remove it *before* we change the total distance!
		          queue.remove(neighbor);
		          nodeWrapper.distance[neighbor] = totalDistance;
		          nodeWrapper.previous[neighbor] = node;
		          queue.add(neighbor);
		        }
		      }
		    }
		  
		  return nodeWrapper;
		 
	  }
	  
	  /*
	  private static List<Integer> buildPath(NodeWrapperForTreeSet nodeWrapper) {
	    List<Integer> path = new ArrayList<>();
	    while (nodeWrapper != null) {
	      path.add(nodeWrapper.getNode());
	      nodeWrapper = nodeWrapper.getPredecessor();
	    }
	    Collections.reverse(path);
	    return path;
	  }
	  */
	  
	  public static List<Integer> createShortestPath(int destinationNode, NodeWrapper nodeWrapper) {
		  List<Integer> path = new ArrayList<>(); 
		  int current = destinationNode;
		  if (nodeWrapper == null) {
			  return null;
		  }
		  while(current!= -1) {
			  path.add(current);		  
			  current = nodeWrapper.previous[current];	  
		  }  
		  Collections.reverse(path);
		  return path;
	  }	
	  /*
	    public static int[] createShortestWay(Graph graph, int destinationNode, NodeWrapper nodeWrapper) {
	        int[] route = new int[graph.numberOfNodes];
	        Arrays.fill(route, -1);
	        route[graph.numberOfNodes - 1] = destinationNode;
	        int u = destinationNode;
	        int i = 2;
	        while (nodeWrapper.previous[u] != -1) {
	            u = nodeWrapper.previous[u];
	            route[graph.numberOfNodes - i] = u;
	            i++;
	        }

	        return route;
	    }
	    */
}