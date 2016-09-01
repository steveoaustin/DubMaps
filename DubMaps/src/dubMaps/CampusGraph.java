package dubMaps;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CampusGraph {
	/*
	 * Abstraction function:graph is represented as a Set<Node<CampusLocation>>
 	 * Each element is a valid node
     * 
     * Representation Invariant: graph does not contain any null nodes
 	 * and each node contains non-null set of edges for 
 	 * which it is the parent node.
  	 */
	private final Set<Node<CampusLocation>> nodes;
	private final boolean DEBUG = true;
	
	/**
	 * Instantiates a new graph.
	 */
	public CampusGraph() {
		nodes = new HashSet<Node<CampusLocation>>();
		checkRep();
	}
	
	
	/**
	 * Returns whether or not the graph contains node
	 * @param node The node to be searched for
	 * @return if the graph contains node
	 */
	public boolean contains(Node<CampusLocation> node) {
		return nodes.contains(node);
	}
	
	/**
	 * Returns a node holding target as its name, null if not in graph
	 * @param target the target node.toString() name
	 * @return node The node matching target
	 */
	public Node<CampusLocation> getNode(String target) {
		for (Node<CampusLocation> node: nodes) {
			if (node.toString().equals(target)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Returns a set of node string representations stored in this graph
	 * @return nodeValues a TreeSet of all node names
	 */
	public TreeSet<String> getNodeStrings() {
		TreeSet<String> nodeValues = new TreeSet<String>();
		for (Node<CampusLocation> node: nodes) {
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
	public void add(Node<CampusLocation> node) {
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
			for (Node<CampusLocation> node: nodes) {
				assert(node != null);
				assert(((Node<CampusLocation>) node).getEdges() != null);
			}
		}
	}
}





















