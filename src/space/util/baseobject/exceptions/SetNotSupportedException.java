package space.util.baseobject.exceptions;

public class SetNotSupportedException extends BaseObjectException {
	
	public SetNotSupportedException() {
	}
	
	public SetNotSupportedException(Class<?> clazz) {
		this("set() is not supported for Class " + clazz.getName());
	}
	
	public SetNotSupportedException(String message) {
		super(message);
	}
	
	public SetNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SetNotSupportedException(Throwable cause) {
		super(cause);
	}
	
	public SetNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
