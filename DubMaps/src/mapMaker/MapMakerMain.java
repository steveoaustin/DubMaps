package mapMaker;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import controller.Display;
import view.ContentFrame;

public class MapMakerMain {
	private static final String MAP_DATA = "updatedCampus";
	private static final String MAP_IMAGE = "src/data/campus_map.jpg";
	
	public static void main(String[] args) {
		Display.setMapFile(MAP_DATA);
		Display.setMapImage(MAP_IMAGE);
		startupMessage();
		
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
				ContentFrame GUI = new ContentFrame(false);
            }
        });
    }
	
	// Display a startup message with directions for the user
	private static void startupMessage() {
		JOptionPane.showMessageDialog(null,
			  "Welcome to map maker!\n\n"
			+ "All path nodes are marked with green.\n"
			+ "Building entrances are marked with purple\n\n"
			+ "Use the option buttons at the top of the window\n"
			+ "to change modes and save the map.\n\n"
			+ "View Mode: simply observe the map.\n"
			+ "Changes cannot be made in this mode\n\n"
			+ "Path Mode: click on existing nodes\n"
			+ "to start drawing new paths. Right-clicking\n"
			+ "will cancel the current path\n\n"
			+ "Building Mode: Paths drawn in building mode\n"
			+ "will create a new building entrance, and prompt\n"
			+ "for a new building name\n\n"
			+ "Label Mode: In this mode, right-clicking on\n"
			+ "the map will prompt for a building name, and\n"
			+ "3 character abreviation\n\n"
			+ "Save: Save any edits to a new data file");
	}
}
