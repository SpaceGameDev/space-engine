package space.util.logger.impl.prefix;

import space.util.logger.impl.LogMessage;

import java.util.function.Consumer;

@FunctionalInterface
public interface Prefix extends Consumer<LogMessage> {
	
}
