package space.engine.render.window;

public interface IMonitor {
	
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
	
	interface IVideoMode<MONITOR extends IMonitor> {
		
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
	
	static IVideoMode<IMonitor> createVideoModeWindowed(int width, int height) {
		return new IVideoMode<>() {
			@Override
			public IMonitor getMonitor() {
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
}
