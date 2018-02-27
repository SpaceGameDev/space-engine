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
	
	interface IVideoMode {
		
		int width();
		
		int height();
		
		int refreshRate();
		
		int bitsR();
		
		int bitsG();
		
		int bitsB();
	}
	
	class VideoMode implements IVideoMode {
		
		public int width;
		public int height;
		public int refreshRate;
		public int bitsR;
		public int bitsG;
		public int bitsB;
		
		public VideoMode() {
		}
		
		public VideoMode(int width, int height, int refreshRate, int bitsR, int bitsG, int bitsB) {
			this.width = width;
			this.height = height;
			this.refreshRate = refreshRate;
			this.bitsR = bitsR;
			this.bitsG = bitsG;
			this.bitsB = bitsB;
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
			return refreshRate;
		}
		
		@Override
		public int bitsR() {
			return bitsR;
		}
		
		@Override
		public int bitsG() {
			return bitsG;
		}
		
		@Override
		public int bitsB() {
			return bitsB;
		}
	}
}
