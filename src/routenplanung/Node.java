package routenplanung;

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
	
	Node(int id,double lati, double longi) {
		this.id = id;
		this.lati = lati;
		this.longi = longi;
	}
	
	Node(int id, int id2, double lati, double longi, int elev) {
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
	
	public double getLati() {
		// TODO Auto-generated method stub
		return lati;
	}
	
	public double getLongi() {
		// TODO Auto-generated method stub
		return lati;
	}

}
