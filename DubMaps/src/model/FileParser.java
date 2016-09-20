package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Representation invariant: Campus parser holds a properly formed CampusGraph constructed
 * from formatted data files which contain information about campus buildings and the paths
 * that connect them to each other 
 * 
 * Abstraction function: CampusParser is represented as a valid CampusGraph
 */
public class FileParser {
	private final MapGraph graph;
	
	/**
	 * Parses input files into node and location data
	 * @requires input files are properly formatted and without duplicates
	 * @param buildingsFile File containing building information
	 * @param pathsFile File containing path information 
	 * @modifies this
	 */
	public FileParser (String buildingsFile, String pathsFile, String buildingLabelsFile){
		graph = new MapGraph();
		try {
			parseBuildings(buildingsFile);
			parsePaths(pathsFile);
			parseLabels(buildingLabelsFile);
		} catch (Exception e) { /*ignore*/ }
	}
	
	/**
	 * Returns graph
	 * @return graph: the CampusGraph constructed from building and path files
	 */
	public MapGraph getGraph() {
		return graph;
	}
	
	
	private void parseLabels(String filename) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	        	
	            // Parse the data, throwing an exception for malformed lines.
	            inputLine = inputLine.replace("\"", "");
	            String[] tokens = inputLine.split("\t");
	            if (tokens.length != 4) {
	                throw new Exception("Line should contain exactly 3 tabs: " + inputLine);
	            }
	            
	            // save location information
	            String shortName = tokens[0];
	            String longName = tokens[1];
	            double x = Double.parseDouble(tokens[2]);
	            double y = Double.parseDouble(tokens[3]);

	            // Create a location from the parsed data
	            Location location = new Location(shortName, longName, x, y);
	            
	            // add new label to the graph
	            graph.addLabel(location);
	        }
		} catch (IOException e) { 
			System.err.println(e.toString());
			e.printStackTrace(System.err);	
        } finally {
			if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
		}
	}
	
	/**
	 * Stores all path data in filename
	 * @param filename The file of path information to be parsed
	 * @requires filename is formatted correctly
	 */
	private void parsePaths(String filename) {
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filename));
	        
	        // Construct a map of campus path information and nodes
	        String inputLine;
	        Node<Location> parent = null;
	        Node<Location> child = null;
	        String[] tokens;        	
        	double x;
        	double y;
        	
	        while ((inputLine = reader.readLine()) != null) {	        	
	        	// no tab indicates the node is a new parent node
	            if (!inputLine.startsWith("\t")) {
	            	
	            	// split tokens and save them in x,y coordinates
	            	tokens = inputLine.split(",");
	            	x = Double.parseDouble(tokens[0]);
	            	y = Double.parseDouble(tokens[1]);
	            	
	            	// check if the parent node has already been found in the buildings file
	            	if (graph.getNode(x, y) != null) { 
	            		parent = graph.getNode(x, y);
	            	} else { 
	            		// node is a not in the buildings file; represent as a path	            		
	            		parent = new Node<Location>(x, y);

	            		// add new node to the graph
	            		graph.add(parent);
	            	}
	            } else { //add edges to parent node
	            	// parse data
	            	inputLine = inputLine.trim();
	            	tokens = inputLine.split("[, ]"); //split x, y, and distance
	            	tokens[1] = tokens[1].substring(0, tokens[1].length() - 1); //remove colon
	            	
	            	// save x y coordinates and length
	            	x = Double.parseDouble(tokens[0]);
	            	y = Double.parseDouble(tokens[1]);
	            	double label = Double.parseDouble(tokens[2]);
	            	
	            	// check if child node has already been found in the buildings file
	            	if (graph.getNode(x, y) != null) { 
	            		child = graph.getNode(x, y);
	            	} else {
	            		// node is a not in the buildings file; represent as a path	            		
	            		child = new Node<Location>(x, y); 
	            		
	            		// add new node to the graph
	            		graph.add(child);
	            	}
	            	// add new edges
	            	parent.addEdge(child, label);
            		child.addEdge(parent, label);
	            }
	        }	        
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }    
	}
	
	/**
	 * Stores all building info in filename
	 * @param filename The file of building information to be parsed
	 * @throws Exception Indicates filename is improperly formatted 
	 */
	private void parseBuildings(String filename) throws Exception {
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filename));
	        
	        // Construct a map of campus path information and nodes
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	        	
	            // Parse the data, throwing an exception for malformed lines.
	            inputLine = inputLine.replace("\"", "");
	            String[] tokens = inputLine.split("\t");
	            if (tokens.length != 4) {
	                throw new Exception("Line should contain exactly 3 tabs: " + inputLine);
	            }
	            
	            // save location information
	            String shortName = tokens[0];
	            String longName = tokens[1];
	            double x = Double.parseDouble(tokens[2]);
	            double y = Double.parseDouble(tokens[3]);

	            // Create a location from the parsed data
	            Location location = new Location(shortName, longName, x, y);
	            Node<Location> building = new Node<Location>(location);
	            
	            // add new building to the graph
	            graph.add(building);
	        }
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }    
	} 
}






















