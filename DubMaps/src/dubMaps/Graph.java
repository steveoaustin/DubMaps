package dubMaps;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Graph<T> {
	/*
	 * Abstraction function:graph is represented as a HashMap<Node, HashSet<Edge>>
 	 * the keys are nodes in the graph
 	 * values are set of edges for which node is a parent
     * an edge represents a directed edge in the graph
     * 
     * Representation Invariant: graph does not contain any null nodes
 	 * and each node maps to a non-null set of edges for 
 	 * which it is the parent node.
  	 */
	private final Set<Node<T>> nodes;
	private final boolean DEBUG = true;
	
	/**
	 * Instantiates a new graph.
	 */
	public Graph() {
		nodes = new HashSet<Node<T>>();
		checkRep();
	}
	
	
	/**
	 * Returns whether or not the graph contains node
	 * @param node The node to be searched for
	 * @return if the graph contains node
	 */
	public boolean contains(Node<T> node) {
		return nodes.contains(node);
	}
	
	/**
	 * Returns a node holding target as its value, null if not in graph
	 * @param target the target node.toString() value
	 * @return node The node matching target
	 */
	public Node<T> getNode(String target) {
		for (Node<T> node: nodes) {
			if (node.toString().equals(target)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Returns the set of node values stored in this graph
	 * @return nodeValues a TreeSet of all node values
	 */
	public TreeSet<String> getNodes() {
		TreeSet<String> nodeValues = new TreeSet<String>();
		for (Node<T> node: nodes) {
			nodeValues.add(node.toString());
		}
		return nodeValues;
	}
	
	/**
	 * Adds node and its edge list to nodes. 
	 * Does nothing is node is null or is already in the graph
	 * @modifies this
	 * @effects adds node to nodes
	 * @param node the node to be added
	 */
	public void add(Node<T> node) {
		checkRep();
		if (node == null || this.contains(node)) {
			return;
		}
		nodes.add(node);
		checkRep();
	}

	/**
	 * Checks that the representation invariant holds
	 */
	private void checkRep() {
		if (DEBUG) {
			assert (nodes != null);
			for (Node<T> node: nodes) {
				assert(node != null);
				assert(((Node<T>) node).getEdges() != null);
			}
		}
	}
}





















