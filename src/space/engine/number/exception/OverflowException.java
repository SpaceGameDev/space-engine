package space.engine.number.exception;

public class OverflowException extends NumberException {
	
	public OverflowException() {
	}
	
	public OverflowException(String message) {
		super(message);
	}
	
	public OverflowException(Throwable cause) {
		super(cause);
	}
	
	public OverflowException(String message, Throwable cause) {
		super(message, cause);
	}
}
