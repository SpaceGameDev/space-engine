package space.util.sync.task;

public class CollectiveExecutionException extends Exception {
	
	public CollectiveExecutionException() {
		this(null, null);
	}
	
	public CollectiveExecutionException(String message) {
		this(message, null);
	}
	
	public CollectiveExecutionException(Throwable cause) {
		this(null, cause);
	}
	
	public CollectiveExecutionException(String message, Throwable cause) {
		super(message, cause, true, false);
	}
}
