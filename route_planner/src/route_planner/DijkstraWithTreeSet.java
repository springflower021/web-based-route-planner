package route_planner;

//import com.google.common.graph.ValueGraph;
import java.util.*;

import route_planner.Dijkstra.DijkstraReturn;


public class DijkstraWithTreeSet {
	  
	  public record NodeWrapper(int[] distance, int[] previous) {
      }
	  
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

}