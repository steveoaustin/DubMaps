package view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JScrollPane;

import controller.ControlPanel;
import mapMaker.MapMakerPanel;

@SuppressWarnings("serial")

/*
 * ControlScrollPane holds a set of control components that are displayed 
 * horizontally and enables horizontal scrolling to allow access when the
 * window is small
 */
public class ControlScrollPane extends JScrollPane {
	ControlPanel controls;
	/**
	 * Constructs a new ControlScrollPane and its child components
	 * @param map: The mapMakerPanel to be controlled by ControlPanel
	 */
	public ControlScrollPane(MapMakerPanel map) {
		controls = new ControlPanel(map);
		setViewportView(controls);
		setVisible(true);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
		
		// Recalculate size restrictions when resized or moved
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ControlScrollPane p = (ControlScrollPane) e.getComponent();
				p.controls.setSize();
				p.setMaximumSize(p.controls.getMaximumSize());
				p.setMinimumSize(p.controls.getMinimumSize());
			}
			
			public void componentMoved(ComponentEvent e) {
				ControlScrollPane p = (ControlScrollPane) e.getComponent();
				p.controls.setSize();
				p.setMaximumSize(p.controls.getMaximumSize());
				p.setMinimumSize(p.controls.getMinimumSize());
			}
		});
	}
}
