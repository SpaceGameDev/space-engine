package space.util.logger;

import space.util.string.CharSequence2D;

public interface Logger {
	
	//log
	void log(LogLevel level, String str);
	
	void log(LogLevel level, CharSequence2D str);
	
	//subLogger
	Logger subLogger(String name);
}
