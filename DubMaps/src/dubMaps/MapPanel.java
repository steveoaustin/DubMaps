package dubMaps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {
	private boolean defaultOutput;
	private MapManager map;
	
	public MapPanel() {
		defaultOutput = true;
		map = new MapManager();
		setVisible(true);
		this.setBackground(Color.blue);
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		//draw map image
		int[] args = map.getImageArgs();
		g2d.drawImage(map.getMap(), args[0], args[1], args[2], args[3],
				args[4], args[5], args[6], args[7], null);
		
	}
	
	/**
	 * Update display to accommodate current window bounds
	 * @param width: Width of the window
	 * @param height: Height of the window
	 */
	public void handleResize(int width, int height) {
		if (defaultOutput)
			map.zoomOut(width, height);
		this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
	}
}
