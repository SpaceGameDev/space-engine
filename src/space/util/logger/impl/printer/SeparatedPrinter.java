package space.util.logger.impl.printer;

import space.util.logger.LogLevel;
import space.util.logger.impl.LogMessage;
import space.util.string.CharSequence2D;

import java.util.function.BiConsumer;

public class SeparatedPrinter implements BiConsumer<LogMessage, CharSequence2D> {
	
	public BiConsumer<LogMessage, CharSequence2D> out;
	public BiConsumer<LogMessage, CharSequence2D> err;
	public LogLevel minErrLevel;
	
	public SeparatedPrinter(BiConsumer<LogMessage, CharSequence2D> out, BiConsumer<LogMessage, CharSequence2D> err) {
		this(out, err, LogLevel.WARNING);
	}
	
	public SeparatedPrinter(BiConsumer<LogMessage, CharSequence2D> out, BiConsumer<LogMessage, CharSequence2D> err, LogLevel minErrLevel) {
		this.out = out;
		this.err = err;
		this.minErrLevel = minErrLevel;
	}
	
	@Override
	public void accept(LogMessage logMessage, CharSequence2D msg) {
		(minErrLevel.allowLog(logMessage.level) ? err : out).accept(logMessage, msg);
	}
}
