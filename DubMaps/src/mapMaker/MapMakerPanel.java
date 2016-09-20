package mapMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import navigator.MapPanel;

@SuppressWarnings("serial")
public class MapMakerPanel extends MapPanel{

	public MapMakerPanel() {
		super();
	}

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
		
		// draw every path on the map
		g2d.setStroke(new BasicStroke(7, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
		g2d.setColor(Color.blue);
		for (int[] i: ui.getPath(model.getAllPaths()))
			g2d.drawLine(i[0], i[1], i[2], i[3]);
	}
	
	/**
	 * Update display to accommodate current window bounds
	 * @param width: Width of the window
	 * @param height: Height of the window
	 */
	public void updateDisplay(int width, int height) {
		// display the map at its native resolution 
		map.zoomIn(width, height, 
			new Dimension(map.getMap().getWidth(), map.getMap().getHeight()));
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		repaint();
		parent.scrollToCenter();
	}
	
}
