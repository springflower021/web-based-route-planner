package de.unistuttgart;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntToDoubleFunction;

public class Graph {

    int[][] edge;
    double[] nodeLat;
    double[] nodeLong;
    int[] offset;
    int numberOfEdges;
    int numberOfNodes;
    Integer[] sortedLatitude;



    public Graph(String pathname) {

        /**
         * Create Scanner and read file with english notation (point instead of comma for float)
         */

        Scanner scan;
        Timestamp tstamp1 = new Timestamp(System.currentTimeMillis());
        try {
            scan = new Scanner(new File(pathname));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        scan.useLocale(Locale.ENGLISH);

        /**
         * Reading the file line by line:
         * "readingline" represents the current line of the text file as string
         */

        String readingLine = new String(scan.nextLine());

        /**
         * The first lines of the text file, which don't contain the actual graph will be skipped
         */

        while (readingLine.contains("#") || readingLine.isEmpty()) {
            readingLine = scan.nextLine();
        }

        /**
         * The number of nodes and edges of the graph will be saved in an additional variable,
         * which will also be used to determine the number of iterations to save single nodes and edges.
         * The single nodes and edges will be saved with their parameters in separate arrays.
         */

        this.numberOfNodes = Integer.parseInt(readingLine);
        readingLine = scan.nextLine();

        this.numberOfEdges = Integer.parseInt(readingLine);

        double twoPercentOfNodes = numberOfNodes * 0.2;
        double twoPercentOfEdges = numberOfEdges * 0.2;


        this.nodeLong = new double[numberOfNodes];
        this.nodeLat = new double[numberOfNodes];
        this.edge = new int[3][numberOfEdges];
        this.offset = new int[numberOfNodes + 1];

        System.out.println("Nodes einlesen:");

        int nodesActuell = 0;
        System.out.print("0.0 %");

        for (int i = 0; i < numberOfNodes; i++) {

            int readId = scan.nextInt();
            scan.nextLong();
            double readLatitude = scan.nextDouble();
            double readLongitude = scan.nextDouble();
            scan.nextInt();

            nodeLat[readId] = readLatitude;
            nodeLong[readId] = readLongitude;

            if ((i % 100) == 0) {
                nodesActuell = (i * 100) / numberOfNodes;
                System.out.print("\r" + nodesActuell + " %");
            }
        }
        System.out.println("\r100 % ");
        System.out.println("Edges einlesen:");
        System.out.print("0.0 %");
        int edgesActuell = 0;

        for (int j = 0; j < numberOfEdges; j++) {

            int readSrc = scan.nextInt();
            int readTrg = scan.nextInt();
            int readWeight = scan.nextInt();
            scan.nextInt();
            scan.nextInt();

            edge[0][j] = readSrc;
            edge[1][j] = readTrg;
            edge[2][j] = readWeight;

            if ((j % 100) == 0) {
                edgesActuell = (j * 100) / numberOfEdges;
                System.out.print("\r" + edgesActuell + " %");
            }


        }
        System.out.println("\r100 % ");
        Timestamp tstamp2 = new Timestamp(System.currentTimeMillis());
        System.out.println("\nAusführungszeit " + pathname+ " eingelesen : "+ ((tstamp2.getTime()-tstamp1.getTime())/1000.0) + "s");

        int offsetAktuell = 0;

        offset[0] = 0;
        int current = 0;
        for (int k = 0; k < (numberOfEdges); k++) {
            if (edge[0][k] == current) {
                offset[current + 1]++;
            } else {
                current++;
                offset[current + 1] = offset[current];
                k--;
            }

        }

        sortedLatitude = new Integer[numberOfNodes];
        for (int i=0; i<numberOfNodes; i++) {
            sortedLatitude[i]= i;
        }
        Comparator<Integer> latComp = (left,right)->Double.compare(nodeLat[left],nodeLat[right]);
        Arrays.sort(sortedLatitude,latComp);

    }

    /**
     *
     * @param givenLatitude
     * @param givenLongitude
     * @param goal Is one for finding start node and zero for finding destination node
     * @return
     */

    public int findNearestNode(double givenLatitude, double givenLongitude) {
        int begin = 0;
        int end = numberOfNodes - 1;
        int nearestNodeLatitude = binarySearch(sortedLatitude,nodeLat, begin, end, givenLatitude);
        int distanceToMid = 1;
        int firstNode = sortedLatitude[nearestNodeLatitude];
        int secondNode = Integer.MAX_VALUE;
        int thirdNode = Integer.MAX_VALUE;
        int iterationsWithNoNewValue = 0;
        do{
            secondNode=sortedLatitude[nearestNodeLatitude+distanceToMid];
            thirdNode=sortedLatitude[nearestNodeLatitude-distanceToMid];

            if(distance(secondNode,givenLatitude,givenLongitude)< distance(firstNode,givenLatitude,givenLongitude)){
                firstNode=secondNode;
            }
            else if (distance(thirdNode,givenLatitude,givenLongitude)< distance(firstNode,givenLatitude,givenLongitude)){
                firstNode=thirdNode;
            }
            else{
                iterationsWithNoNewValue ++;
            }
            distanceToMid++;
        }while(iterationsWithNoNewValue<10);


        return firstNode;
    }

    private static int binarySearch(Integer[] sortedLatitude, double[] nodeLat,int fromIndex, int toIndex, double key) {
        int nearestValue = -1;
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            @SuppressWarnings("rawtypes")
            Comparable midVal = (Comparable) nodeLat[sortedLatitude[mid]];
            @SuppressWarnings("unchecked")
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found

            nearestValue = mid;
        }
        return nearestValue;  // key not found.
    }
    
    private double distance(int nodeStart, int nodeDestination){
        double x = nodeLong[nodeStart]-nodeLong[nodeDestination];
        double y = nodeLat[nodeStart]-nodeLat[nodeDestination];
        return Math.sqrt((y*y)+(x*x));
    }
    private double distance(int nodeDestination, double givenLat, double givenLong){
        double x = givenLong-nodeLong[nodeDestination];
        double y = givenLat-nodeLat[nodeDestination];
        return Math.sqrt((y*y)+(x*x));
    }


}



