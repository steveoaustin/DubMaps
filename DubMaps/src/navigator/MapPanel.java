package navigator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Display;
import controller.MapManager;
import controller.UIManager;
import model.MapGraph;
import model.Location;
import model.FileParser;
import model.Node;
import view.MapScrollPane;

@SuppressWarnings("serial")

/*
 * Map panel is a JPanel designed to draw an image of a map, optimized for 
 * the current display. MapPanel should be re-optimized for display each time
 * its parent container is resized, via the handleResize method   
 */
public class MapPanel extends JPanel {
	private final int OFFSET = 2; // outlined text border width
	private final int MAX_DISTANCE = 100; // max acceptable click distance from target
	private Node<Location> pathStart, pathDest;
	private int[] closestEntrance;
	private boolean path;
	protected MapScrollPane parent;
	protected MapManager map;
	protected UIManager ui;
	protected MapGraph model;
	
	/**
	 * Constructs a new MapPanel to display a map
	 */
	public MapPanel() {
		FileParser parser = new FileParser(Display.getMapFile());
		model = parser.getGraph();
		parent = null;
		map = new MapManager(getWidth(), getHeight());
		ui = new UIManager(map);
		path = false;
		pathStart = null;
		pathDest = null;
		closestEntrance = new int[2];
		repaint();
		setVisible(true);
	}	
	
	/**
	 * Handles left click events in the parent container
	 * @param x: Mouse's x coordinate
	 * @param y: Mouse's y coordinate
	 */
	public boolean leftClick(int x, int y) {
		return handlePath(x, y);
	}
	
	/**
	 * Handles right click events in the parent container
	 * @param x: Mouse's x coordinate
	 * @param y: Mouse's y coordinate
	 */
	public boolean rightClick(int x, int y) {
		return clearPath();
	}
	
	/**
	 * Handles mouse moved events from the parent container
	 * @param x: Mouse's x coordinate
	 * @param y: Mouse's y coordinate
	 */
	public void mouseMoved(int x, int y) {
		highlightClosestBuilding(x, y);
	}
	
	/**
	 * Update display to accommodate current window bounds
	 * @param width: Width of the window
	 * @param height: Height of the window
	 */
	public void updateDisplay(int width, int height) {
		if (path) {
			Rectangle bounds = ui.getPathBounds(model.getPath(pathStart, pathDest));
			map.zoomIn(parent.getWidth(), parent.getHeight(),
					new Dimension(bounds.width, bounds.height));
			setScrollCenter();
		} else {
			map.zoomOut(width, height);
		}
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		repaint();
		parent.scrollToCenter();
	}
	
	/**
	 * Sets the parent scroll pane's scroll center-point to the center of
	 * the drawn path
	 */
	public void setScrollCenter() {
		// exit if a path is not drawn
		if (pathStart == null || pathDest == null) { return; } 
		Rectangle bounds = ui.getPathBounds(model.getPath(pathStart, pathDest));
		
		// calculate the "percentage" that scrollBars should be set to, scaling 
		// with respect to native image pixels
		double x = ((bounds.x + (1.0 * bounds.width / 2)) / map.getMap().getWidth());
		double y = ((bounds.y + (1.0 * bounds.height / 2)) / map.getMap().getHeight());
		parent.setCenter(x, y);
	}
	
	/**
	 * Highlights the closest building to the x y coordinate
	 * @param x: X coordinate
	 * @param y: Y coordinate
	 */
	public void highlightClosestBuilding(int x, int y) {
		if (path) { return; } // don't highlight entrances if a path is already drawn
		x *= ui.scaleWidth();
		y *= ui.scaleHeight();
		Node<Location> n = model.getClosestBuilding(x, y, MAX_DISTANCE);
		// quit if a the mouse if too far from a building, reset closestEntrance
		if (n == null) { 
			closestEntrance[0] = -1;
			closestEntrance[1] = -1;
			repaint();
			return; 
		}  
		
		// scale the coordinates of n to fit on screen
		List<Location> temp = new ArrayList<Location>();
		temp.add(n.getLocation());
		int[] loc = ui.getBuildingEntrances(temp).get(0);
		closestEntrance[0] = loc[0];
		closestEntrance[1] = loc[1];
		repaint();
	}
	
	/**
	 * Handles the path drawing behavior of mapPanel, and returns a value indicating 
	 * whether the path was updated
	 * @param x: The click's x coordinate
	 * @param y: The clicks's y coordinate
	 * @return true if a path was drawn, false otherwise
	 */
	public boolean handlePath(int x, int y) {
		if (path) { return false; } // exit if path is already drawn
		// Convert click locations to image pixels
		x *= ui.scaleWidth();
		y *= ui.scaleHeight();
		Node<Location> n = model.getClosestBuilding(x, y, MAX_DISTANCE);
		if (n == null) { return false; }  // quit if a valid node is not found
		
		if (pathStart == null) {
			pathStart = n;
			repaint();
			return false;
		} else if (pathStart != null && pathStart != n) {
			pathDest = n;
			path = true;
			updateDisplay(parent.getWidth(), parent.getHeight());
			parent.scrollToCenter();
		}
		return true;
	}
	
	/**
	 * Resets path data in mapPanel; returns true if a drawn path is cleared
	 * @return
	 */
	public boolean clearPath() {
		// clear path 
		pathStart = null;
		pathDest = null;
		parent.resetCenter();		
		// only re-scroll to center if a path was drawn
		if (path) {
			path = false;
			updateDisplay(parent.getWidth(), parent.getHeight());
			return true;
		}
		repaint();
		return false;
	}
	
	/**
	 * Sets parent to the mapScrollPane holding this map. parent must be set to enable
	 * scrolling features
	 * @param parent: The parent mapScrollPane
	 */
	public void setParent(MapScrollPane parent) {
		this.parent = parent;
	}
	
	/**
	 * Handles painting for mapPanel, displaying the map and ui components relevant to the
	 * current state
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		// set graphics preferences
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g2d.setFont(ui.getFont());
		
		// update ui graphics
		ui.setGraphics(g2d);
		
		//draw map image
		int[] args = map.getImageArgs();
		g2d.drawImage(map.getMap(), args[0], args[1], args[2], args[3],
				args[4], args[5], args[6], args[7], null);
		
		// draw the path if it has been selected
		g2d.setStroke(new BasicStroke(7, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
		if (pathStart != null && pathDest != null) {
			g2d.setColor(Color.blue);
			for (int[] i: ui.getPath(model.getPath(pathStart, pathDest)))
				g2d.drawLine(i[0], i[1], i[2], i[3]);
		}
		
		// Print building labels
		for(String[] s: ui.getBuildingLabels(model.getLabels())) {
			int x = Integer.parseInt(s[1]);
			int y = Integer.parseInt(s[2]);
		    // outline text in black
			g2d.setColor(Color.black);
			g2d.drawString(s[0], x - OFFSET, y - OFFSET);
			g2d.drawString(s[0], x + OFFSET, y + OFFSET);
			g2d.drawString(s[0], x + OFFSET, y - OFFSET);
			g2d.drawString(s[0], x - OFFSET, y + OFFSET);
			// print the label 
			g2d.setColor(Color.white);
			g2d.drawString(s[0], x, y);
		}
		
		// Print small markers at each building entrance and highlight the closest
		// one to the mouse pointer
		g2d.setStroke(new BasicStroke(1, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
		int offset = 5, size = 2 * offset;		
		for(int[] i: ui.getBuildingEntrances(model.getBuildings())) {
			g2d.setColor(Color.green);
			// label the closest building with a different color			
			if (closestEntrance[0] == i[0] && closestEntrance[1] == i[1])
				g2d.setColor(Color.orange);
				
			g2d.fillOval(i[0] - offset, i[1] - offset, size, size);
			g2d.setColor(Color.black);
			g2d.drawOval(i[0] - offset - 1, i[1] - offset - 1, size + 2, size + 2);
		}
		
		// highlight the start building in green
		if (pathStart != null) {
			String[] s = ui.getBuildingLabel(model.getLabel(pathStart));
			if (s != null) { 
				int x = Integer.parseInt(s[1]);
				int y = Integer.parseInt(s[2]);
				g2d.setColor(Color.green);
				g2d.drawString(s[0], x, y);
			}
		}
		
		// highlight the destination building in red
		if (pathDest != null) {
			String[] s = ui.getBuildingLabel(model.getLabel(pathDest));
			if (s != null) {
				int x = Integer.parseInt(s[1]);
				int y = Integer.parseInt(s[2]);
				g2d.setColor(Color.red);
				g2d.drawString(s[0], x, y);
			}
		}	
	}
}