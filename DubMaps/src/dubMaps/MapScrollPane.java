package dubMaps;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MapScrollPane extends JScrollPane {

	public MapScrollPane() {
		MapPanel map = new MapPanel();
		setVisible(true);
		setDoubleBuffered(true);
		setViewportView(map);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				if (e.getComponent() instanceof JScrollPane) {
					JScrollPane p = (JScrollPane) e.getComponent();
					Rectangle bounds = p.getViewport().getViewRect();
					Dimension size = p.getViewport().getViewSize();
					
					p.getViewport().setViewPosition(new Point(
						((size.width - bounds.width) / 2), (size.height - bounds.height) / 2));
				}
				map.handleResize(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});
	}

}
























