package routenplanung;
import java.io.*;
import java.util.*;

public class Benchmark {

	public static void main(String[] args)  {
		// read parameters (parameters are expected in exactly this order)
		String graphPath = args[1];
		double lon = Double.parseDouble(args[3]);
		double lat = Double.parseDouble(args[5]);
		String quePath = args[7];
		int sourceNodeId = Integer.parseInt(args[9]);

		// run benchmarks
		System.out.println("Reading graph file and creating graph data structure (" + graphPath + ")");
		long graphReadStart = System.currentTimeMillis();
		// TODO: read graph here
		int n = 0;
		int m = 0;
        List<Node> nodes = new ArrayList<Node>();
        List<Edge> edges = new ArrayList<Edge>();
		Graph graph = new Graph(n, m, nodes, edges);
		try (LineNumberReader rnr = new LineNumberReader(new FileReader(graphPath))) {
			String currLine;
			int currIndex = 0;
			while((currLine = rnr.readLine()) != null) {
				
				if (rnr.getLineNumber() == 5) {
					n = Integer.parseInt(currLine);	
				}
				else if (rnr.getLineNumber() == 6) {
					m = Integer.parseInt(currLine);
				}
				else if(rnr.getLineNumber() >= 7 && rnr.getLineNumber() <= 6 + n) {
					int nodeId = Integer.parseInt(currLine.substring(0, currLine.indexOf(" ")));
					currIndex = currLine.indexOf(" ") + 1;
					int nodeId2 = Integer.parseInt(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
					currIndex = currLine.indexOf(" ", currIndex) + 1;
					double lati = Double.valueOf(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
					currIndex = currLine.indexOf(" ", currIndex) + 1;
					double longi = Double.valueOf(currLine.substring(currIndex, currLine.indexOf(" ", currIndex)));
					currIndex = currLine.indexOf(" ", currIndex) + 1;
					int elev = Integer.parseInt(currLine.substring(currIndex));
					Node node = new Node(nodeId, nodeId2, lati, longi, elev);
					nodes.add(node);
				}
				else if(rnr.getLineNumber() >= 7 + n) {
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
			graph = new Graph(n, m, nodes, edges);
		} catch (Exception e) {
			System.out.println("Exception...");
			e.printStackTrace();
		}
		long graphReadEnd = System.currentTimeMillis();
		System.out.println("\tgraph read took " + (graphReadEnd - graphReadStart) + "ms");

		System.out.println("Setting up closest node data structure...");
		// TODO: set up closest node data structure here
		Node point= new Node(0, lat, lon);
		Collections.sort(nodes, (n1, n2) -> Double.compare(Math.abs(n1.getLati() - lat), Math.abs(n2.getLati() - lat)));	
		Node closestNode = nodes.get(0);
	    double minDistance = graph.getDistance(point, nodes.get(0));
		
		System.out.println("Finding closest node to coordinates " + lon + " " + lat);
		long nodeFindStart = System.currentTimeMillis();
		double[] coords = {0.0, 0.0};
		// TODO: find closest node here and write coordinates into coords
		int i = 1;
	    while(Math.abs(nodes.get(i).getLati() - lat) <= minDistance) {
	    	if (graph.getDistance(point, nodes.get(i)) < minDistance) {
	    		minDistance = graph.getDistance(point, nodes.get(i));
	    		closestNode = nodes.get(i);
	    	}
	    	i++;
	    }
	    coords[0] = closestNode.lati;
	    coords[1] = closestNode.longi;

		long nodeFindEnd = System.currentTimeMillis();
		System.out.println("\tfinding node took " + (nodeFindEnd - nodeFindStart) + "ms: " + coords[0] + ", " + coords[1]);

		System.out.println("Running one-to-one Dijkstras for queries in .que file " + quePath);
		long queStart = System.currentTimeMillis();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(quePath))) {
			String currLine;
			while ((currLine = bufferedReader.readLine()) != null) {
				int oneToOneSourceNodeId = Integer.parseInt(currLine.substring(0, currLine.indexOf(" ")));
				int oneToOneTargetNodeId = Integer.parseInt(currLine.substring(currLine.indexOf(" ") + 1));
				int oneToOneDistance = -42;
				// TODO set oneToOneDistance to the distance from
				// oneToOneSourceNodeId to oneToOneSourceNodeId as computed by
				// the one-to-one Dijkstra
				System.out.println(oneToOneDistance);
			}
		} catch (Exception e) {
			System.out.println("Exception...");
			e.printStackTrace();
		}
		long queEnd = System.currentTimeMillis();
		System.out.println("\tprocessing .que file took " + (queEnd - queStart) + "ms");

		System.out.println("Computing one-to-all Dijkstra from node id " + sourceNodeId);
		long oneToAllStart = System.currentTimeMillis();
		// TODO: run one-to-all Dijkstra here
		long oneToAllEnd = System.currentTimeMillis();
		System.out.println("\tone-to-all Dijkstra took " + (oneToAllEnd - oneToAllStart) + "ms");

		// ask user for a target node id
		System.out.print("Enter target node id... ");
		int targetNodeId = (new Scanner(System.in)).nextInt();
		int oneToAllDistance = -42;
		// TODO set oneToAllDistance to the distance from sourceNodeId to
		// targetNodeId as computed by the one-to-all Dijkstra
		System.out.println("Distance from " + sourceNodeId + " to " + targetNodeId + " is " + oneToAllDistance);
	}

}
