package routenplanung;

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
	
    Edge(int src, int trg, int weight) {
        this.src = src;
        this.trg = trg;
        this.weight = weight;
    } 
    
    Edge(int src, int trg, int weight, int type, int maxSpeed) {
        this.src = src;
        this.trg = trg;
        this.weight = weight;
        this.type = type;
        this.maxSpeed = maxSpeed;
    } 

    @Override
    public String toString() {
        return this.src + " ——> " + this.trg + " (" + this.weight + ")";
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
}