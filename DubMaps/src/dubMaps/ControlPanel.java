package dubMaps;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private Dimension minSize, maxSize;
	
	public ControlPanel() {
		setVisible(true);
		setDoubleBuffered(true);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		setLayout(layout);
		
		//add temp button for resize testing
		/*
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
		*/
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				setSize();
			}
		});
	}
	
	public void setSize() {
		int maxHeight = 0;  // height coordinate of lowest component
		int minWidth = 0;  // width of the widest component
		for (Component c : getComponents()) {
			if (c.getWidth() > minWidth)
				minWidth = c.getWidth();
			if (c.getY() > maxHeight) 
				maxHeight = c.getY() + c.getHeight();
		}
		minSize = new Dimension(minWidth, maxHeight);
		maxSize = new Dimension(Display.getWidth(), maxHeight);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		setPreferredSize(maxSize);
	}
}
