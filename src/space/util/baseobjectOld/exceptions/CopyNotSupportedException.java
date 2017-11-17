package space.util.baseobjectOld.exceptions;

public class CopyNotSupportedException extends RuntimeException {
	
	public CopyNotSupportedException() {
	}
	
	public CopyNotSupportedException(String message) {
		super(message);
	}
	
	public CopyNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CopyNotSupportedException(Throwable cause) {
		super(cause);
	}
	
	public CopyNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
