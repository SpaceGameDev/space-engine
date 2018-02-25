package space.util.logger.prefix;

import space.util.logger.LogMessage;

public class ThreadPrefix extends AbstractPrefix {
	
	public ThreadPrefix() {
	}
	
	public ThreadPrefix(char start, char end) {
		super(start, end);
	}
	
	@Override
	public void accept(LogMessage logMessage) {
		logMessage.prefix.append(startChar).append(Thread.currentThread().getName()).append(endChar);
	}
}
