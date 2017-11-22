package space.util.baseobject.exceptions;

public class MakeNotSupportedException extends BaseObjectException {
	
	public MakeNotSupportedException() {
	}
	
	public MakeNotSupportedException(Class<?> clazz) {
		this("create() is not supported for Class " + clazz.getName());
	}
	
	public MakeNotSupportedException(String message) {
		super(message);
	}
	
	public MakeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MakeNotSupportedException(Throwable cause) {
		super(cause);
	}
	
	public MakeNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
