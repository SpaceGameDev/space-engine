package space.util.gui.exception;

public class IllegalGuiElementException extends RuntimeException {
	
	public IllegalGuiElementException() {
	}
	
	public IllegalGuiElementException(String message) {
		super(message);
	}
	
	public IllegalGuiElementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IllegalGuiElementException(Throwable cause) {
		super(cause);
	}
	
	public IllegalGuiElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
