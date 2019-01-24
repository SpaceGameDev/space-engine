package space.engine.task;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.lock.SyncLock;

import static space.engine.sync.barrier.Barrier.EMPTY_BARRIER_ARRAY;
import static space.engine.sync.lock.SyncLock.EMPTY_SYNCLOCK_ARRAY;

/**
 * A {@link TaskCreator} is something which is created to be executed by some thread in a protected environment,
 * signaling back it's execution, completion and error states. It also allows for Hooks to be added and to be awaited on.
 */
public interface TaskCreator<TASK extends Barrier> {
	
	//change state
	
	/**
	 * Starts the Execution of this Task.
	 * Eg. on Java-based Tasks, it will submit all Entry points as {@link Runnable Runnables} to a ThreadPool.<br>
	 * <b>Should only be called once. </b>Calling it more than once should throw an {@link IllegalStateException}.
	 *
	 * @return the created Task of generic type TASK
	 */
	default @NotNull TASK submit() {
		return submit(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY);
	}
	
	/**
	 * Starts the Execution of this Task.
	 * Eg. on Java-based Tasks, it will submit all Entry points as {@link Runnable Runnables} to a ThreadPool.<br>
	 * <b>Should only be called once. </b>Calling it more than once should throw an {@link IllegalStateException}.
	 *
	 * @return the created Task of generic type TASK
	 */
	default @NotNull TASK submit(@NotNull Barrier... barriers) {
		return submit(EMPTY_SYNCLOCK_ARRAY, barriers);
	}
	
	/**
	 * Starts the Execution of this Task.
	 * Eg. on Java-based Tasks, it will submit all Entry points as {@link Runnable Runnables} to a ThreadPool.<br>
	 *
	 * @return the created Task of generic type TASK
	 */
	@NotNull TASK submit(@NotNull SyncLock[] locks, @NotNull Barrier... barriers);
}
