package de.unistuttgart;

public class Node {
    // nodeID
    int id;
    // nodeID2
    int id2;
    // latitude
    double lati;
    // longitude
    double longi;
    // elevation
    int elev;

    Node(int id) {
        this.id = id;
    }

    Node(int id, double lati, double longi) {
        this.id = id;
        this.lati = lati;
        this.longi = longi;
    }

    public int getId() {
        // TODO Auto-generated method stub
        return id;
    }


}
