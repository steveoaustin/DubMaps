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
 * ControlPanel holds GUI control components that work with MapMakerPanel
 */
public class ControlPanel extends JPanel {
	private Dimension minSize, maxSize;
	private MapMakerPanel map;
	private int WIDTH_PADDING = 30, HEIGHT_PADDING = 20;
	
	/**
	 * Construct a new ControlPanel with buttons that control the state of MapMakerPanel
	 * @param map: The map controlled by ControlPanel's controls
	 */
	public ControlPanel(MapMakerPanel map) {
		this.map = map;
		
		// Set default properties
		setVisible(true);
		setDoubleBuffered(true);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		setLayout(layout);
		this.setBackground(Color.BLUE);
		
		// Add Buttons to change the mode of map, and save it 
		JButton view = new JButton("View Mode");
		view.addActionListener(e -> map.setMode(Mode.OBSERVE));
		view.setVisible(true);
		add(view);
		
		JButton path = new JButton("Add Paths");
		path.addActionListener(e -> map.setMode(Mode.ADD_PATHS));
		path.setVisible(true);
		add(path);
		
		JButton building = new JButton("Add Buildings");
		building.addActionListener(e -> map.setMode(Mode.ADD_BUILDINGS));
		building.setVisible(true);
		add(building);
		
		JButton label = new JButton("Label Buildings");
		label.addActionListener(e -> map.setMode(Mode.ADD_LABELS));
		label.setVisible(true);
		add(label);
		
		JButton save = new JButton("Save Map");
		save.addActionListener(e -> map.saveMap());
		save.setVisible(true);
		add(save);
		
		setSize();
		
		// Update size whenever the parent component is resized
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
