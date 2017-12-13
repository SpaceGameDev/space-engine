package space.util.buffer.buffers.exception;

public class NullBufferException extends RuntimeException {
	
	public NullBufferException() {
		this("Buffer address was 0!");
	}
	
	public NullBufferException(String message) {
		super(message);
	}
	
	public NullBufferException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NullBufferException(Throwable cause) {
		super(cause);
	}
	
	public NullBufferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
