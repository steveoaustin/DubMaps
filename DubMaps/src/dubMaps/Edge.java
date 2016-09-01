package dubMaps;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
	/*
	 * Abstraction function: an Edge represents a directed edge in a graph 
     * 
     * Representation Invariant: Edge has a non-null parent node, child node, and nonzero length
  	 */
	
	private final Node<T> parent;
	private final Node<T> child;
	private final double length;
	private final boolean DEBUG = true;
	
	/**
	 * Creates a new Edge object
	 * @effects constructs a new Edge with parent, child, and length
	 * @throws IllegalArgumentException Indicates parent or child is null, or length is zero
	 * @param parent the parent node
	 * @param child the child node
	 * @param length the edge's length
	 */
	public Edge(Node<T> parent, Node<T> child, double length) {
		if (parent == null || child == null|| length == 0.0) {
			throw new IllegalArgumentException("cannot create edge with null values");
		}
		this.parent = parent;
		this.child = child;
		this.length = length;
		checkRep();
	}
	
	/**
	 * returns the edge's parent
	 * @return parent the edge's parent node
	 */
	public Node<T> getParent() {
		return parent;
	}
	
	/**
	 * returns the edge's child
	 * @return child the edge's child node
	 */
	public Node<T> getChild() {
		return child;
	}
	
	/**
	 * returns the edge's length
	 * @return length the edge's length
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * returns a string representation of the edge
	 * @return string representation
	 */
	public String toString() {
		return parent.toString() + "\n(" + length + ")\n" + child.toString();
	}
	
	@Override
	/**
	 * standard equals function 
	 * @param o the object to be compared to this
	 * @return boolean indicating equality
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Edge<?>)) {
			return false;
		}
		Edge<?> e = (Edge<?>) o;
		if (this.length != e.length) {
			return false;
		} else if (!this.parent.toString().equals(e.parent.toString())) {
			return false;
		} else if (!this.child.toString().equals(e.child.toString())) {
			return false;
		}
		return true;
	}
	
	/**
	 * Standard hashCode function
	 * @return an int that all objects equal to this will also
	 */
	public int hashCode() {
		String code = length + parent.toString() + child.toString();
		return code.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds
	 */
	public void checkRep() {
		if (DEBUG) {
			assert (this.length != 0.0);
			assert (this.parent != null);
			assert (this.child != null);
		}
	}

	/**
	 * standard compareTo function
	 * @param e The edge this is compared to
	 * @return an integer indicating the value of this.toString - e.toString
	 */
	public int compareTo(Edge<T> e) {
		if (this.child.toString().equals(e.child.toString())) {
			return (int) ((1000 *this.length) - (1000 *e.length));
		}
		return this.child.toString().compareTo(e.child.toString());
	}
}
















