package controller;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import model.CampusLocation;
import model.Edge;

/*
 * UI manager works with map manager to scale and return graphics arguments to
 * draw a user interface on-screen
 */
public class UIManager {
	private List<int[]> path;
	private final MapManager map;
	private Graphics2D context;
	
	public UIManager(MapManager map) {
		path = new ArrayList<int[]>();
		this.map = map;
		context = null;
	}
	
	/**
	 * Constructs a list of arrays to be used as method arguments for
	 * the graphics drawLine method to draw a path
	 * @param unscaledPath: The list of path segments
	 * @return path: The path segments scaled to be drawn on-screen
	 */
	public List<int[]> getPath(List<Edge<CampusLocation>> unscaledPath) {
		path = new ArrayList<int[]>();		
		// scale each path segment to fit the current display size
		for(Edge<CampusLocation> e: unscaledPath)
			path.add(new int[] { 
			    (int) (e.getParent().getLocation().getX() / scaleWidth()),
			    (int) (e.getParent().getLocation().getY() / scaleHeight()),
			    (int) (e.getChild().getLocation().getX() / scaleWidth()),
			    (int) (e.getChild().getLocation().getY() / scaleHeight())});
			
		return path;
	}
	
	/**
	 * Returns a rectangle representing the on-screen boundaries of the current path
	 * @param unscaledPath: A list of path segments
	 * @return a rectangle enclosing the entire path, in native image pixels
	 */
	public Rectangle getPathBounds(List<Edge<CampusLocation>> unscaledPath) {
		List<int[]> segs = getPath(unscaledPath);
		int maxX = 0, minX = map.getMap().getWidth();
		int maxY = 0, minY = map.getMap().getHeight();
		for (int[] i: segs) {
			if (i[0] > maxX || i[2] > maxX)
				maxX = Math.max(i[0], i[2]);
			if (i[0] < minX || i[2] < minX)
				minX = Math.min(i[0], i[2]);
			if (i[1] > maxY || i[3] > maxY)
				maxY = Math.max(i[1], i[3]);
			if (i[1] < minY || i[3] < minY)
				minY = Math.min(i[1], i[3]);
		}
		// scale back up to pixel dimensions
		minY *= scaleHeight();
		maxY *= scaleHeight();
		minX *= scaleWidth();
		maxX *= scaleWidth();
		
		return new Rectangle(minX, minY, (maxX - minX), (maxY - minY));
	}
	
	/**
	 * Returns a list of scaled x y coordinates that represent building entrances
	 * @param entrances: The list of entrances
	 * @return A list of 2 element integer arrays; each is the x y position of an entrance
	 */
	public List<int[]> getBuildingEntrances(List<CampusLocation> entrances) {
		List<int[]> result = new ArrayList<int[]>();
		// add x y coordinates for each entrance
		for(CampusLocation c: entrances)
			result.add(new int[]{ (int) (c.getX() / scaleWidth()),
					(int) (c.getY() / scaleHeight()) });
		
		return result;
	}
	
	/**
	 * Returns a list of graphics draw String method arguments containing locations
	 * abbreviations and their display x y coordinates
	 * @param buildings: A list of CampusLocations that represent destinations
	 * @return The building's abbreviated names and drawString arguments
	 */
	public List<String[]> getBuildingLabels(List<CampusLocation> buildings) {
		List<String[]> result = new ArrayList<String[]>();
		for(CampusLocation c: buildings)		
			result.add(centerText(c.getName(), 
		        (int) (c.getX() / scaleWidth()),
				(int) (c.getY() / scaleHeight())));
		
		return result;
	}
	
	/**
	 * Returns graphics draw String method arguments containing location
	 * abbreviation and display x y coordinates
	 * @param location: A CampusLocation that represents the building
	 * @return The building's drawString arguments
	 */
	public String[] getBuildingLabel(CampusLocation location) {
		if (location == null) { return null; }
		return centerText(location.getName(), 
				   (int) (location.getX() / scaleWidth()),
				   (int) (location.getY() / scaleHeight()));
	}
	
	/**
	 * Constructs a new font with size optimized for the current display
	 * @return the new font
	 */
	public Font getFont() {
		int maxSize = 24, minSize = 16, size;
		
		// Scale the font size to match window dimensions
		if (scaleWidth() >= 5.0)
			size = minSize;
		else if (scaleWidth() <= 2.0)
			size = maxSize;
		else
			size = (int) (minSize + ((5 - scaleWidth()) * 3));
		
		return new Font("Ariel", Font.BOLD, size);
	}
	
	public void setGraphics(Graphics2D g) {
		context = g;
	}
	
	// calculates drawString arguments to display text centered exactly at x, y
	private String[] centerText(String text, int x, int y) {
		String[] result = new String[3];
		result[0] = text;
		
		// create a fontMetrics object to get text display dimensions
		FontMetrics metrics = context.getFontMetrics(getFont());
		Rectangle2D bounds = metrics.getStringBounds(text, context);
		result[1] = Integer.toString((int) (x - (bounds.getWidth() / 2)));
		result[2] = Integer.toString((int) (y + (bounds.getHeight() / 2)));
		
		return result;
	}
	
	// returns the ratio of display width to image width
	public double scaleWidth() {
		return (1.0 * map.getMap().getWidth()) / (1.0 * map.getWidth());
	}
	
	// returns the ratio of display height to image height
	public double scaleHeight() {
		return (1.0 * map.getMap().getHeight()) / (1.0 * map.getHeight());
	}
}
