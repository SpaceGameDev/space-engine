package space.engine.logger;

import space.engine.string.CharSequence2D;
import space.engine.string.builder.CharBufferBuilder1D;

public class LogMessage {
	
	public final Thread thread;
	public Logger logger;
	public LogLevel level;
	public CharSequence2D msg;
	
	public CharBufferBuilder1D<?> prefix = new CharBufferBuilder1D<>();
	
	public LogMessage(Thread thread, Logger logger, LogLevel level, CharSequence2D msg) {
		this.thread = thread;
		this.logger = logger;
		this.level = level;
		this.msg = msg;
	}
}
