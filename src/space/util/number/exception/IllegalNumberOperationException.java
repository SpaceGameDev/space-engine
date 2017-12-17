package space.util.number.exception;

public class IllegalNumberOperationException extends NumberException {
	
	public IllegalNumberOperationException() {
	}
	
	public IllegalNumberOperationException(String message) {
		super(message);
	}
	
	public IllegalNumberOperationException(Throwable cause) {
		super(cause);
	}
	
	public IllegalNumberOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
