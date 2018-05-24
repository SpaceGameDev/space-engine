package space.engine.window;

public interface Monitor {
	
	/**
	 * name of the Monitor for identification
	 */
	String name();
	
	/**
	 * width in mm
	 */
	int physicalWidth();
	
	/**
	 * height in mm
	 */
	int physicalHeight();
	
	/**
	 * relative position to other monitors
	 */
	int posX();
	
	/**
	 * relative position to other monitors
	 */
	int posY();
	
	/**
	 * the default video mode used of the desktop environment
	 */
	VideoMode getDefaultVideoMode();
	
	/**
	 * get all available video modes
	 */
	VideoMode[] getAvailableVideoModes();
}
