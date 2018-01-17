package space.util.logger;

import space.util.string.CharSequence2D;

public abstract class AbstractLogger implements Logger {
	
	public LogLevel minLevel = LogLevel.INFO;
	
	public void setMinLevel(LogLevel minLevel) {
		this.minLevel = minLevel;
	}
	
	//log
	@Override
	public void log(LogLevel level, String str) {
		if (minLevel.allowLog(level))
			log0(level, str);
	}
	
	@Override
	public void log(LogLevel level, CharSequence2D str) {
		if (minLevel.allowLog(level))
			log0(level, str);
	}
	
	protected abstract void log0(LogLevel level, String str);
	
	protected abstract void log0(LogLevel level, CharSequence2D str);
}
