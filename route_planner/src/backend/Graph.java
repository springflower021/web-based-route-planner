package backend;

import java.io.*;
import java.util.*;

public class Graph {

    int[][] edge;
    public double[] nodeLat;
    public double[] nodeLong;
    int[] offset;
    int numberOfEdges;
    int numberOfNodes;
    Integer[] sortedLatitude;


    public Graph(String pathname) throws IOException {

        /*
         * Create Scanner and read file with english notation (point instead of comma for float)
         */

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(pathname));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //scan.useLocale(Locale.ENGLISH);

        /*
         * Reading the file line by line:
         * "readingLine" represents the current line of the text file as string
         */

        String readingLine = bufferedReader.readLine();

        /*
         * The first lines of the text file, which don't contain the actual graph will be skipped
         */

        while (readingLine.contains("#") || readingLine.isEmpty()) {
            readingLine = bufferedReader.readLine();
        }

        /*
         * The number of nodes and edges of the graph will be saved in an additional variable,
         * which will also be used to determine the number of iterations to save single nodes and edges.
         * The single nodes and edges will be saved with their parameters in separate arrays.
         */

        this.numberOfNodes = Integer.parseInt(readingLine);
        readingLine = bufferedReader.readLine();

        this.numberOfEdges = Integer.parseInt(readingLine);


        this.nodeLong = new double[numberOfNodes];
        this.nodeLat = new double[numberOfNodes];
        this.edge = new int[3][numberOfEdges];
        this.offset = new int[numberOfNodes + 1];


        int readId;
        double readLatitude;
        double readLongitude;
        String nodeLine;
        String[] nodeArray;


        for (int i = 0; i < numberOfNodes; i++) {

            nodeLine = bufferedReader.readLine();
            nodeArray = nodeLine.split(" ");
            readId = Integer.parseInt(nodeArray[0]);
            readLatitude = Double.parseDouble(nodeArray[2]);
            readLongitude = Double.parseDouble(nodeArray[3]);

            nodeLat[readId] = readLatitude;
            nodeLong[readId] = readLongitude;

        }

        int readSrc;
        int readTrg;
        int readWeight;
        String edgeLine;
        String[] edgeArray;

        for (int j = 0; j < this.numberOfEdges; j++) {

            edgeLine = bufferedReader.readLine();
            edgeArray = edgeLine.split(" ");
            readSrc = Integer.parseInt(edgeArray[0]);
            readTrg = Integer.parseInt(edgeArray[1]);
            readWeight = Integer.parseInt(edgeArray[2]);

            edge[0][j] = readSrc;
            edge[1][j] = readTrg;
            edge[2][j] = readWeight;


        }

        offset[0] = 0;
        int current = 0;
        for (int k = 0; k < this.numberOfEdges; k++) {
            if (edge[0][k] == current) {
                offset[current + 1]++;
            } else {
                current++;
                offset[current + 1] = offset[current];
                k--;
            }
        }
        
        sortedLatitude = new Integer[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            sortedLatitude[i] = i;
        }
        Comparator<Integer> latComp = (left, right) -> Double.compare(nodeLat[left], nodeLat[right]);
        Arrays.sort(sortedLatitude, latComp);

        bufferedReader.close();
    }
    

    //may have to change it later because there could be two nodes of the same distance!
    public int findNearestNode(double givenLatitude, double givenLongitude) {
        int begin = 0;
        int end = numberOfNodes - 1;
        int nearestNodeLatitude = binarySearch(sortedLatitude, nodeLat, begin, end, givenLatitude);
        int nearestNode = sortedLatitude[nearestNodeLatitude];
        int currentNode;
        double minDistance = distance(nearestNode, givenLatitude, givenLongitude);
        
        /*
         * try to find the nearest node from the index onwards 
         */
        int index = nearestNodeLatitude;
        while (index < end) {
        	index++;         
            currentNode = sortedLatitude[index];            
            if (minDistance < latDifference(currentNode,givenLatitude)) {
            	break;
            } else {
            	double currentDistance = distance(currentNode, givenLatitude, givenLongitude); 
            	if (currentDistance < minDistance) {
            		minDistance = currentDistance;
            		nearestNode = currentNode;
            	}
            }    
        }
        
        /*
         * try to find the nearest node from the index backwards
         */
        index = nearestNodeLatitude;     
        while (index > begin) {
        	index--;            
            currentNode = sortedLatitude[index];           
            if (minDistance < latDifference(currentNode,givenLatitude)) {
            	break;
            } else {
            	double currentDistance = distance(currentNode, givenLatitude, givenLongitude); 
            	if (currentDistance < minDistance) {
            		minDistance = currentDistance;
            		nearestNode = currentNode;
            	}
            }    
        }      
        return nearestNode;
    }
    
    private static int binarySearch(Integer[] sortedLatitude, double[] nodeLat, int fromIndex, int toIndex, double key) {
        int nearestValue = -1;
        int low = fromIndex;
        int high = toIndex;

        while (low <= high) {        	
            int mid = (low + high) >>> 1;
            @SuppressWarnings("rawtypes")
            Comparable midVal = nodeLat[sortedLatitude[mid]];
            @SuppressWarnings("unchecked")
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else {
                return mid; // key found
            }
            nearestValue = mid;
        }
        return nearestValue;  // key not found.
    }


    private double distance(int nodeDestination, double givenLat, double givenLong) {
        double x = givenLong - nodeLong[nodeDestination];
        double y = givenLat - nodeLat[nodeDestination];
        return Math.sqrt((y * y) + (x * x));
    }

    private double latDifference(int node, double givenLat) {
        double delta = Math.abs(givenLat - nodeLat[node]);
        return delta;
    }
    
}
