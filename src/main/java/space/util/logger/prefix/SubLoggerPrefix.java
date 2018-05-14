package space.util.logger.prefix;

import space.util.logger.LogMessage;
import space.util.logger.Logger;
import space.util.string.builder.CharBufferBuilder1DBackwards;

public class SubLoggerPrefix extends AbstractPrefix {
	
	public SubLoggerPrefix() {
	}
	
	public SubLoggerPrefix(char start, char end) {
		super(start, end);
	}
	
	@Override
	public void accept(LogMessage logMessage) {
		Logger logger = logMessage.logger;
		if (logger == null)
			return;
		
		CharBufferBuilder1DBackwards<?> b = new CharBufferBuilder1DBackwards<>();
		while (true) {
			String name = logger.name();
			logger = logger.parentLogger();
			if (logger == null)
				break;
			b.append(endChar).append(name).append(startChar);
		}
		logMessage.prefix.append(b);
	}
}
