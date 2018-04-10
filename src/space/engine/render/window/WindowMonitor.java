package space.engine.render.window;

public interface WindowMonitor {
	
	String name();
	
	/**
	 * @return width in mm
	 */
	int physicalWidth();
	
	/**
	 * @return height in mm
	 */
	int physicalHeight();
	
	int posX();
	
	int posY();
	
	IVideoMode getCurrentVideoMode();
	
	IVideoMode[] getAvailableVideoModes();
	
	static IVideoMode<WindowMonitor> createVideoModeWindowed(int width, int height) {
		return new IVideoMode<>() {
			@Override
			public WindowMonitor getMonitor() {
				return null;
			}
			
			@Override
			public int width() {
				return width;
			}
			
			@Override
			public int height() {
				return height;
			}
			
			@Override
			public int refreshRate() {
				return -1;
			}
			
			@Override
			public int bitsR() {
				return -1;
			}
			
			@Override
			public int bitsG() {
				return -1;
			}
			
			@Override
			public int bitsB() {
				return -1;
			}
		};
	}
	
	interface IVideoMode<MONITOR extends WindowMonitor> {
		
		MONITOR getMonitor();
		
		int width();
		
		int height();
		
		/**
		 * default: -1
		 */
		int refreshRate();
		
		/**
		 * default: -1
		 */
		int bitsR();
		
		/**
		 * default: -1
		 */
		int bitsG();
		
		/**
		 * default: -1
		 */
		int bitsB();
	}
}
