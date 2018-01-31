package space.util.logger.impl;

import space.util.logger.LogLevel;
import space.util.logger.Logger;
import space.util.logger.impl.prefix.LogLevelPrefix;
import space.util.logger.impl.prefix.Prefix;
import space.util.logger.impl.prefix.SubLoggerPrefix;
import space.util.logger.impl.prefix.ThreadPrefix;
import space.util.logger.impl.prefix.TimePrefix;
import space.util.logger.impl.printer.SeparatedPrinter;
import space.util.logger.impl.printer.SimpleStringPrinter;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.task.chained.ChainedTaskBuilder;
import space.util.task.chained.ChainedTaskEntry;
import space.util.task.typehandler.ITypeHandler;
import space.util.task.typehandler.TypeBiConsumer;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class BaseLogger extends LoggerImpl {
	
	ChainedTaskBuilder<Prefix> handler = new ChainedTaskBuilder<>(true);
	ChainedTaskBuilder<BiConsumer<LogMessage, CharSequence2D>> printers = new ChainedTaskBuilder<>(true);
	public String prefixMessageSeparator;
	
	public BaseLogger() {
		this(": ");
	}
	
	public BaseLogger(String prefixMessageSeparator) {
		this.prefixMessageSeparator = prefixMessageSeparator;
	}
	
	public ChainedTaskEntry<BiConsumer<LogMessage, CharSequence2D>> addPrinter(String uuid, BiConsumer<LogMessage, CharSequence2D> o) {
		return printers.addTask(uuid, o);
	}
	
	public boolean removePrinter(ChainedTaskEntry<BiConsumer<LogMessage, CharSequence2D>> o) {
		return printers.removeTask(o);
	}
	
	@Override
	public void logDirect(LogMessage msg) {
		try {
			handler.execute(new ITypeHandler<Prefix>() {
				@Override
				public void accept(Prefix consumer) {
					consumer.accept(msg);
				}
				
				@Override
				public boolean allowMultithreading() {
					return false;
				}
			}).awaitAndRethrow();
			printers.execute(new TypeBiConsumer<>(msg, new CharBufferBuilder2D<>().append(msg.prefix).append(prefixMessageSeparator).append(msg.msg).toString2D())).awaitAndRethrow();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public Logger subLogger(LoggerImpl parent, String name) {
		return new SubLogger(parent, name);
	}
	
	/**
	 * adds a {@link java.util.Date} (formatted with HH:mm:ss), {@link Thread}, {@link LogLevel} and {@link SubLogger} prefix to the {@link BaseLogger}
	 */
	public static BaseLogger defaultHandler(BaseLogger logger) {
		logger.handler.addTask("time", 0, new TimePrefix(new SimpleDateFormat("HH:mm:ss")));
		logger.handler.addTask("thread", 1, new ThreadPrefix());
		logger.handler.addTask("loglevel", 2, new LogLevelPrefix());
		logger.handler.addTask("sublogger", 3, new SubLoggerPrefix());
		return logger;
	}
	
	/**
	 * adds the default System.out and System.err {@link java.io.PrintStream}s as Printer to the BaseLogger
	 */
	public static BaseLogger defaultPrinter(BaseLogger logger) {
		logger.addPrinter("system", new SeparatedPrinter(new SimpleStringPrinter(System.out), new SimpleStringPrinter(System.err)));
		return logger;
	}
}
