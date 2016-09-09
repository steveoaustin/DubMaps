package dubMaps;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel {

	public ContentPanel() {
		ControlPanel control = new ControlPanel();
		MapScrollPane mapScroll = new MapScrollPane();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(control);
		add(mapScroll);
		setVisible(true);
		setDoubleBuffered(true);
	}
}
