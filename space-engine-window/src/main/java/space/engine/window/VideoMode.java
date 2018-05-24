package space.engine.window;

import org.jetbrains.annotations.Nullable;

public interface VideoMode {
	
	@Nullable Monitor getMonitor();
	
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
	
	/**
	 * default: -1
	 */
	int bitsA();
	
	static VideoMode createVideoModeWindowed(int width, int height) {
		return createVideoModeWindowed(width, height, false);
	}
	
	static VideoMode createVideoModeWindowed(int width, int height, boolean hasTransparent) {
		return new VideoMode() {
			@Override
			public Monitor getMonitor() {
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
			
			@Override
			public int bitsA() {
				return hasTransparent ? 8 : 0;
			}
		};
	}
}
