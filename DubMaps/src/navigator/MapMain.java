package navigator;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import controller.Display;
import view.ContentFrame;

public class MapMain {
	// the prefix for map paths, buildings, and labels files
	private static final String MAP_DATA = "updatedCampus";
	// the file path of the map image
	private static final String MAP_IMAGE = "src/data/campus_map.jpg";
	 
	public static void main(String[] args) {
		// set map data and image files
		Display.setMapFile(MAP_DATA);
		Display.setMapImage(MAP_IMAGE);
		// show directions on startup
		startupMessage();
		
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                @SuppressWarnings("unused")
				ContentFrame GUI = new ContentFrame(true);
            }
        });
    }
	
	// Display a startup message with directions for the user
	private static void startupMessage() {
		JOptionPane.showMessageDialog(null, "Welcome to Navigator!\n\n"
				+ "All building entrances are marked with green,\nand are highlighted in "
				+ "yellow when close enough\nto be selected.\n\nLeft click on 2 different"
				+ " entrances\nto draw the shortest path between them.\n\n"
				+ "Right clicking at any time will reset the map");
	}
}