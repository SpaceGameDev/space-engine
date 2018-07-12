package space.util.task;

/**
 * A {@link TaskResult} is an enum having all results a Task can have after executing.
 * A {@link TaskResult} can be overridden by a {@link TaskResult} of higher priority (determined by the mask).
 */
public enum TaskResult {
	
	/**
	 * The Task executed successfully, without throwing an {@link TaskResult#EXCEPTION} or being {@link TaskResult#CANCELED}.
	 * Can be overridden by {@link TaskResult#EXCEPTION} or {@link TaskResult#CANCELED}.
	 */
	DONE(1),
	
	/**
	 * The {@link Task} threw an {@link Throwable} while executing.
	 * The State of the executed code can be inconsistent.
	 * Can be overridden by {@link TaskResult#CANCELED}.
	 */
	EXCEPTION(2),
	
	/**
	 * The {@link Task} was canceled. It either did not get executed or interrupted while executing.
	 * The State of the executed code can be inconsistent.
	 * The {@link Task} may have thrown {@link Exception Exceptions}.
	 * Can not be overridden.
	 */
	CANCELED(4);
	
	/**
	 * the mask of this state, usually used to restrict execution of a workload to a certain state of previous {@link Task ITasks}
	 */
	public final int mask;
	
	TaskResult(int mask) {
		this.mask = mask;
	}
	
	public boolean insideMask(int mask) {
		return (mask & this.mask) != 0;
	}
	
}
