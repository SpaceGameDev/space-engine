package space.util.logger.impl;

import space.util.logger.AbstractLogger;
import space.util.logger.LogLevel;
import space.util.logger.Logger;
import space.util.string.CharSequence2D;
import space.util.string.String2D;

public abstract class LoggerImpl extends AbstractLogger {
	
	//log
	@Override
	public final void log0(LogLevel level, String str) {
		logDirect(new LogMessage(this, level, new String2D(str)));
	}
	
	@Override
	public final void log0(LogLevel level, CharSequence2D str) {
		logDirect(new LogMessage(this, level, str));
	}
	
	public abstract void logDirect(LogMessage msg);
	
	//subLogger
	@Override
	public final Logger subLogger(String name) {
		return subLogger(this, name);
	}
	
	protected abstract Logger subLogger(LoggerImpl parent, String name);
}
