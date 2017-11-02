package space.util.sync.task;

/**
 * A {@link TaskResult} is an enum having all results a ITask can have after executing.
 */
public enum TaskResult {
	
	/**
	 * The ITask executed successfully, without being CANCELED or throwing an EXCEPTION.
	 */
	DONE(1),
	
	/**
	 * The ITask was canceled. It either did not get executed or interrupted while executing.
	 * The State of the executed code is probably inconsistent.
	 */
	CANCELED(2),
	
	/**
	 * The ITask threw an {@link Throwable} while executing.
	 * The State of the executed code is probably inconsistent.
	 */
	EXCEPTION(4);
	
	public final int mask;
	
	TaskResult(int mask) {
		this.mask = mask;
	}
	
	public boolean insideMask(int mask) {
		return (mask & this.mask) != 0;
	}
	
}
