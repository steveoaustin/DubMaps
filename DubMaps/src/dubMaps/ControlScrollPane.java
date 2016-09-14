package dubMaps;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")

/*
 * ControlScrollPane holds a set of control components that are displayed 
 * horizontally and enables horizontal scrolling to allow access when the
 * window is small
 */
public class ControlScrollPane extends JScrollPane {
	
	/**
	 * Constructs a new ControlScrollPane and its child components
	 */
	public ControlScrollPane() {
		ControlPanel controls = new ControlPanel();
		setViewportView(controls);
		setMaximumSize(controls.getMaximumSize());
		setVisible(true);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
	}

}
