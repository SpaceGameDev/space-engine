package space.engine.logger;

import org.jetbrains.annotations.NotNull;
import space.engine.event.Event;
import space.engine.event.EventEntry;
import space.engine.event.SequentialEventBuilder;
import space.engine.event.typehandler.TypeBiConsumer;
import space.engine.logger.prefix.LogLevelPrefix;
import space.engine.logger.prefix.Prefix;
import space.engine.logger.prefix.SubLoggerPrefix;
import space.engine.logger.prefix.ThreadPrefix;
import space.engine.logger.prefix.TimePrefix;
import space.engine.logger.printer.SeparatedPrinter;
import space.engine.logger.printer.SimpleStringPrinter;
import space.engine.string.CharSequence2D;
import space.engine.string.builder.CharBufferBuilder2D;
import space.engine.sync.Tasks;
import space.engine.sync.barrier.Barrier;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.BiConsumer;

public class BaseLogger extends AbstractLogger {
	
	public static final EventEntry<Prefix> PREFIX_TIME = new EventEntry<>(new TimePrefix(new SimpleDateFormat("HH:mm:ss")));
	public static final EventEntry<Prefix> PREFIX_THREAD = new EventEntry<>(new ThreadPrefix());
	public static final EventEntry<Prefix> PREFIX_LOGLEVEL = new EventEntry<>(new LogLevelPrefix());
	public static final EventEntry<Prefix> PREFIX_SUBLOGGER = new EventEntry<>(new SubLoggerPrefix());
	
	public static final EventEntry<BiConsumer<LogMessage, CharSequence2D>> PRINTER_SEPARATED = new EventEntry<>(new SeparatedPrinter(new SimpleStringPrinter(System.out),
																																	 new SimpleStringPrinter(System.err)));
	public static final EventEntry<BiConsumer<LogMessage, CharSequence2D>> PRINTER_OUT = new EventEntry<>(new SimpleStringPrinter(System.out));
	
	Event<Prefix> handler = new SequentialEventBuilder<>();
	Event<BiConsumer<LogMessage, CharSequence2D>> printer = new SequentialEventBuilder<>();
	public String prefixMessageSeparator;
	
	public BaseLogger() {
		this(": ");
	}
	
	public BaseLogger(String prefixMessageSeparator) {
		this.prefixMessageSeparator = prefixMessageSeparator;
	}
	
	//subLogger
	@NotNull
	@Override
	public String name() {
		return "root";
	}
	
	@Override
	public Logger parentLogger() {
		return null;
	}
	
	@NotNull
	@Override
	public Logger subLogger(String name) {
		return new SubLogger(this, name);
	}
	
	//log
	@Override
	public Barrier logDirect0(LogMessage msg) {
		return Tasks.sequential(List.of(
				handler.taskCreator(consumer -> consumer.accept(msg)),
				printer.taskCreator(new TypeBiConsumer<>(msg, new CharBufferBuilder2D<>().append(msg.prefix).append(prefixMessageSeparator).append(msg.msg).toString2D()))
		)).submit();
	}
	
	//utility
	
	/**
	 * adds a {@link java.util.Date} (formatted with HH:mm:ss), {@link Thread}, {@link LogLevel} and {@link SubLogger} prefix to the {@link BaseLogger}
	 */
	public static BaseLogger defaultHandler(BaseLogger logger) {
		logger.handler.addHook(PREFIX_TIME);
		logger.handler.addHook(PREFIX_THREAD);
		logger.handler.addHook(PREFIX_LOGLEVEL);
		logger.handler.addHook(PREFIX_SUBLOGGER);
		return logger;
	}
	
	/**
	 * adds the default System.out and System.err {@link java.io.PrintStream}s as Printer to the BaseLogger
	 */
	public static BaseLogger defaultPrinter(BaseLogger logger) {
		logger.printer.addHook(PRINTER_SEPARATED);
		return logger;
	}
}
