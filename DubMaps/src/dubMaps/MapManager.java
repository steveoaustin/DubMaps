package dubMaps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapManager {
	
	private final String mapFilepath = "src/data/campus_map.jpg";
	private BufferedImage map ;
	private final int[] imageArgs = new int[8]; // array of arguments for client's drawImage method call
	
	public MapManager() {
		try {
			map = ImageIO.read(new File(mapFilepath));
		} catch (IOException e) {
			System.out.println("Invalid filepath");
		}
		
		// draw the entire image and place it in the top left of its destination
		imageArgs[0] = 0; // start x coordinate of the destination
		imageArgs[1] = 0; // start y coordinate of the destination	
		imageArgs[4] = 0; // start x coordinate of the image
		imageArgs[5] = 0; // start y coordinate of the image
		imageArgs[6] = map.getWidth(); // end x coordinate of the image
		imageArgs[7] = map.getHeight(); // end y coordinate of the image
		
		// manipulate ONLY the destination's dimensions 
		imageArgs[2] = Display.getWidth(); // end x coordinate of the destination
		imageArgs[3] = scaleHeight(Display.getWidth()); // end y coordinate of the destination	
	}
	
	public BufferedImage getMap() {
		return map;
	}
	
	public int[] getImageArgs() {
		return imageArgs;
	}
	
	/**
	 * Recalculates imageArgs to be optimized for the current display,
	 * "zooming out" as much as possible
	 * @param width: The display's width
	 * @param height: The display's height
	 */
	public void zoomOut(int width, int height) {
		//calculate the ratio of image pixels to screen pixels
		double hRat = ((1.0 * map.getHeight()) / (1.0 * height));
		double wRat = ((1.0 * map.getWidth()) / ( 1.0 * width));
		
		if (width >= height) {
			// scale up image width if necessary
			if (wRat >= Display.maxRatio()) {
				width = getValidSize(width, wRat, true);
			}
			// scale up width further if height would be too small
			if (scaleHeight(width) < height) {
				width = scaleWidth(height);
			}
			imageArgs[2] = width;
			imageArgs[3] = scaleHeight(width);				
		} else {
			// scale up image height if necessary
			if (hRat >= Display.maxRatio()) {
				height = getValidSize(height, hRat, true);
			}
			// update display arguments
			imageArgs[2] = scaleWidth(height);
			imageArgs[3] = height;		
		}
	}
	
	/**
	 * returns width of image in display pixels
	 * @return the image's display width
	 */
	public int getWidth() {
		return imageArgs[2];
	}
	
	/**
	 * returns height of image in display pixels
	 * @return the image's display height
	 */
	public int getHeight() {
		return imageArgs[3];
	}
	
	// returns a valid image display size, flag = true indicates preference for
	// zooming out, in otherwise
	private int getValidSize(int length, double curRat, boolean zoomOut) {
		if (zoomOut) 
			return (int) ((1.0 * length) / (Display.maxRatio() / curRat));
		else
			return (int) ((1.0 * length) / (Display.minRatio() / curRat));
	}
	
	// returns a width to match height at the image size ratio 
	private int scaleWidth(int height) {
		return (int) (((1.0 * map.getWidth()) / (1.0 * map.getHeight())) * height);
	}
	
	// returns a height to match width at the image size ratio
	private int scaleHeight(int width) {
		return (int) (((1.0 * map.getHeight()) / (1.0 * map.getWidth())) * width);
	}
}
























