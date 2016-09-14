package dubMaps;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

@SuppressWarnings("serial")

/*
 * MapScrollPane is designed to work a map application to optimize the display,
 * centering content automatically upon resizing, and enabling helpful UI features
 */
public class MapScrollPane extends JScrollPane {
	private MapPanel map;
	
	/**
	 * Constructs a new MapScrollPane that always has vertical and horizontal scroll bars,
	 * resize listeners, and mouse listeners
	 */
	public MapScrollPane() {
		map = new MapPanel();
		setVisible(true);
		setDoubleBuffered(true);
		setViewportView(map);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		
		
		// listen for resize events to center scroll bars and notify child components
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				MapScrollPane p = (MapScrollPane) e.getComponent();					
				p.getViewport().setViewPosition(p.getCenter());
				map.handleResize(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});
		
		//enable click-drag scrolling
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
			
			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
			}
		};
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
	}
	
	/**
	 * The center-point of the scrollpane's content
	 * @return a new point object holding the x and y values of the pane's center-point
	 */
	public Point getCenter() {
		Rectangle bounds = getViewport().getViewRect();
		Dimension size = getViewport().getViewSize();
		return new Point(((size.width - bounds.width) / 2), (size.height - bounds.height) / 2);
	}
}
























