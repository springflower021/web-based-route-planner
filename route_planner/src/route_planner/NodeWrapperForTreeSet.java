package route_planner;

/*
public class NodeWrapperForTreeSet<N extends Comparable<N>>
implements Comparable<NodeWrapperForTreeSet<N>> {
	private final int node;
    private int totalDistance;
    private NodeWrapperForTreeSet<N> predecessor;

    NodeWrapperForTreeSet(int node, int totalDistance, NodeWrapperForTreeSet<N> predecessor) {
    this.node = node;
    this.totalDistance = totalDistance;
    this.predecessor = predecessor;
    }

    int getNode() {
    	return node;
    	}
    void setTotalDistance(int totalDistance) {
    	this.totalDistance = totalDistance;
    }

    public int getTotalDistance() {
    	return totalDistance;
    }

    public void setPredecessor(NodeWrapperForTreeSet<N> predecessor) {
    	this.predecessor = predecessor;
    }

    public NodeWrapperForTreeSet<N> getPredecessor() {
    	return predecessor;
    }


    @Override
    public int compareTo(NodeWrapperForTreeSet<N> other) {
    	int compare = Integer.compare(this.totalDistance, other.totalDistance);
    	if (compare == 0) {
    		compare = Integer.compare(this.node, other.node);
    	}
    	return compare;
    }

// Not overriding equals() and hashcode(), to use Object's methods.
// Object's methods use object identity, which is much faster.
// It's sufficient as within the algorithm, we have only one NodeWrapperForTreeSet
// instance per node.

}
*/
/*
public class NodeWrapperForTreeSet<N extends Comparable<N>>
implements Comparable<NodeWrapperForTreeSet<N>> {
	private final int node;
    private int totalDistance;
    private NodeWrapperForTreeSet<N> predecessor;

    NodeWrapperForTreeSet(int node, int totalDistance, NodeWrapperForTreeSet<N> predecessor) {
    this.node = node;
    this.totalDistance = totalDistance;
    this.predecessor = predecessor;
    }

    int getNode() {
    	return node;
    	}
    
    void setTotalDistance(int totalDistance) {
    	this.totalDistance = totalDistance;
    }

    public int getTotalDistance() {
    	return totalDistance;
    }

    public void setPredecessor(NodeWrapperForTreeSet<N> predecessor) {
    	this.predecessor = predecessor;
    }

    public NodeWrapperForTreeSet<N> getPredecessor() {
    	return predecessor;
    }
    
    @Override
    public int compareTo(NodeWrapperForTreeSet<N> other) {
    	int compare = Integer.compare(this.totalDistance, other.totalDistance);
    	if (compare == 0) {
    		compare = Integer.compare(this.node, other.node);
    	}
    	return compare;
    }
    
// Not overriding equals() and hashcode(), to use Object's methods.
// Object's methods use object identity, which is much faster.
// It's sufficient as within the algorithm, we have only one NodeWrapperForTreeSet
// instance per node.
}

*/