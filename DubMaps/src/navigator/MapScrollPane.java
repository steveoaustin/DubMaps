package navigator;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")

/*
 * MapScrollPane is designed to work a map application to optimize the display,
 * centering content automatically upon resizing, and enabling helpful UI features
 */
public class MapScrollPane extends JScrollPane {
	private MapPanel map;
	private double xCenterOffset, yCenterOffset;
	private boolean customCenter; // true indicates center-point set by client
	
	/**
	 * Constructs a new MapScrollPane that always has vertical and horizontal scroll bars,
	 * resize listeners, and mouse listeners
	 */
	public MapScrollPane(MapPanel map) {
		this.map = map;
		xCenterOffset = 0.0;
	    yCenterOffset = 0.0;
	    customCenter = false;
	    
	    // set scrollPane properties
		setVisible(true);
		setDoubleBuffered(true);
		setViewportView(map);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		getViewport().setViewPosition(getCenter());
		
		// listen for resize events to center scroll bars and notify child components
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				MapScrollPane p = (MapScrollPane) e.getComponent();
				p.map.repaint();
				p.getViewport().setViewPosition(p.getCenter());
				map.updateDisplay(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});
		
		// handle mouse input and pass off click information to map 
		MouseAdapter mouseAdapter = new MouseAdapter() {
			int startX, startY;
			
			@Override
			// Enables mouse-drag scrolling
			public void mouseDragged(MouseEvent e) {
				MapScrollPane p = (MapScrollPane) e.getComponent();		        
				JScrollBar horizontal = p.getHorizontalScrollBar();
				JScrollBar vertical = p.getVerticalScrollBar();
				
				// get change in xy values, then update them
				int dX = startX - e.getX();
				int dY = startY - e.getY();
				startX = e.getX();
				startY = e.getY();
				
				horizontal.setValue(horizontal.getValue() + dX);
				vertical.setValue(vertical.getValue() + dY);
			}
			
			// passes off click information to map for path drawing
			public void mousePressed(MouseEvent e) {
				MapScrollPane p = (MapScrollPane) e.getComponent();
				JScrollBar xOffset = p.getHorizontalScrollBar();
				JScrollBar yOffset = p.getVerticalScrollBar();
				boolean pathDrawn = false, pathCleared = false;
				
				if (SwingUtilities.isLeftMouseButton(e))
					pathDrawn = p.map.leftClick(e.getX() + xOffset.getValue(),
							e.getY() + yOffset.getValue());
				if (SwingUtilities.isRightMouseButton(e)) 
					pathCleared = p.map.rightClick(e.getX() + xOffset.getValue(),
							e.getY() + yOffset.getValue());;
				
				// save click location for click-drag scrolling
				startX = e.getX();
				startY = e.getY();
				
				// trigger resize events if the map image was updated
				if (pathDrawn || pathCleared) {
				    yOffset.setValue(yOffset.getValue() + 1);
					yOffset.setValue(yOffset.getValue() - 1);
				    p.setSize(p.getWidth() + 1, p.getHeight() + 1);
					p.setSize(p.getWidth() - 1, p.getHeight() - 1);
					p.map.updateDisplay(p.getWidth(), p.getHeight());
				}
			}
			
			// Highlight the closest building entrance to the mouse
			public void mouseMoved(MouseEvent e) {
				MapScrollPane p = (MapScrollPane) e.getComponent();		        
				JScrollBar horizontal = p.getHorizontalScrollBar();
				JScrollBar vertical = p.getVerticalScrollBar();
				p.map.mouseMoved(e.getX() + horizontal.getValue(), e.getY() + vertical.getValue());
			}
		};
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
	}
	
	/**
	 * adjusts the scroll bars to the current center-point
	 */
	public void scrollToCenter() {
		getViewport().setViewPosition(getCenter());
	}
	
	/**
	 * The center-point of the scrollpane's content
	 * @return a new point object holding the x and y values of the pane's center-point
	 */
	private Point getCenter() {
		Rectangle bounds = getViewport().getViewRect();
		Dimension size = getViewport().getViewSize();
		Point center = new Point(((size.width - bounds.width) / 2),
								 (size.height - bounds.height) / 2);
		if (customCenter) {
			center.x *= (2 * xCenterOffset);
			center.y *= (2 * yCenterOffset);
		}
		return center;
	}
	
	/**
	 * Set the scrollpane's content center-point to the the given percentages, with .5
	 * representing the true center
	 * @param x: Content's x percentage
	 * @param y: Content's y percentage
	 */
	public void setCenter(double xPercent, double yPercent) {
		xCenterOffset = xPercent;
		yCenterOffset = yPercent;
		customCenter = true;
	} 
	
	/**
	 * Resets the center-point of the scroll-pane's content to the true center
	 */
	public void resetCenter() {
		customCenter = false;
	}
}
























