package space.util.logger;

import space.util.concurrent.task.chained.ChainedTaskBuilder;
import space.util.concurrent.task.typehandler.ITypeHandler;
import space.util.concurrent.task.typehandler.TypeBiConsumer;
import space.util.logger.prefix.LogLevelPrefix;
import space.util.logger.prefix.Prefix;
import space.util.logger.prefix.SubLoggerPrefix;
import space.util.logger.prefix.ThreadPrefix;
import space.util.logger.prefix.TimePrefix;
import space.util.logger.printer.SeparatedPrinter;
import space.util.logger.printer.SimpleStringPrinter;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class BaseLogger extends AbstractLogger {
	
	ChainedTaskBuilder<Prefix> handler = new ChainedTaskBuilder<>(true);
	ChainedTaskBuilder<BiConsumer<LogMessage, CharSequence2D>> printer = new ChainedTaskBuilder<>(true);
	public String prefixMessageSeparator;
	
	public BaseLogger() {
		this(": ");
	}
	
	public BaseLogger(String prefixMessageSeparator) {
		this.prefixMessageSeparator = prefixMessageSeparator;
	}
	
	//subLogger
	@Override
	public String name() {
		return "root";
	}
	
	@Override
	public Logger parentLogger() {
		return null;
	}
	
	@Override
	public Logger subLogger(String name) {
		return new SubLogger(this, name);
	}
	
	//log
	@Override
	public void logDirect0(LogMessage msg) {
		try {
			handler.execute(new ITypeHandler<>() {
				@Override
				public void accept(Prefix consumer) {
					consumer.accept(msg);
				}
				
				@Override
				public boolean allowMultithreading() {
					return false;
				}
			}).awaitAndRethrow();
			printer.execute(new TypeBiConsumer<>(msg, new CharBufferBuilder2D<>().append(msg.prefix).append(prefixMessageSeparator).append(msg.msg).toString2D())).awaitAndRethrow();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	//utility
	
	/**
	 * adds a {@link java.util.Date} (formatted with HH:mm:ss), {@link Thread}, {@link LogLevel} and {@link SubLogger} prefix to the {@link BaseLogger}
	 */
	public static BaseLogger defaultHandler(BaseLogger logger) {
		logger.handler.addHook("time", 0, new TimePrefix(new SimpleDateFormat("HH:mm:ss")));
		logger.handler.addHook("thread", 1, new ThreadPrefix());
		logger.handler.addHook("loglevel", 2, new LogLevelPrefix());
		logger.handler.addHook("sublogger", 3, new SubLoggerPrefix());
		return logger;
	}
	
	/**
	 * adds the default System.out and System.err {@link java.io.PrintStream}s as Printer to the BaseLogger
	 */
	public static BaseLogger defaultPrinter(BaseLogger logger) {
		logger.printer.addHook("system", new SeparatedPrinter(new SimpleStringPrinter(System.out), new SimpleStringPrinter(System.err)));
		return logger;
	}
}
