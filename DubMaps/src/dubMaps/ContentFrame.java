package dubMaps;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")

/*
 * ContentFrame is the parent component for map GUIs. It holds a contentPanel containing all
 * child components, and restricts resizing to reasonable boundaries
 */
public class ContentFrame extends JFrame {
	
	private static final double MIN_SCREEN_RATIO = 0.3;
	private Dimension minSize, maxSize;

	public ContentFrame() {
		// add GUI content
		ContentPanel content = new ContentPanel();
		add(content);
		
		// set frame properties
		setSizeRestrictions();
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		
		// add event listeners
		addComponentListener(new ComponentListener() {
			/**
			 * Recalculates the frame's max and min size in case it was moved to a new window
			 * in a multiple monitor display
			 * @param e: Component event associated with resizing
			 */
			@Override
			public void componentMoved(ComponentEvent e) {
				setSizeRestrictions();
			}
			
			@Override
			public void componentResized(ComponentEvent e) { /* ignore */ }	
			
			@Override
			public void componentShown(ComponentEvent e) {	/* ignore */ }
			
			@Override
			public void componentHidden(ComponentEvent e) {	/* ignore */ }			
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
	}
	
}





















