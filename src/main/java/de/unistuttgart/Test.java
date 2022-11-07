package de.unistuttgart;

import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //String pathname = "toy.fmi.txt";
        String pathname = "germany.fmi.txt";

        Graph graph = Graph.readGraphFromFile(pathname);
        Graph.printGraph(graph);

    }
}
