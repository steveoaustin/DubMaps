package dubMaps;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/*
 * Display mimics static class behavior to be used by all GUI components that need 
 * information about the current screen dimensions. Holds screen width and height information,
 * and the maximum and minimum scaling ratios for GUI images
 */
public final class Display {

	// scale ratios in the form of screen pixels to image pixels
	private static final double MIN_SCALE_RATIO = 1.0, MAX_SCALE_RATIO = 5.0;
	private static GraphicsDevice screen = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static int screenWidth = screen.getDisplayMode().getWidth();
	private static int screenHeight = screen.getDisplayMode().getHeight();
	
	// private constructor mimics static class behavior
	private Display() {}
	
	/**
	 * returns the maximum scale ratio
	 * @return MAX_SCALE_RATIO: the max ratio for screen pixels to image pixels
	 */
	public static double getMaxRatio() {
		return MAX_SCALE_RATIO;
	}
	
	/**
	 * returns the minimum scale ratio
	 * @return MIN_SCALE_RATIO: the min ratio for screen pixels to image pixels
	 */
	public static double getMinRatio() {
		return MIN_SCALE_RATIO;
	}
	
	/**
	 * updates and returns the current screen's width
	 * @return screen width as an integer
	 * TODO: get width of the current screen, not default
	 */
	public static int getWidth() {	
		screenWidth = screen.getDisplayMode().getWidth();
		return (int) screenWidth;
	}
	
	/**
	 * updates and returns the current screen's height
	 * @return screen height as an integer
	 * TODO: get height of the current screen, not default
	 */
	public static int getHeight() {
		screenHeight = screen.getDisplayMode().getHeight();
		return (int) screenHeight;
	}
	
}
