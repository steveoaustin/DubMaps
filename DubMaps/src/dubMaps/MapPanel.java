package dubMaps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

@SuppressWarnings("serial")

/*
 * Map panel is a JPanel designed to draw an image of a map, optimized for 
 * the current display. MapPanel should be re-optimized for display each time
 * its parent container is resized, via the handleResize method   
 */
public class MapPanel extends JPanel {
	private final int OFFSET = 2, MAX_DISTANCE = 200;
	private Node<CampusLocation> pathStart, pathDest;
	private MapScrollPane parent;
	private boolean path;
	private MapManager map;
	private UIManager ui;
	private CampusGraph model;
	/**
	 * Constructs a new MapPanel to display a map
	 */
	public MapPanel(MapScrollPane parent) {
		CampusParser parser = new CampusParser("src/data/campus_buildings.dat",
											   "src/data/campus_paths.dat",
											   "src/data/campus_labels.dat");
		model = parser.getGraph();
		this.parent = parent;
		map = new MapManager(getWidth(), getHeight());
		ui = new UIManager(map);
		path = false;
		pathStart = null;
		pathDest = null;
		repaint();
		setVisible(true);
		
	}
	
	// update the map
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		// set graphics preferences
		g2d.setStroke(new BasicStroke(7));
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g2d.setFont(ui.getFont());
		
		// update ui graphics
		ui.setGraphics(g2d);
		
		//draw map image
		int[] args = map.getImageArgs();
		g2d.drawImage(map.getMap(), args[0], args[1], args[2], args[3],
				args[4], args[5], args[6], args[7], null);
		
		g2d.setColor(Color.blue);
		if (pathStart != null && pathDest != null) {
			for (int[] i: ui.getPath(model.getPath(pathStart, pathDest)))
				g2d.drawLine(i[0], i[1], i[2], i[3]);
		}
		
		
		// Print building labels
		for(String[] s: ui.getBuildings(model.getLabels())) {
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
		
		if (pathStart != null) {
			String[] s = ui.getBuilding(model.getLabel(pathStart));
			int x = Integer.parseInt(s[1]);
			int y = Integer.parseInt(s[2]);
			g2d.setColor(Color.green);
			g2d.drawString(s[0], x, y);
		}
		
		if (pathDest != null) {
			String[] s = ui.getBuilding(model.getLabel(pathDest));
			int x = Integer.parseInt(s[1]);
			int y = Integer.parseInt(s[2]);
			g2d.setColor(Color.red);
			g2d.drawString(s[0], x, y);
		}
		
		
	}
	
	/**
	 * Update display to accommodate current window bounds
	 * @param width: Width of the window
	 * @param height: Height of the window
	 */
	public void handleResize(int width, int height) {
		if (path) {
			Rectangle bounds = ui.getPathBounds(model.getPath(pathStart, pathDest));
			map.zoomIn(width, height, new Dimension(bounds.width, bounds.height));
			
			// scale center with map
			setScrollCenter();
		} else {
			map.zoomOut(width, height);
		}
		
		this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		repaint();
	}
	
	public void setScrollCenter() {
		Rectangle bounds = ui.getPathBounds(model.getPath(pathStart, pathDest));		
		// scale center with map
		System.out.println((bounds.x + (1.0 * bounds.width / 2)));
		
		
		parent.setCenter(((bounds.x + (1.0 * bounds.width / 2)) / map.getWidth()),
						 ((bounds.y + (1.0 * bounds.height / 2)) / map.getHeight()));
	}
	
	public void handlePath(int x, int y) {
		if (path) { return; } // exit if path is already drawn
		// Convert click locations to image pixels
		x *= ui.scaleWidth();
		y *= ui.scaleHeight();
		Node<CampusLocation> n = model.getClosestBuilding(x, y, MAX_DISTANCE);
		if (n == null) { return; }  // quit if a valid node is not found
		
		if (pathStart == null) {
			pathStart = n;
		} else if (pathStart != null && pathStart != n) {
			pathDest = n;
			path = true;
			handleResize(parent.getWidth(), parent.getHeight());
			parent.scrollToCenter();
		}
	}
	
	public void clearPath() {
		if (!path) { return; } // exit if path is already cleared
		pathStart = null;
		pathDest = null;
		path = false;
		parent.resetCenter();
		handleResize(parent.getWidth(), parent.getHeight());
		parent.scrollToCenter();
	}
}
