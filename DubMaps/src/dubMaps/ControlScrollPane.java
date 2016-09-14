package dubMaps;

import java.awt.event.ComponentAdapter;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ControlScrollPane extends JScrollPane {
	
	public ControlScrollPane() {
		ControlPanel controls = new ControlPanel();
		setViewportView(controls);
		setMaximumSize(controls.getMaximumSize());
		setVisible(true);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
	}

}
