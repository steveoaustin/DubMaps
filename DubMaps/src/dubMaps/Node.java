package dubMaps;

import java.util.Set;
import java.util.TreeSet;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	/*
	 * Abstraction function: Node is represented as a unique value 
	 * and has a set of all edges for which it is a parent 
     * 
     * Representation Invariant: Node has a unique name and a non-null
     * set of edges for which it is the parent 
  	 */
	
	private final Set<Edge<T>> edges;                   
	private final T value;
	private final boolean DEBUG = false;
	
	/**
	 * Creates a new node object
	 * @throws IllegalArgumentException Indicates node with this value is null
	 * @effects adds value to value and sets this.value to value
	 * @param value the new node's value
	 */
	public Node(T value) {
		if (value == null) {
			throw new IllegalArgumentException("All Nodes must hold a non-null value");
		}
		edges = new TreeSet<Edge<T>>();
		this.value = value;
		checkRep();
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
	public void addEdge(Node<T> child, E label) {
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
	 * Returns node's value as a string
	 * @return value the node's value
	 */
	public String toString() {
		return value.toString();
	}
	
	@Override
	/**
	 * standard equals function
	 * @param o the object this is compared to
	 * @return a boolean indicating if this is equal to o
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Node<?, ?>)) {
			return false;
		}
		Node<?, ?> n = (Node<?, ?>) o;
		if (this.value.equals(n.value)) {
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
		return value.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds
	 */
	public void checkRep() {
		if (DEBUG) {
			assert (this.value != null);
			assert (this.edges != null);
			for (Edge<T> edge: edges) {
				assert(edge != null);
			}
		}	
	}

	/**
	 * standard compare to function
	 * @param other node to be compared to this
	 * @return an int representing the value of this - other
	 */
	public int compareTo(Node<T> other) {
		return  this.value.compareTo(other.value);
	}
}












