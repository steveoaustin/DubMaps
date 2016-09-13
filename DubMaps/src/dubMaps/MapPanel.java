package dubMaps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {
	
	public MapPanel() {
		setVisible(true);
		this.setBackground(Color.blue);
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
	}
}
