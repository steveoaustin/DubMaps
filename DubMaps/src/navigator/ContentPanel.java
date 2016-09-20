package navigator;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import mapMaker.MapMakerPanel;

@SuppressWarnings("serial")

/*
 * ContentPanel is a holder for a map and necessary control sets. Content is
 * arranged vertically
 */
public class ContentPanel extends JPanel {

	/**
	 * Constructs a new ContentPanel and its child components
	 */
	public ContentPanel(boolean navigate) {
		//ControlScrollPane controls = new ControlScrollPane();
		MapPanel map;
		if (navigate)
			map = new MapPanel();
		else
			map = new MapMakerPanel();
		
		MapScrollPane mapScroll = new MapScrollPane(map);
		map.setParent(mapScroll);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//add(controls);
		add(mapScroll);
		setVisible(true);
	}
}
