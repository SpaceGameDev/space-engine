package space.engine.window.exception;

public class WindowFrameworkInitializationException extends WindowException {
	
	public WindowFrameworkInitializationException() {
	}
	
	public WindowFrameworkInitializationException(String message) {
		super(message);
	}
	
	public WindowFrameworkInitializationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WindowFrameworkInitializationException(Throwable cause) {
		super(cause);
	}
	
	public WindowFrameworkInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
