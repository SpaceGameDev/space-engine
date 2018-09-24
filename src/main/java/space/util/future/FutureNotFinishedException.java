package space.util.future;

public class FutureNotFinishedException extends Exception {
	
	public FutureNotFinishedException() {
	}
	
	public FutureNotFinishedException(String message) {
		super(message);
	}
	
	public FutureNotFinishedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FutureNotFinishedException(Throwable cause) {
		super(cause);
	}
}
