package space.util.logger;

import space.util.string.String2D;

public abstract class LoggerToString2D extends AbstractLogger {
	
	@Override
	protected void print0(String str) {
		print0(new String2D(str));
	}
}
