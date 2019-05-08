package space.engine.logger.prefix;

import space.engine.logger.LogMessage;
import space.engine.logger.Logger;

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
		
		StringBuilder b = new StringBuilder();
		while (true) {
			String name = logger.name();
			logger = logger.parentLogger();
			if (logger == null)
				break;
			b.append(endChar).append(name).append(startChar);
		}
		logMessage.prefix.append(b.reverse());
	}
}
