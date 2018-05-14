package space.util.logger.printer;

import space.util.logger.LogMessage;
import space.util.logger.Logger;
import space.util.string.CharSequence2D;

import java.util.function.BiConsumer;

public class PipePrinter implements BiConsumer<LogMessage, CharSequence2D> {
	
	public Logger in;
	
	public PipePrinter(Logger in) {
		this.in = in;
	}
	
	@Override
	public void accept(LogMessage logMessage, CharSequence2D charSequence2D) {
		in.logDirect(logMessage);
	}
}
