package space.util.logger.impl.prefix;

import space.util.logger.impl.LogMessage;
import space.util.logger.impl.LoggerImpl;
import space.util.logger.impl.SubLogger;
import space.util.string.builder.CharBufferBuilder1DBackwards;

public class SubLoggerPrefix extends AbstractPrefix {
	
	public SubLoggerPrefix() {
	}
	
	public SubLoggerPrefix(char start, char end) {
		super(start, end);
	}
	
	@Override
	public void accept(LogMessage logMessage) {
		CharBufferBuilder1DBackwards<?> b = new CharBufferBuilder1DBackwards<>();
		LoggerImpl logger = logMessage.logger;
		while (logger instanceof SubLogger) {
			SubLogger subLogger = (SubLogger) logger;
			b.append(endChar).append(subLogger.name).append(startChar);
			logger = subLogger.parent;
		}
		logMessage.prefix.append(b);
	}
}
