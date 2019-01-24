package space.engine.logger;

import space.engine.string.CharSequence2D;
import space.engine.string.builder.CharBufferBuilder1D;

public class LogMessage {
	
	public Logger logger;
	public LogLevel level;
	public CharSequence2D msg;
	
	public CharBufferBuilder1D<?> prefix = new CharBufferBuilder1D<>();
	
	public LogMessage(Logger logger, LogLevel level, CharSequence2D msg) {
		this.logger = logger;
		this.level = level;
		this.msg = msg;
	}
}
