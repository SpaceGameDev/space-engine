package space.util.logger;

import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder1D;

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
