package space.util.baseobject.exceptions;

/**
 * thrown if the Supplied Object in {@link space.util.baseobject.Setable#set(Object)} is not a valid applicant to the Method.
 */
public class InvalidSetException extends RuntimeException {
	
	public InvalidSetException() {
	}
	
	public InvalidSetException(Class<?> clazz) {
		this("set() is not supported for Class " + clazz.getName());
	}
	
	public InvalidSetException(String message) {
		super(message);
	}
	
	public InvalidSetException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidSetException(Throwable cause) {
		super(cause);
	}
	
	public InvalidSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
