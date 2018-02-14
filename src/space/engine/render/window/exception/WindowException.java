package space.engine.render.window.exception;

public class WindowException extends RuntimeException {
	
	public WindowException() {
	}
	
	public WindowException(String message) {
		super(message);
	}
	
	public WindowException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WindowException(Throwable cause) {
		super(cause);
	}
	
	public WindowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
