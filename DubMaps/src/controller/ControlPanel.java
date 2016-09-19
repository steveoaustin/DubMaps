package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")

/*
 * ControlPanel holds GUI control components 
 */
public class ControlPanel extends JPanel {
	private Dimension minSize, maxSize;
	
	public ControlPanel() {
		setVisible(true);
		setDoubleBuffered(true);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		setLayout(layout);
		this.setBackground(Color.pink);
		
		/* TODO: to fix resizing issue, make controlPanel a fixed height of one row of controls
		 * add it to a scrollpane with no visible bars, and enable wheel scrolling and click dragging
		 * to move the controlbar
		 * 
		 * not perfect but consistent behavior is vital in a good application
		 * 
		 * failure to snap-resize AND go fullscreen is unacceptable
		 * 
		*/
		
		JButton b = new JButton("I'm Rick Harrison, and this is my pawn shop.");
		b.setVisible(true);
		add(b);
		
		JButton b2 = new JButton("I work here with my old man and my son, Big Hoss.");
		b2.setVisible(true);
		add(b2);
		
		JButton b3 = new JButton("Everything in here has a story and a price. ");
		b3.setVisible(true);
		add(b3);
		
		JButton b4 = new JButton("One thing I've learned after 21 years");
		b4.setVisible(true);
		add(b4);
		
		JButton b5 = new JButton("you never know WHAT is gonna come through that door.");
		b5.setVisible(true);
		add(b5);
		setSize();
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				setSize();
			}
		});
	}
	
	/**
	 * Sets the panel's size preferences to fit all of its components
	 */
	public void setSize() {
		int maxHeight = 0;  // height coordinate of tallest component
		int minWidth = 0;  // width of the widest component
		for (Component c : getComponents()) {
			if (c.getWidth() > minWidth)
				minWidth = c.getWidth();
			if (c.getHeight() > maxHeight) 
				maxHeight = c.getHeight() + c.getY();
		}
		minSize = new Dimension(minWidth, maxHeight);
		maxSize = new Dimension(Display.getWidth(), maxHeight);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		setPreferredSize(maxSize);
	}
}
