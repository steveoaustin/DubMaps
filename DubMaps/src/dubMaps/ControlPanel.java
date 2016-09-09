package dubMaps;

import java.awt.FlowLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	
	public ControlPanel() {
		setVisible(true);
		setDoubleBuffered(true);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		setLayout(layout);
	}
}
