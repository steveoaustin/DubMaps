package dubMaps;

public class Edge<T extends Comparable<T>, E> implements Comparable<Edge<T, E>> {
	/*
	 * Abstraction function: an Edge represents a directed edge in a graph 
     * 
     * Representation Invariant: Edge has a non-null parent node, child node, and label
  	 */
	
	private final Node<T, E> parent;
	private final Node<T, E> child;
	private final E label;
	private final boolean DEBUG = false;
	
	/**
	 * Creates a new Edge object
	 * @effects constructs a new Edge with parent, child, and label
	 * @throws IllegalArgumentException Indicates parent,child,or label is null
	 * @param parent the parent node
	 * @param child the child node
	 * @param label the edge's label
	 */
	public Edge(Node<T, E> parent, Node<T, E> child, E label) {
		if (parent == null || child == null|| label == null) {
			throw new IllegalArgumentException("cannot create edge with null values");
		}
		this.parent = parent;
		this.child = child;
		this.label = label;
		checkRep();
	}
	
	/**
	 * returns the edge's parent
	 * @return parent the edge's parent node
	 */
	public Node<T, E> getParent() {
		return parent;
	}
	
	/**
	 * returns the edge's child
	 * @return child the edge's child node
	 */
	public Node<T, E> getChild() {
		return child;
	}
	
	/**
	 * returns the edge's label
	 * @return label the edge's label
	 */
	public E getLabel() {
		return label;
	}
	
	/**
	 * returns a string representation of the edge
	 * @return string representation
	 */
	public String toString() {
		return child + "(" + label + ")";
	}
	
	@Override
	/**
	 * standard equals function 
	 * @param o the object to be compared to this
	 * @return boolean indicating equality
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Edge<?, ?>)) {
			return false;
		}
		Edge<?, ?> e = (Edge<?, ?>) o;
		if (!this.label.equals(e.label)) {
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
		String code = label + parent.toString() + child.toString();
		return code.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds
	 */
	public void checkRep() {
		if (DEBUG) {
			assert (this.label != null);
			assert (this.parent != null);
			assert (this.child != null);
		}
	}

	/**
	 * standard compareTo function
	 * @param e The edge this is compared to
	 * @return an int indicating the value of this - e
	 */
	public int compareTo(Edge<T, E> e) {
		if (this.child.toString().equals(e.child.toString())) {
			return this.label.toString().compareTo(e.label.toString());
		}
		return this.child.toString().compareTo(e.child.toString());
	}
}
















