package mapMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.FileWriter;
import model.Location;
import model.Node;
import navigator.MapPanel;

@SuppressWarnings("serial")

/*
 * MapMakerPanel displays a map and all paths, nodes, destinations, and labels contained
 * in that map. Allows the user to add to the map, and save changes
 */
public class MapMakerPanel extends MapPanel{
	private final int OFFSET = 2; // outlined text border width
	private final int MAX_DISTANCE = 8; // max acceptable click distance from target
	private int[] closestNode, selectedNode, mouseXY;
	private boolean nodeSelected;
	private Mode mode;
	public enum Mode { OBSERVE, ADD_PATHS, ADD_BUILDINGS, ADD_LABELS }
	
	/**
	 * Constructs a new MapMakerPanel set to observe mode
	 */
	public MapMakerPanel() {
		super();
		closestNode = new int[2];
		selectedNode = new int[2];
		mouseXY = new int[2];
		nodeSelected = false;
		mode = Mode.OBSERVE;
	}
	
	/**
	 * Handles left click events from the parent container
	 * @param x: The click's x location
	 * @param y: The Click's y location
	 */
	public boolean leftClick(int x, int y) {
		// ignore clicks if user is observing. Adding labels is done via right click
		if (mode == Mode.OBSERVE || mode == Mode.ADD_LABELS) { return false; }
		
		x *= ui.scaleWidth();
		y *= ui.scaleHeight();
		handleNewMap(x, y);
		Node<Location> n = model.getClosestNode(x, y, MAX_DISTANCE);
		
		if (nodeSelected) {
			if (mode == Mode.ADD_BUILDINGS) {
				// allow buildings to be added "on top" of nodes
				addBuilding(x, y);
				nodeSelected = false;
			}
			else if (mode == Mode.ADD_PATHS) {
				// add 2 way edge between n and selectedNode 
				if (n != null) {
					addEdge(n);
					nodeSelected = false;
				}	
				// add n as a new node
				else {
					addNode(x, y);
					addEdge(model.getNode(x, y));
					selectNode(model.getNode(x, y)); // keep drawing path from new node
				}
			}
		} else if (nodeSelected == false && n != null) {
			selectNode(n);
		}
		repaint();
		return false; // avoid re-centering display
	}
	
	/**
	 * Handles right click events from the parent container
	 * @param x: The click's x location
	 * @param y: The click's y location
	 */
	public boolean rightClick(int x, int y) {
		x *= ui.scaleWidth();
		y *= ui.scaleHeight();
		
		// right clicking while adding a new path will cancel it
		if (mode == Mode.ADD_PATHS || mode == Mode.ADD_BUILDINGS) {
			nodeSelected = false;
			repaint();
		}
		if (mode == Mode.ADD_LABELS) {
			addLabel(x, y);
		}
		return false;
	}
	
	/**
	 * Handles mouse movement events from the parent container
	 * @param x: the mouse's x location
	 * @param y: the mouse's y location
	 */
	public void mouseMoved(int x, int y) {
		highlightClosestNode(x, y);
		mouseXY = new int[]{ x, y };
	}
	
	/**
	 * Update display to accommodate current window bounds
	 * @param width: Width of the window
	 * @param height: Height of the window
	 */
	public void updateDisplay(int width, int height, boolean center) {
		// display the map at its native resolution 
		if (mode != Mode.OBSERVE) {
			map.zoomIn(width, height, 
					new Dimension(map.getMap().getWidth(), map.getMap().getHeight()));
		} else {
			map.zoomOut(width, height);
		}
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		repaint();
		if (center)
			parent.scrollToCenter();
	}
	
	/**
	 * Sets the mode to m
	 * @param m: The new mode
	 */
	public void setMode(Mode m) {
		mode = m;
		if (mode == Mode.OBSERVE)
			updateDisplay(parent.getWidth(), parent.getHeight(), true);
		else
			updateDisplay(parent.getWidth(), parent.getHeight(), false);
		repaint();
	}
	
	/**
	 * Prompts the user for a filename and writes out the current map to data files
	 */
	public void saveMap() {
		new FileWriter(model);
	}
		
	/**
	 * Handles all graphical output for mapMaker
	 */
	protected void paintComponent(Graphics g) {
		// set appropriate component sizes for the current mode
		int pathWidth, markerWidth;
		if (mode == Mode.OBSERVE) {
			pathWidth = 3;
			markerWidth = 2;
		} else {
			pathWidth = 7;
			markerWidth = 5;
		}
		
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
		
		// draw every path on the map
		g2d.setStroke(new BasicStroke(pathWidth, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
		g2d.setColor(Color.blue);
		for (int[] i: ui.getPath(model.getAllPaths()))
			g2d.drawLine(i[0], i[1], i[2], i[3]);
		
		// draw a path from the selected node to the mouse pointer
		if (nodeSelected) {
			g2d.drawLine(selectedNode[0], selectedNode[1], mouseXY[0], mouseXY[1]);
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
		
		// draw a marker on each node and highlight the node which
		// the mouse currently points to
		g2d.setStroke(new BasicStroke(1, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
		int offset = markerWidth, size = 2 * offset;		
		for(int[] i: ui.getBuildingEntrances(model.getAllLocations())) {
			g2d.setColor(Color.green);
			// label the closest building with a different color			
			if (closestNode[0] == i[0] && closestNode[1] == i[1])
				g2d.setColor(Color.orange);
				
			g2d.fillOval(i[0] - offset, i[1] - offset, size, size);
			g2d.setColor(Color.black);
			g2d.drawOval(i[0] - offset - 1, i[1] - offset - 1, size + 2, size + 2);
		}
		
		// Highlight building nodes with a different color
		for(int[] i: ui.getBuildingEntrances(model.getBuildings())) {
			g2d.setColor(Color.magenta);
			// label the closest building with a different color			
			if (closestNode[0] == i[0] && closestNode[1] == i[1])
				g2d.setColor(Color.orange);
				
			g2d.fillOval(i[0] - offset, i[1] - offset, size, size);
			g2d.setColor(Color.black);
			g2d.drawOval(i[0] - offset - 1, i[1] - offset - 1, size + 2, size + 2);
		}
	}

	// allows users to add the first node or building to an empty map
	private void handleNewMap(int x, int y) {
		// enable adding new nodes to an empty map
		if (model.getAllNodes().size() == 0) {
			// ask if the user would like to start drawing the new map here
			int result = JOptionPane.showConfirmDialog(null,
				     new JLabel("Would you like to start the map here? all "
				     		+ "paths will branch out from this point"), "Start Here?",
					 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			// place the first node if the user agrees
			if (result == JOptionPane.YES_OPTION) {
				if (mode == Mode.ADD_BUILDINGS)
					addBuilding(x, y);
				else if (mode == Mode.ADD_PATHS)
					addNode(x, y);
			}
		}
	}
		
	// sets nodeSelected to true and saves the node's x y coordinates
	private void selectNode(Node<Location> n) {
		nodeSelected = true;
		selectedNode = new int[]{ (int) (n.getLocation().getX() / ui.scaleWidth()),
							 (int) (n.getLocation().getY() / ui.scaleHeight()) };
	}
	
	// prompts the user for a name, then adds the building at location x, y
	private void addBuilding(int x, int y) {
		String[] name = promptInput("New entrance", 
				"Enter a name for this Building",
				"Enter a 3-letter abreviated name");
		if (name != null) {
			model.add(new Node<Location>(new Location(name[0], name[1], x, y)));
			addEdge(model.getNode(x, y));
		}
	}
		
	// prompts the user for a building name, then adds it at location x, y
	private void addLabel(int x, int y) {
		String[] name = promptInput("New Label", "Enter a name for this location",
									"Enter a 3-letter abreviated name");
		
		// show error message if the label does not exist as a building
		if (name != null && model.getNode(name[0]) == null) {
			JOptionPane.showMessageDialog(null, "Label must match an existing building");
			return;
		}
		
		if (name != null)
			model.addLabel(new Location(name[0], name[1], x, y));
	}
	 
	// prompts the user for full name and abbreviation as text input
	private String[] promptInput(String frameLabel, String inputLabel, String abrevLabel) {
		String[] result = new String[2];
		
		JTextField nameInput = new JTextField();
		JTextField abrevInput = new JTextField();
		
		int nameResult = JOptionPane.showConfirmDialog(null,
				     new JComponent[]{ nameInput, new JLabel(inputLabel) }, frameLabel,
					 JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (nameResult == JOptionPane.OK_OPTION) {
			result[1] = nameInput.getText();
		} else {
			return null;  // exit immediately if the user does not choose ok_option
		}
		
		int abrevResult = JOptionPane.showConfirmDialog(null,
			     new JComponent[]{ abrevInput, new JLabel(abrevLabel) }, frameLabel,
				 JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	
		if (abrevResult == JOptionPane.OK_OPTION) {
			result[0] = abrevInput.getText().toUpperCase().trim();
		    if (result[0].length() != 3) {
		    	JOptionPane.showMessageDialog(null, "Abbreviated name must be exactly 3 characters");
		    	return null;
		    } 
		} else {
			return null;  // exit immediately if the user does not choose ok_option
		}	
		return result;
	}
	
	// adds the location x, y as a node 
	private void addNode(int x, int y) {
		model.add(new Node<Location>(x, y));
	}	
	
	// add edges between node and selectedNode
	private void addEdge(Node<Location> node) {
		Node<Location> selected = model.getNode(ui.scaleWidth() * selectedNode[0],
			ui.scaleHeight() * selectedNode[1]);
		if (selected == null) {
			throw new Error("closestNode should be valid");
		}
		double distance = Math.sqrt(
			Math.pow(node.getLocation().getX() - selected.getLocation().getX(), 2) +
			Math.pow(node.getLocation().getY() - selected.getLocation().getY(), 2));
		
		node.addEdge(selected, distance);
		selected.addEdge(node, distance);
	}
	
	// Highlights the closest node to the mouse pointer
	private void highlightClosestNode(int x, int y) {
		x *= ui.scaleWidth();
		y *= ui.scaleHeight();
		Node<Location> n = model.getClosestNode(x, y, MAX_DISTANCE);
		
		// reset closestNode if a the mouse if too far from a node
		if (n == null) { 
			closestNode[0] = -1;
			closestNode[1] = -1;
		}  else {
			// scale the coordinates of n to fit on screen
			List<Location> temp = new ArrayList<Location>();
			temp.add(n.getLocation());
			int[] loc = ui.getBuildingEntrances(temp).get(0);
			closestNode[0] = loc[0];
			closestNode[1] = loc[1];
		}
		repaint();
	}
}