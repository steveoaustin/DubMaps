package dubMaps;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")

/*
 * ContentFrame is the parent component for map GUIs. It holds a contentPanel containing all
 * child components, and restricts resizing to reasonable boundaries
 */
public class ContentFrame extends JFrame {
	
	private static final double MIN_SCREEN_RATIO = 0.2;
	private Dimension minSize, maxSize;

	/**
	 * Constructs the outer window frame, and child components, for map applications
	 */
	public ContentFrame() {
		// add GUI content
		ContentPanel content = new ContentPanel();
		add(content);
		
		// set frame properties
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		// size frame after packing to trigger resize events
		setSizeRestrictions();
		
		// recalculate size restrictions when frame is moved
		addComponentListener(new ComponentAdapter() {			
			public void componentMoved(ComponentEvent e) {
				setSizeRestrictions();
			}		
		});
	}
	
	/*
	 * Gets current display window dimensions and uses them to restrict frame size
	 */
	private void setSizeRestrictions() {
		int min = (int) (Display.getWidth() * MIN_SCREEN_RATIO);
		minSize = new Dimension(min, min);
		maxSize = new Dimension(Display.getWidth(), Display.getHeight());
		setMaximumSize(maxSize);
		setMinimumSize(minSize);
		setPreferredSize(minSize);
		validate();
	}
	
}





















