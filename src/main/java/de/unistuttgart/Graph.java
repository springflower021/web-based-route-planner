package de.unistuttgart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntToDoubleFunction;

public class Graph {

    int[][] edge;
    double[][] node;
    int[] offset;

    int numberOfEdges;
    int numberOfNodes;

    public Graph(String pathname) {

        /**
         * Create Scanner and read file with english notation (point instead of comma for float)
         */

        Scanner scan;

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

        double twoPercentOfNodes = numberOfNodes*0.2;
        double twoPercentOfEdges = numberOfEdges*0.2;


        this.node = new double[2][numberOfNodes];
        this.edge = new int[3][numberOfEdges];
        this.offset = new int[numberOfNodes + 1];

        System.out.println("Nodes einlesen:");

        int nodesActuell =0;
        System.out.print("0.0 %");

        for (int i = 0; i < numberOfNodes; i++) {

            int readId = scan.nextInt();
            scan.nextLong();
            double readLatitude = scan.nextDouble();
            double readLongitude = scan.nextDouble();
            scan.nextInt();

            node[0][readId] = readLatitude;
            node[1][readId] = readLongitude;

            if((i%100)==0){
                nodesActuell = (i*100)/numberOfNodes;
                System.out.print("\r"+nodesActuell+" %");
            }
        }
        System.out.println("\r100 % ");
        System.out.println("Edges einlesen:");
        System.out.print("0.0 %");
        int edgesActuell =0;

        for (int j = 0; j < numberOfEdges; j++) {

            int readSrc = scan.nextInt();
            int readTrg = scan.nextInt();
            int readWeight = scan.nextInt();
            scan.nextInt();
            scan.nextInt();

            edge[0][j] = readSrc;
            edge[1][j] = readTrg;
            edge[2][j] = readWeight;

            if((j%100)==0){
                edgesActuell = (j*100)/numberOfEdges;
                System.out.print("\r"+edgesActuell+" %");
            }


        }
        System.out.println("\r100 % ");
        System.out.println("Offset berechnen:");
        System.out.print("0.0 %");

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
            if((k%100)==0){
                offsetAktuell= (k*100)/numberOfEdges;
                System.out.print("\r"+offsetAktuell+" %");
            }

        }
        System.out.println("\r100 % ");

        }
    }



