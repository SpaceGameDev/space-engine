package space.util.logger;

import space.util.string.CharSequence2D;

public abstract class AbstractLogger implements Logger {
	
	public LogLevel minLevel;
	
	@Override
	public void print(LogLevel level, String str) {
		if (minLevel.allowLog(level))
			print0(str);
	}
	
	@Override
	public void print(LogLevel level, CharSequence2D str) {
		if (minLevel.allowLog(level))
			print0(str);
	}
	
	protected abstract void print0(String str);
	
	protected abstract void print0(CharSequence2D str);
}
