package space.util.baseobject.exceptions;

public class BaseObjectException extends RuntimeException {
	
	public BaseObjectException() {
	}
	
	public BaseObjectException(String message) {
		super(message);
	}
	
	public BaseObjectException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseObjectException(Throwable cause) {
		super(cause);
	}
	
	public BaseObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
