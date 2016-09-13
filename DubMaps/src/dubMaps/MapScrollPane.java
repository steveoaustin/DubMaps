package dubMaps;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MapScrollPane extends JScrollPane {

	public MapScrollPane() {
		MapPanel map = new MapPanel();
		setVisible(true);
		setDoubleBuffered(true);
		setViewportView(map);
	}
}
























