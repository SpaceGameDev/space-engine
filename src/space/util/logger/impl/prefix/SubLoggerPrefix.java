package space.util.logger.impl.prefix;

import space.util.logger.impl.LogMessage;
import space.util.logger.impl.LoggerImpl;
import space.util.logger.impl.SubLogger;

public class SubLoggerPrefix extends AbstractPrefix {
	
	public SubLoggerPrefix() {
	}
	
	public SubLoggerPrefix(char start, char end) {
		super(start, end);
	}
	
	@Override
	public void accept(LogMessage logMessage) {
		LoggerImpl logger = logMessage.logger;
		while (logger instanceof SubLogger) {
			SubLogger subLogger = (SubLogger) logger;
			logMessage.prefix.append(start).append(subLogger.name).append(end);
			logger = subLogger.parent;
		}
	}
}
