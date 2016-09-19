package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/*
 * Abstraction function:graph is represented as a Set<Node<CampusLocation>>
 * Each element is a valid node
 * 
 * Representation Invariant: graph does not contain any null nodes
 * and each node contains non-null set of edges for 
 * which it is the parent node.
 */
public class CampusGraph {
	
	private final Set<CampusLocation> labels;
	private final Set<Node<CampusLocation>> nodes;
	private final Set<Node<CampusLocation>> destinationNodes;
	private final boolean DEBUG = true;
	
	/**
	 * Instantiates a new graph.
	 */
	public CampusGraph() {
		labels = new TreeSet<CampusLocation>();
		nodes = new HashSet<Node<CampusLocation>>();
		destinationNodes = new TreeSet<Node<CampusLocation>>();
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
	 * Returns the closest node to x,y within a limited distance, or null of none are close enough
	 * @param x: The x coordinate to be searched for
	 * @param y: The y coordinate to be searched for
	 * @param maxDistance: The maximum acceptable distance from the node
	 * @return The closest node to x,y within maxDistance, null if none found
	 */
	public Node<CampusLocation> getClosestBuilding(int x, int y, int maxDistance) {
		Node<CampusLocation> closest = null;
		
		// apply the distance formula on each building node to find the closest one
		for (Node<CampusLocation> n: destinationNodes) {
			int distance = (int) Math.sqrt(Math.pow(x - n.getLocation().getX(), 2.0) + 
					                       Math.pow(y - n.getLocation().getY(), 2.0));
			if (distance < maxDistance) {
				closest = n;
				maxDistance = distance;
			}
		}
		return closest;
	}
	
	/**
	 * Returns a list of campusLocations belonging to destinations in the graph
	 * @return temporary list of locations
	 */
	public List<CampusLocation> getDestinations() {
		List<CampusLocation> result = new ArrayList<CampusLocation>();
		for(Node<CampusLocation> n: destinationNodes)
			result.add(n.getLocation());
		
		return result;
	}
	
	/**
	 * Returns the location label associated with this building, or null if none found
	 * @param building: The building to search for
	 * @return the building's label, or null if not found
	 */
	public CampusLocation getLabel(Node<CampusLocation> building) {
		for(CampusLocation c: labels) 
			if (c.getName().equals(building.getLocation().getName().substring(0, 3)))
				return c;
		
		return null;
	}
	
	/**
	 * Returns a list of locations for building labels
	 * @return a list of campusLocations used as labels
	 */
	public List<CampusLocation> getLabels() {
		List<CampusLocation> result = new ArrayList<CampusLocation>();
		for(CampusLocation c: labels)
			result.add(c);
		
		return result;
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
		if (node == null || this.contains(node))
			return;
		
		nodes.add(node);
		// check if the node is named as a building/destination
		if (!node.getLocation().getLongName().substring(0, 4).equals("Path"))
			destinationNodes.add(node);
		checkRep();
	}
	
	/**
	 * Adds label to the list of labels if it is non-null and not already in the graph
	 * @param label the location label to be added
	 */
	public void addLabel(CampusLocation label) {
		if (label == null || this.labels.contains(label))
			return;
		
		labels.add(label);
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
				return current.getPath();  // found destination node! return path 
			}
			
			// destination not found, avoid re-finding current 
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





















