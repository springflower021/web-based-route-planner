package de.unistuttgart;

import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //String pathname = "toy.fmi.txt";
        //String pathname = "germany.fmi.txt";
        String pathname = "stgtregbz.fmi.txt";


        System.out.println("Datei: " + pathname);
        Graph graph = new Graph(pathname);
        int[] way = Dijkstra.dijkstra(graph, graph.findNearestNode(48.783380,9.181280,true),graph.findNearestNode(48.774071,9.170642,false));
        System.out.print("Weg: ");
        for (int j : way) {
            if (j != -1) {
                System.out.print("->"+j);
            }
        }
        System.out.print("\n");


    }
}
