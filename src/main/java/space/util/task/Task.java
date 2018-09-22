package space.util.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.barrier.Barrier;

/**
 * A {@link Task} is something which is created to be executed by some thread in a protected environment,
 * signaling back it's execution, completion and error states. It also allows for Hooks to be added and to be awaited on.
 */
public interface Task extends Barrier {
	
	//change state
	
	/**
	 * Submits the tasks work to the executor.
	 * Should only be called once. Calling it multiple times may lead to undefined behavior.
	 * An implementation can choose to submit multiple {@link Runnable}s.
	 *
	 * @return this
	 */
	@NotNull Task submit();
	
	@NotNull Task submit(Barrier... barriers);
	
	/**
	 * Cancels the execution of the task.
	 *
	 * @param mayInterrupt if it should set the interrupt flags on all threads executing any work for this task
	 * @return true if the task was canceled early enough to not have finished, meant for informational purpose
	 */
	boolean cancel(boolean mayInterrupt);
	
	//getter state
	
	@NotNull TaskState getState();
	
	@Override
	default boolean isTriggered() {
		return getState() == TaskState.FINISHED;
	}
	
	/**
	 * Gets the {@link TaskResult} of this {@link Task}.
	 * <p>This Method is NOT marked with @Nullable, as it will ONLY return null when the Task is not finished. When it IS finished it will always be NotNull.</p>
	 *
	 * @return the {@link TaskResult} of the {@link Task} or null, if not already finished
	 */
	@Nullable TaskResult getResult();
	
}
