package dubMaps;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ContentFrame extends JFrame {

	public ContentFrame() {
		ContentPanel content = new ContentPanel();
		add(content);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
