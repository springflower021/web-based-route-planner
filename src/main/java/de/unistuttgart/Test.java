package de.unistuttgart;

import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String pathname = "toy.fmi.txt";
        //String pathname = "germany.fmi.txt";

        Graph graph = new Graph(pathname);
        System.out.println("Graph eingelesen");
        int[] way = Dijkstra.dijkstra(graph, 1,3);
        for (int j : way) {
            if (j != -1) {
                System.out.println(j);
            }
        }

    }
}
