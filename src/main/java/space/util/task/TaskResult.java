package space.util.task;

/**
 * A {@link TaskResult} is an enum having all results a Task can have after executing.
 */
public enum TaskResult {
	
	/**
	 * The Task executed successfully, without throwing an {@link TaskResult#CRASH} or being {@link TaskResult#CANCELED}.
	 */
	SUCCESSFUL,
	
	/**
	 * The {@link Task} threw an {@link Throwable} while executing.
	 * The State of the executed code can be inconsistent.
	 */
	CRASH,
	
	/**
	 * The {@link Task} was canceled. It either did not get executed or interrupted while executing.
	 * The State of the executed code can be inconsistent.
	 * The {@link Task} may have thrown {@link Exception Exceptions}.
	 */
	CANCELED
}
