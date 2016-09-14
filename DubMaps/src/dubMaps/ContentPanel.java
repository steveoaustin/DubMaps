package dubMaps;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel {

	public ContentPanel() {
		//ControlScrollPane controls = new ControlScrollPane();
		MapScrollPane map = new MapScrollPane();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//add(controls);
		add(map);
		setVisible(true);
	}
}
