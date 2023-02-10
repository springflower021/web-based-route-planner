
package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  
import backend.Graph;
import backend.DijkstraWithTreeSet;
import backend.DijkstraWithTreeSet.NodeWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JavaHttpServer {

	public static void main(String args[]) throws IOException {
		// Initialize the Graph
		
		Scanner scanner = new Scanner(System.in);
        System.out.println("Please insert the name of the graph file");
        String pathname = scanner.nextLine();
        Graph graph;
        try {
            graph = new Graph(pathname);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
		
		// Start receiving messages - ready to receive messages!
		try (ServerSocket serverSocket = new ServerSocket(8080)) {
			System.out.println("Server started.\nListening for messages.");
			
			while (true) {
				//Handle a new incoming message
				
				try (Socket client = serverSocket.accept()){
					// client <-- messages queued up in it!!
					System.out.println("Debug: got new message " + client.toString());
					
					// Read the request - listen to the message
					InputStreamReader isr = new InputStreamReader(client.getInputStream());
					
					// Read the first request from the client
					BufferedReader br = new BufferedReader(isr);
					
					StringBuilder request = new StringBuilder();
					
					String line; // Temp variable called line that holds one line at a time of our message
					line = br.readLine();
					while (!(line.isBlank())) {
						request.append(line + "\r\n");
						line = br.readLine();
					}
					
					System.out.println("--REQUEST--");					
					// Decide how we'd like to respond
					    
					// Get the first line of the request
					   String firstLine = request.toString().split("\n")[0];
					// Get the second thing "resource" from the first line (separated by spaces)
					   String resource = firstLine.split(" ")[1];
					// Compare the "resource" to our list of things
					System.out.println(resource);   
				    
					OutputStream clientOutput = client.getOutputStream();
				    
				    if (resource.contains("/requestNearestNode/")) {
				    	String[] arrOfStr = resource.split("/",-1);
				    	int size = arrOfStr.length;
				    	
				    	double lat = Double.parseDouble(arrOfStr[size-2]);
				    	double lng = Double.parseDouble(arrOfStr[size-1]);
				    	int nearestNode = graph.findNearestNode(lat, lng);
				    	double nodeLat = graph.nodeLat[nearestNode];
				    	double nodeLong = graph.nodeLong[nearestNode];
				    	JSONObject node = new JSONObject();
				    	JSONArray JSONArrayCoord = new JSONArray();
				    	JSONArrayCoord.add(0, nodeLat);
				    	JSONArrayCoord.add(1, nodeLong);
				    	node.put("id", nearestNode);
				    	node.put("coordinates", JSONArrayCoord);
				    	System.out.print(node);
				    	
				    	clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
				    	clientOutput.write(("\r\n").getBytes());
				    	clientOutput.write((node.toString().getBytes()));
				    	clientOutput.flush();
				    	
				    } 
				    else if (resource.contains("/requestShortestPath/")){
				    	String[] arrOfStr = resource.split("/",-1);
				    	int size = arrOfStr.length;
				    	int sourceId = Integer.parseInt(arrOfStr[size-2]);
				    	int targetId = Integer.parseInt(arrOfStr[size-1]);
				    	DijkstraWithTreeSet dijkstra = new DijkstraWithTreeSet();
				    	NodeWrapper nodeWrapper = dijkstra.findShortestPathOneToOne(graph, sourceId, targetId);
				    	List<Integer> path = new ArrayList<>();
				    	path = dijkstra.createShortestPath(targetId, nodeWrapper);
				    	//StringBuilder jsonText = new StringBuilder();
				    	JSONArray list = new JSONArray();
				        for (int nodeId : path) {
				        	JSONObject node = new JSONObject();
					    	node.put("latitude", graph.nodeLat[nodeId]);
					    	node.put("longitude", graph.nodeLong[nodeId]);
						    list.add(node);
						    //jsonText.append(node.toString());
				        }
				   
				    	System.out.print(list.toJSONString());
				    	
				    	clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
				    	clientOutput.write(("\r\n").getBytes());
				    	clientOutput.write((list.toJSONString().getBytes()));
				    	clientOutput.flush();
				    
				    } else {
				    	File file = new File("/Users/zhao/Desktop/web-based-route-planner/route_planner/src/index.html");
				    	FileInputStream fis = new FileInputStream(file);
						
					    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
					    clientOutput.write(("\r\n").getBytes());
					    clientOutput.write((fis.readAllBytes()));
					    clientOutput.flush();
				    }
				    
				    
				    // System.out.println(resource);
	  
					client.close();
					
				} catch (Exception e) {
					System.out.println("Error: " + e);
					return;
				}
				
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return;
		}
	}
	

}

