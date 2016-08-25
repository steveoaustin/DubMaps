package dubMaps;

import java.util.Set;
import java.util.TreeSet;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	/*
	 * Abstraction function: Node is represented as a name 
	 * and has a set of all edges for which it is a parent 
     * 
     * Representation Invariant: Node has a name and a non-null
     * set of edges for which it is the parent 
  	 */
	
	private final Set<Edge<T>> edges;                   
	private final String name;
	private static int pathCount = 0;
	private final boolean DEBUG = true;
	
	/**
	 * Creates a new node object
	 * @throws IllegalArgumentException Indicates node with this name is null
	 * @effects adds name to name and sets this.name to name
	 * @param name the new node's name
	 */
	public Node(String name) {
		if (name == null) {
			throw new IllegalArgumentException("All Nodes must hold a non-null name");
		}
		edges = new TreeSet<Edge<T>>();
		this.name = name;
		checkRep();
	}
	
	
	public Node() {
		this("Path " + Integer.toString(pathCount));
		pathCount++;
	}
	
	/**
	 * Creates a new edge object with this as parent.
	 * Checks edges for an identical edge. If none found, adds to edges.
	 * If child or label are null, does nothing 
	 * @modifies this
	 * @effects adds a new edge to edges 
	 * @param child the edge's child node
	 * @param label the edge's label
	 */
	public void addEdge(Node<T> child, T label) {
		checkRep();
		if (child == null || label == null) {
			return;
		}
		Edge<T> temp = new Edge<T>(this, child, label);
		
		if(edges.contains(temp)) {
			return;
		}
		
		edges.add(temp); //add temp if it is a unique edge
		checkRep();
	}
	
	/**
	 * Return a set of the node's edges
	 * @return edges set of all edges for which this is parent
	 */
	public Set<Edge<T>> getEdges() {
		return (TreeSet<Edge<T>>) edges;
	}
	
	/**
	 * Returns node's name
	 * @return name the node's name
	 */
	public String toString() {
		return name;
	}
	
	@Override
	/**
	 * standard equals function
	 * @param o the object this is compared to
	 * @return a boolean indicating if this is equal to o
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Node<?>)) {
			return false;
		}
		Node<?> n = (Node<?>) o;
		if (this.name.equals(n.name)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Standard hashCode function
	 * @return an int identifier unique to this object
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds
	 */
	public void checkRep() {
		if (DEBUG) {
			assert (this.name != null);
			assert (this.edges != null);
			for (Edge<T> edge: edges) {
				assert(edge != null);
			}
		}	
	}

	/**
	 * standard compare to function
	 * @param other node to be compared to this
	 * @return an int representing the name of this - other
	 */
	public int compareTo(Node<T> other) {
		return  this.name.compareTo(other.name);
	}
}












