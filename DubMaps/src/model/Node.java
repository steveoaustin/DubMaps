package model;

import java.util.Set;
import java.util.TreeSet;

/*
 * Abstraction function: Node is represented as a CampusLocation 
 * and has a set of all edges for which it is a parent 
 * 
 * Representation Invariant: Node holds a CampusLocation and a non-null
 * set of edges for which it is the parent 
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	
	private final Set<Edge<T>> edges;                   
	private final CampusLocation data;
	private static int pathCount = 0;  // tracks number of path nodes for naming
	private final boolean DEBUG = true;
	
	/**
	 * Creates a new node object
	 * @throws IllegalArgumentException Indicates data is null
	 * @effects sets this.data to data
	 * @param data: the new node's CampusLocation object
	 */
	public Node(CampusLocation data) {
		if (data == null) {
			throw new IllegalArgumentException("All Nodes must hold a non-null CampusLocation");
		}
		edges = new TreeSet<Edge<T>>();
		this.data = data;
		checkRep();
	}
	
	/**
	 * Creates a new node object that represents a point on a path
	 * @param x: the path's x coordinate
	 * @param y: the path's y coordinate
	 */
	public Node(double x, double y) {
		this(new CampusLocation(Integer.toString(pathCount),
				"Path #" + Integer.toString(pathCount), x, y));
		pathCount++;
	}
	
	/**
	 * Creates a new edge object with this as parent.
	 * Checks edges for an identical edge. If none found, adds to edges.
	 * If child or label are null, does nothing 
	 * @modifies this
	 * @effects adds a new edge to edges 
	 * @param child: the edge's child node
	 * @param label: the edge's label
	 */
	public void addEdge(Node<T> child, double length) {
		checkRep();
		if (child == null || length == 0.0) {
			return;
		}
		Edge<T> temp = new Edge<T>(this, child, length);
		
		if(edges.contains(temp)) {
			return;  // do not add temp if it is already an edge
		}
		
		edges.add(temp); //add temp if it is a unique edge
		checkRep();
	}
	
	/**
	 * Return a set of the node's edges
	 * @return edges: set of all edges for which this is parent
	 */
	public Set<Edge<T>> getEdges() {
		return (TreeSet<Edge<T>>) edges;
	}
	
	/**
	 * Returns the node's location
	 * @return data: the node's CampusLocation object
	 */
	public CampusLocation getLocation() {
		return this.data;
	}
	
	/**
	 * Returns node's CampusLocation String representation
	 * @return data.toString
	 */
	public String toString() {
		return data.toString();
	}
	
	@Override
	/**
	 * standard equals function
	 * @param o: the object this is compared to
	 * @return a boolean indicating if this is equal to o
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Node<?>)) {
			return false;
		}
		Node<?> n = (Node<?>) o;
		if (this.data.equals(n.data)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Standard hashCode function
	 * @return an integer identifier unique to this object
	 */
	@Override
	public int hashCode() {
		return data.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds
	 */
	public void checkRep() {
		if (DEBUG) {
			assert (this.data != null);
			assert (this.edges != null);
			for (Edge<T> edge: edges) {
				assert(edge != null);
			}
		}	
	}

	/**
	 * standard compare to function
	 * @param other node to be compared to this
	 * @return an integer representing this.data - other.data
	 */
	public int compareTo(Node<T> other) {
		return  this.data.compareTo(other.data);
	}
}












