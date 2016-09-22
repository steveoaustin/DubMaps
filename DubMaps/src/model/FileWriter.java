package model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FileWriter {
	private final MapGraph data;
	// suffixes for data files
	private final String PATH = "src/data/",
						 LABELS = "_labels.dat",
						 PATHS = "_paths.dat",
						 BUILDINGS = "_buildings.dat";
	
	public FileWriter(MapGraph data) {
		this.data = data;
		String filename = getFilename();
		if (filename != null) {
			try {
				writePaths(PATH + filename + PATHS);
				writeBuildings(PATH + filename + BUILDINGS);
				writeLabels(PATH + filename + LABELS);
			} catch (IOException e) {
				System.out.println("file error: files could not be written to");
				e.printStackTrace();
			}
			
		}
	}

	// write out all paths in the map to pathsFile
	private void writePaths(String pathsFile) throws IOException {
		Path file = Paths.get(pathsFile);
		List<String> content = new ArrayList<String>();
		
		
		Files.write(file, content, Charset.forName("UTF-8"));
		
	}
	
	// writes out all buildings in the map to buildingsFile
	private void writeBuildings(String buildingsFile) throws IOException {
		Path file = Paths.get(buildingsFile);
		List<String> content = new ArrayList<String>();
		
		
		Files.write(file, content, Charset.forName("UTF-8"));
	}

	// writes out all labels in the graph to labelsFile
	 private void writeLabels(String labelsFile) throws IOException {
		 Path file = Paths.get(labelsFile);
		List<String> content = new ArrayList<String>();
		
		for (Location l: data.getLabels()) {
			String line = l.getName() + "\t" + l.getLongName() 
						  + "\t" + l.getX() + "\t" + l.getY();
			content.add(line);
		}
		Files.write(file, content, Charset.forName("UTF-8"));
	}

	// prompts the user for a filename. returns the filename if supplied, or null
	 // if the user exited
	private String getFilename() {
		String result;
		JTextField input = new JTextField();
		
		int inputResult = JOptionPane.showConfirmDialog(null,
						new JComponent[] { input, new JLabel("Enter a name for this map") },
						"Name your map!", 
						JOptionPane.OK_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
		if (inputResult == JOptionPane.OK_OPTION) {
			result = input.getText().trim().replace("/", "");
			// check if the file already exists
			File f = new File(PATH + result + PATHS);
			if (f.exists()) {
				result = promptOverwriteFile(result);
			}
			return result;
		} else {
			return null;
		}
	}
	
	// ask the user if they want to overwrite existing files, prompting for 
	// a new filename if not
	private String promptOverwriteFile(String existingName) {
		int inputResult = JOptionPane.showConfirmDialog(null,
						new JLabel("This file already exists, would you like to overwrite it?"),
						"Overwrite files?", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
		
		if (inputResult == JOptionPane.YES_OPTION) {
			return existingName;
		} else if (inputResult == JOptionPane.NO_OPTION) {
			// ask the user for another filename if they choose not to overwrite
			return getFilename(); 
		} else {
			return null;
		}
	}
}
