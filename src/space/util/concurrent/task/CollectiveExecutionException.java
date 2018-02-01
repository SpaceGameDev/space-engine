package space.util.concurrent.task;

import space.util.concurrent.task.impl.MultiTask;

/**
 * If you're {@link ITask} has multiple Sub-{@link ITask ITasks} (like {@link MultiTask}),
 * you can use this Exception to "collect" or better {@link Exception#addSuppressed(Throwable)} all the Exceptions of the Sub-{@link ITask ITasks}.
 */
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
