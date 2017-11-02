package space.util.conversion.exception;

public class InvalidConversionException extends ConversionException {
	
	public InvalidConversionException() {
	}
	
	public InvalidConversionException(String message) {
		super(message);
	}
	
	public InvalidConversionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidConversionException(Throwable cause) {
		super(cause);
	}
	
	public InvalidConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
