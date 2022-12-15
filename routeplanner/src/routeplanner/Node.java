package routeplanner;

import java.util.*;

public class Node {
	// nodeID
	int id;
	// nodeID2
	double id2;
	// latitude 
	double lati;
	// longitude
	double longi;
	// elevation
	int elev;
	
	public Node() {
		
	}
		
	public Node(int id,double lati, double longi) {
		this.id = id;
		this.lati = lati;
		this.longi = longi;
	}
	
	public Node(int id, double id2, double lati, double longi, int elev) {
		this.id = id;
		this.id2 = id2;
		this.lati = lati;
		this.longi = longi;
		this.elev = elev;
	}
    
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	public String toString() {
		return Integer. toString(id);
	}

	public double getLati() {
		// TODO Auto-generated method stub
		return lati;
	}

	public double getLongi() {
		// TODO Auto-generated method stub
		return longi;
	}


}
