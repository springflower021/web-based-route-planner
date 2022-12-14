package de.unistuttgart;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        int choice;
        double latitudeStart;
        double longitudeStart;
        double latitudeDestination;
        double longitudeDestination;
        int startNode;
        int destinationNode;
        Dijkstra.DijkstraReturn dijkstraReturn;
        long tsmp1;
        long tsmp2;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please insert the name of the graph file");
        String pathname=scanner.nextLine();
        tsmp1=System.currentTimeMillis();
        Graph graph = new Graph(pathname);
        tsmp2=System.currentTimeMillis();
        System.out.println("Took: "+((tsmp2-tsmp1)) +"ms");

        do{
            System.out.println("///TEST MODULE///");
            System.out.println("0: Exit");
            System.out.println("1: Perform Dijkstra OneToAll: Position to Position");
            System.out.println("2: Perform Dijkstra OneToAll: Node to Node");
            System.out.println("3: Perform Dijkstra OneToOne: Position to Position");
            System.out.println("4: Perform Dijkstra OneToOne: Node to Node");
            System.out.println("5: FindNextNode");

            choice=scanner.nextInt();

            switch (choice){
                case 1:
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart= scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart= scanner.nextDouble();
                    startNode=graph.findNearestNode(latitudeStart,longitudeStart);
                    tsmp1 = System.currentTimeMillis();
                    dijkstraReturn = Dijkstra.dijkstraOneToAll(graph, startNode);
                    tsmp2 = System.currentTimeMillis();
                    System.out.println("Took "+ ((tsmp2-tsmp1)) +"ms");

                    break;
                case 2:
                    System.out.println("Please insert start node");
                    startNode= scanner.nextInt();
                    System.out.println("Please insert the destination node");
                    destinationNode=scanner.nextInt();
                    tsmp1 = System.currentTimeMillis();
                    dijkstraReturn = Dijkstra.dijkstraOneToAll(graph, startNode);
                    tsmp2 = System.currentTimeMillis();
                    System.out.println("Took "+ ((tsmp2-tsmp1)) +"ms");
                    break;
                case 3:
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart= scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart= scanner.nextDouble();
                    System.out.println("Please insert the latitude of the destination position");
                    latitudeDestination=scanner.nextDouble();
                    System.out.println("Please insert the longitude of the destination position");
                    longitudeDestination=scanner.nextDouble();
                    startNode=graph.findNearestNode(latitudeStart,longitudeStart);
                    destinationNode=graph.findNearestNode(latitudeDestination,latitudeStart);
                    dijkstraReturn = Dijkstra.dijkstraOneToOne(graph,startNode,destinationNode);
                    System.out.print("Weg: ");
                    for (int j : Dijkstra.createShortestWay(graph,destinationNode,dijkstraReturn)) {
                        if (j != -1) {
                            System.out.print("->"+j);
                        }
                    }
                    System.out.print("\n");
                    System.out.println("Distance: "+dijkstraReturn.distance()[destinationNode]);
                    break;
                case 4:
                    System.out.println("Please insert start node");
                    startNode= scanner.nextInt();
                    System.out.println("Please insert the destination node");
                    destinationNode=scanner.nextInt();
                    dijkstraReturn = Dijkstra.dijkstraOneToOne(graph,startNode,destinationNode);
                    System.out.print("Weg: ");
                    for (int j : Dijkstra.createShortestWay(graph,destinationNode,dijkstraReturn)) {
                        if (j != -1) {
                            System.out.print("->"+j);
                        }
                    }
                    System.out.print("\n");
                    System.out.println("Distance: "+dijkstraReturn.distance()[destinationNode]);
                    break;
                case 5:
                    System.out.println("Please insert the latitude of the start position");
                    latitudeStart= scanner.nextDouble();
                    System.out.println("Please insert the longitude of the start position");
                    longitudeStart= scanner.nextDouble();
                    startNode=graph.findNearestNode(latitudeStart,longitudeStart);
                    System.out.println("The next node is: "+startNode);
                    break;
            }

        }while(choice!=0);







        System.out.println("Please choose which Dijkstra should be performed");
        System.out.println("'a' for one to all and 'o' for one to one ");
        char dijkstraType = (char) scanner.nextByte();





        //String pathname = "toy.fmi.txt";
        //String pathname = "germany.fmi.txt";
        //String pathname = "stgtregbz.fmi.txt";


        /*System.out.println("Datei: " + pathname);
        Graph graph = new Graph(pathname);
        int[] way = Dijkstra.dijkstra(graph, graph.findNearestNode(48.783380,9.181280,true),graph.findNearestNode(48.774071,9.170642,false));
        System.out.print("Weg: ");
        for (int j : way) {
            if (j != -1) {
                System.out.print("->"+j);
            }
        }
        System.out.print("\n");

        public static int[] dijkstra(Graph graph, int startNode, int destinationNode) {
            Timestamp tstamp1 = new Timestamp(System.currentTimeMillis());
            Dijkstra.DijkstraReturn d = dijkstraFunction(graph, startNode);
            int[] shortestWay = createShortestWay(graph, destinationNode, d);
            Timestamp tstamp2 = new Timestamp(System.currentTimeMillis());
            System.out.println("\nAusführungszeit Dijkstra: "+ ((tstamp2.getTime()-tstamp1.getTime())/1000.0) + "s");
            System.out.println(d.distance[destinationNode]);
            return shortestWay;
        }
        */

    }
}
