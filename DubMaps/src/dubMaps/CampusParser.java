package dubMaps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Representation invariant: Campus parser holds the contents of destination building locations
 * and path locations. If input files are formatted correctly, destinations are saved as buildings
 * and coordinates, and paths are saved as coordinates. each coordinate has a corresponding node
 * 
 * Abstraction function: CampusParser is represented as a set of buildings, a map from coordinates
 * and name location, and a map from location to node. Coordinates, locations, and buildings are 
 * non-null, and each building maps to a location, and each location maps to a node
 */
public class CampusParser {
	CampusGraph graph;
	
	Map<CampusLocation, Node<CampusLocation>> locations;
	Set<String> buildings;
	Map<String, CampusLocation> coordinates;
	
	/**
	 * Parses input files into node and location data
	 * @requires input files are properly formatted and without duplicates
	 * @param buildingsFile File containing building information
	 * @param pathsFile File containing path information 
	 * @modifies this
	 */
	public CampusParser (String buildingsFile, String pathsFile){
		
		graph = new CampusGraph();
		try {
			parseBuildings(buildingsFile);
			parsePaths(pathsFile);
		} catch (Exception e) { /*ignore*/ }
	}
	
	/**
	 * Stores all path data in filename
	 * @param filename The file of path information to be parsed
	 * @requires filename is formatted correctly
	 */
	public void parsePaths(String filename) {
	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filename));
	        
	        // Construct a map of campus path information and nodes
	        String inputLine;
	        Node<CampusLocation> parent = new Node<CampusLocation>(" "); //prevents error
	        String path = "path1";
	        while ((inputLine = reader.readLine()) != null) {
	        	String[] tokens;
	        	Node<CampusLocation> child;
	        	double x;
	        	double y;
	        	//set new parent node
	            if (!inputLine.startsWith("\t")) {
	            	tokens = inputLine.split(",");
	            	x = Double.parseDouble(tokens[0]);
	            	y = Double.parseDouble(tokens[1]);
	            	
	            	if (locations.containsKey(coordinates.get(x + " " + y))) { 
	            		parent = locations.get(coordinates.get(x + " " + y)); //already found 
	            	} else { 
	            		CampusLocation outdoorPath = new CampusLocation(path, " ", x, y);
	            		parent =  new Node<CampusLocation>(path); 
	            		locations.put(outdoorPath, parent);
	            		coordinates.put(x + " " + y, outdoorPath);
	            		coordinates.put(path, outdoorPath);
	            		//increment path label 
	            		path = "path" + (Integer.parseInt(path.substring(4,path.length()))+1);	        
	            	}
	            } else { //add edges to parent node
	            	inputLine = inputLine.trim();
	            	tokens = inputLine.split("[, ]"); //split int x y and distance
	            	
	            	x = Double.parseDouble(tokens[0]);
	            	tokens[1] = tokens[1].substring(0, tokens[1].length() - 1); //remove colon
	            	y = Double.parseDouble(tokens[1]);
	            	double label = Double.parseDouble(tokens[2]);
	            	
	            	if (locations.containsKey(coordinates.get(x + " " + y))) { 
	            		child = locations.get(coordinates.get(x + " " + y)); //already found 
	            	} else {
	            		CampusLocation outdoorPath = new CampusLocation(path, " ", x, y);
	            		child =  new Node<CampusLocation>(path); 
	            		locations.put(outdoorPath, child);
	            		coordinates.put(x + " " + y, outdoorPath);
	            		coordinates.put(path, outdoorPath);
	            		//increment path label 
	            		path = "path" + (Integer.parseInt(path.substring(4,path.length()))+1);
	            	}
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
	public void parseBuildings(String filename) throws Exception {
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
	                throw new Exception("Line should contain exactly 3 tabs: "
	                                                 + inputLine);
	            }
	            String shortName = tokens[0];
	            String longName = tokens[1];
	            double x = Double.parseDouble(tokens[2]);
	            double y = Double.parseDouble(tokens[3]);

	            // Create a location from the parsed data and map it to a matching node
	            CampusLocation location = new CampusLocation(shortName, longName, x, y);
	            
	            buildings.add(shortName);
	            coordinates.put(shortName, location); //allow the location to be accessed by name 
	            coordinates.put(x + " " + y, location);
	            Node<CampusLocation> locNode = new Node<CampusLocation>(shortName);
	            if (!locations.containsKey(location)) {
	            	locations.put(location, locNode);
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
}






















