package routeplanner;

public class Edge {
	// srcIDX
	int src;
	// trgIDX
	int trg;
	// cost
	int weight;
	// type
	private	int type;
	// max speed
	private	int maxSpeed;

	
    public Edge(int src, int trg, int weight) {
        this.src = src;
        this.trg = trg;
        this.weight = weight;
    } 
    
	
    public Edge(int src, int trg, int weight, int type, int maxSpeed) {
        this.src = src;
        this.trg = trg;
        this.weight = weight;
        this.type = type;
        this.maxSpeed = maxSpeed;
    }

	public int getSource() {
		// TODO Auto-generated method stub
		return src;
	}

	public int getTarget() {
		// TODO Auto-generated method stub
		return trg;
	}

	public int getWeight() {
		// TODO Auto-generated method stub
		return weight;
	} 
	
	@Override
	public String toString() {
		return Integer. toString(src) + " " + Integer. toString(trg);
	}

    
}
