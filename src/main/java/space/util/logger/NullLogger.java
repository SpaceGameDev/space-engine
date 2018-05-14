package space.util.logger;

import space.util.string.CharSequence2D;

/**
 * all_msg > /dev/null
 */
public class NullLogger implements Logger {
	
	public static final NullLogger NULL_LOGGER = new NullLogger();
	
	private NullLogger() {
	}
	
	//subLogger
	@Override
	public String name() {
		return "null";
	}
	
	@Override
	public Logger parentLogger() {
		return null;
	}
	
	@Override
	public Logger subLogger(String name) {
		return this;
	}
	
	//log
	@Override
	public void log(LogLevel level, String str) {
	
	}
	
	@Override
	public void log(LogLevel level, CharSequence2D str) {
	
	}
	
	@Override
	public void logDirect(LogMessage msg) {
	
	}
}
