package debugMaps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelTest extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6546348429540975679L;
	private BufferedImage map;
	
	public PanelTest() {
		
		try {
			map = ImageIO.read(new File("src/data/campus_map.jpg"));
		} catch(IOException e) { /*  ignore  */ }
		
		//this.setSize(new Dimension(200, 200));
		this.setPreferredSize(new Dimension(4330, 2964));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setVisible(true);
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//this.setSize(new Dimension(this.getParent().getWidth(), this.getParent().getHeight()));
		super.paintComponent(g2d);
		g2d.drawImage(map, 0, 0, 4330, 2964, null);
	}
}
