package space.engine.buffer.exception;

public class BufferAddressNullException extends BufferException {
	
	public BufferAddressNullException() {
		this("DirectBuffer address was 0!");
	}
	
	public BufferAddressNullException(String message) {
		super(message);
	}
	
	public BufferAddressNullException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BufferAddressNullException(Throwable cause) {
		super(cause);
	}
	
	public BufferAddressNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
