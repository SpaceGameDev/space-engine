package space.util.logger.prefix;

import space.util.logger.LogMessage;

import java.util.function.Consumer;

@FunctionalInterface
public interface Prefix extends Consumer<LogMessage> {
	
}
