package space.engine.window;

import org.jetbrains.annotations.Nullable;

/**
 * The {@link VideoMode} is a Description of how a Window will be in it's fundamental properties.
 * <b>NOTE: all of these properties are "hints", not actual forced properties!</b>
 * The result may differ from the hints you set, depending on implementation.
 * There are two mayor types: {@link VideoModeMonitor} and {@link VideoModeDesktop}. There may be other types created,
 * but if unsupported the Window implementation may throw an Exception.
 * <p>
 * Shared Properties:
 * <ul>
 * <li>{@link VideoModeMonitor#width()}</li>
 * <li>{@link VideoModeMonitor#height()}</li>
 * </ul>
 * <p>
 * additional Properties of {@link VideoModeMonitor}:
 * <ul>
 * <li>{@link VideoModeMonitor#refreshRate()}</li>
 * <li>{@link VideoModeMonitor#bitsR()}</li>
 * <li>{@link VideoModeMonitor#bitsG()}</li>
 * <li>{@link VideoModeMonitor#bitsB()}</li>
 * </ul>
 * <p>
 * additional Properties of {@link VideoModeDesktop}:
 * <ul>
 * <li>{@link VideoModeDesktop#hasTransparency()} (if supported)</li>
 * </ul>
 */
public interface VideoMode {
	
	int width();
	
	int height();
	
	interface VideoModeMonitor extends VideoMode {
		
		@Nullable Monitor getMonitor();
		
		int refreshRate();
		
		int bitsR();
		
		int bitsG();
		
		int bitsB();
	}
	
	interface VideoModeDesktop extends VideoMode {
		
		int posX();
		
		int posY();
		
		boolean hasTransparency();
	}
	
	//create VideoMode for Desktop
	static VideoMode createVideoModeDesktop(int width, int height) {
		return createVideoModeDesktop(width, height, 0, 0, false);
	}
	
	static VideoMode createVideoModeDesktop(int width, int height, int posX, int posY, boolean hasTransparency) {
		return new VideoModeDesktop() {
			@Override
			public int width() {
				return width;
			}
			
			@Override
			public int height() {
				return height;
			}
			
			@Override
			public int posX() {
				return posX;
			}
			
			@Override
			public int posY() {
				return posY;
			}
			
			@Override
			public boolean hasTransparency() {
				return hasTransparency;
			}
		};
	}
}
