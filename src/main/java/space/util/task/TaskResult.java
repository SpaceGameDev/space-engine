package space.util.task;

/**
 * A {@link TaskResult} is an enum having all results a Task can have after executing.
 * A {@link TaskResult} can be overridden by a {@link TaskResult} of higher priority.
 */
public enum TaskResult {
	
	/**
	 * The Task executed successfully, without throwing an {@link TaskResult#EXCEPTION} or being {@link TaskResult#CANCELED}.
	 * <p>
	 * Can be overridden by {@link TaskResult#EXCEPTION} or {@link TaskResult#CANCELED}.
	 */
	SUCCESSFUL,
	
	/**
	 * The {@link Task} threw an {@link Throwable} while executing.
	 * The State of the executed code can be inconsistent.
	 * <p>
	 * Can be overridden by {@link TaskResult#CANCELED}.
	 */
	EXCEPTION,
	
	/**
	 * The {@link Task} was canceled. It either did not get executed or interrupted while executing.
	 * The State of the executed code can be inconsistent.
	 * The {@link Task} may have thrown {@link Exception Exceptions}.
	 * <p>
	 * Can not be overridden.
	 */
	CANCELED
}
