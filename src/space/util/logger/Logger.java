package space.util.logger;

import space.util.string.CharSequence2D;
import space.util.string.String2D;

public interface Logger {
	
	//subLogger
	String name();
	
	Logger subLogger(String name);
	
	Logger parentLogger();
	
	//log
	default void log(LogLevel level, String str) {
		logDirect(new LogMessage(this, level, new String2D(str)));
	}
	
	default void log(LogLevel level, CharSequence2D str) {
		logDirect(new LogMessage(this, level, str));
	}
	
	void logDirect(LogMessage msg);
}
