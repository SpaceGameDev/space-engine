package space.util.logger.impl;

import space.util.logger.LogLevel;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder1D;

public class LogMessage {
	
	public LoggerImpl logger;
	public LogLevel level;
	public CharSequence2D msg;
	
	public CharBufferBuilder1D prefix = new CharBufferBuilder1D();
	
	public LogMessage(LoggerImpl logger, LogLevel level, CharSequence2D msg) {
		this.logger = logger;
		this.level = level;
		this.msg = msg;
	}
}
