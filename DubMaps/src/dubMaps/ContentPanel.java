package dubMaps;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")

/*
 * ContentPanel is a holder for a map and necessary control sets. Content is
 * arranged vertically
 */
public class ContentPanel extends JPanel {

	/**
	 * Constructs a new ContentPanel and its child components
	 */
	public ContentPanel() {
		//ControlScrollPane controls = new ControlScrollPane();
		MapScrollPane map = new MapScrollPane();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//add(controls);
		add(map);
		setVisible(true);
	}
}
