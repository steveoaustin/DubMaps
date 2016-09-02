package dubMaps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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
	 * Returns a node holding target as its shortName, null if not in graph
	 * @param name: the target node's location shortName
	 * @return The node with shortName name
	 */
	public Node<CampusLocation> getNode(String name) {
		for (Node<CampusLocation> node: nodes) {
			if (node.getLocation().getName().equals(name))
				return node;
		}
		return null;
	}
	
	/**
	 * Returns a node with matching x y coordinates, null if not in graph
	 * @param x: target x coordinate
	 * @param y: target y coordinate
	 * @return The node located at x, y
	 */
	public Node<CampusLocation> getNode(double x, double y) {
		for (Node<CampusLocation> node: nodes) {
			if (node.getLocation().getX() == x && node.getLocation().getY() == y)
				return node;
		}
		return null;
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
	 * Standard toString method
	 */
	public String toString() {
		String result = "";
		for (Node<CampusLocation> n: nodes) {
			result += n.toString() + "\n";
			for (Edge<CampusLocation> e: n.getEdges())
				result += e.toString() + "\n";
			result += "\n";
		}
		return result;
	}
	
	/**
	 * returns the shortest path from start to dest, null if no path exists
	 * @param start The start node
	 * @param dest The destination node
	 * @return a list of edges leading from start to dest, null if none
	 * @throws IllegalArgumentException Indicates null parameter
	 */
	public List<Edge<CampusLocation>> getPath(Node<CampusLocation> start, Node<CampusLocation> dest) {	
		if (start == null || dest == null) {
			throw new IllegalArgumentException("Null input");
		}
		Set<Node<CampusLocation>> finished = new HashSet<Node<CampusLocation>>();
		Queue<DijkPath> active = new PriorityQueue<DijkPath>();
		active.add(new DijkPath(start));
		
		// find paths branching off the end node until the queue is empty
		while (!active.isEmpty()) {
			DijkPath current = active.remove();
			Node<CampusLocation> node = current.getDest();

			if (finished.contains(node)) { continue; }  //skip already found node
			if (node.equals(dest)) {
				return current.getPath();  // found dest node! return path 
			}
			
			// dest not found, avoid re-finding current 
			finished.add(node);
			
			// continue paths to node's children if they have not been found
			for (Edge<CampusLocation> e: node.getEdges()) {
				if (finished.contains(e.getChild())) { continue; }  // skip found nodes
				
				// build next DijkPath and add it to the queue
				Double newCost = current.getCost() + e.getLength();
				List<Edge<CampusLocation>> newPath = new ArrayList<Edge<CampusLocation>>();
				newPath.addAll(current.getPath());
				newPath.add(e);
				DijkPath next = new DijkPath(newCost, e.getChild(), newPath);
				active.add(next);
			}
		}
		return null;
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





















