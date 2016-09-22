package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import mapMaker.MapMakerPanel;
import mapMaker.MapMakerPanel.Mode;
import view.ControlScrollPane;

@SuppressWarnings("serial")

/*
 * ControlPanel holds GUI control components 
 */
public class ControlPanel extends JPanel {
	private Dimension minSize, maxSize;
	private ControlScrollPane parent;
	private MapMakerPanel map;
	private int WIDTH_PADDING = 30, HEIGHT_PADDING = 20;
	
	public ControlPanel(ControlScrollPane parent, MapMakerPanel map) {
		this.parent = parent;
		this.map = map;
		setVisible(true);
		setDoubleBuffered(true);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		setLayout(layout);
		this.setBackground(Color.BLUE);
		
		/* TODO: to fix resizing issue, make controlPanel a fixed height of one row of controls
		 * add it to a scrollpane with no visible bars, and enable wheel scrolling and click dragging
		 * to move the controlbar
		 * 
		 * not perfect but consistent behavior is vital in a good application
		 * 
		 * failure to snap-resize AND go fullscreen is unacceptable
		 * 
		*/
		

		
		JButton b = new JButton("View Mode");
		b.addActionListener(e -> map.setMode(Mode.OBSERVE));
		b.setVisible(true);
		add(b);
		
		JButton b2 = new JButton("Add Paths");
		b2.addActionListener(e -> map.setMode(Mode.ADD_PATHS));
		b2.setVisible(true);
		add(b2);
		
		JButton b3 = new JButton("Add Buildings");
		b3.addActionListener(e -> map.setMode(Mode.ADD_BUILDINGS));
		b3.setVisible(true);
		add(b3);
		
		JButton b4 = new JButton("Label Buildings");
		b4.addActionListener(e -> map.setMode(Mode.ADD_LABELS));
		b4.setVisible(true);
		add(b4);
		
		JButton b5 = new JButton("Save Map");
		b5.addActionListener(e -> map.saveMap());
		b5.setVisible(true);
		add(b5);
		setSize();
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				setSize();
			}
		});
		repaint();
	}
	
	/**
	 * Sets the panel's size preferences to fit all of its components
	 */
	public void setSize() {
		int maxHeight = 0;  // height coordinate of tallest component
		int minWidth = 0;  // width of all the components
		for (Component c : getComponents()) {
			minWidth += c.getWidth();
			if (c.getHeight() > maxHeight) 
				maxHeight = c.getHeight() + HEIGHT_PADDING;
		}
		
		minSize = new Dimension(minWidth + WIDTH_PADDING, maxHeight);
		maxSize = new Dimension(Display.getWidth(), maxHeight);
		
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		setPreferredSize(minSize);
	}
}
