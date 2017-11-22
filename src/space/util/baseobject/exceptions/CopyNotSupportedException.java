package space.util.baseobject.exceptions;

public class CopyNotSupportedException extends BaseObjectException {
	
	public CopyNotSupportedException() {
	}
	
	public CopyNotSupportedException(Class<?> clazz) {
		this("copy() is not supported for Class " + clazz.getName());
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
