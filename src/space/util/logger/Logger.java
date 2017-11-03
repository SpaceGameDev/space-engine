package space.util.logger;

import space.util.string.CharSequence2D;

public interface Logger {
	
	void print(LogLevel level, String str);
	
	void print(LogLevel level, CharSequence2D str);
}
