package space.util.task;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;
import space.util.sync.lock.SyncLock;
import space.util.task.impl.RunnableTask;

import java.util.concurrent.Executor;

import static space.util.sync.lock.SyncLock.EMPTY_SYNCLOCK_ARRAY;

/**
 * A {@link Task} is something which is created to be executed by some thread in a protected environment,
 * signaling back it's execution, completion and error states. It also allows for Hooks to be added and to be awaited on.
 */
public interface Task extends Barrier {
	
	static RunnableTask create(Runnable run) {
		return new RunnableTask() {
			
			@Override
			public void execute() {
				run.run();
			}
		};
	}
	
	static RunnableTask create(Executor exec, Runnable run) {
		return new RunnableTask() {
			@Override
			protected synchronized void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
			
			@Override
			protected void execute() {
				run.run();
			}
		};
	}
	
	//change state
	
	/**
	 * Starts the Execution of this Task.
	 * Eg. on Java-based Tasks, it will submit all Entry points as {@link Runnable Runnables} to a ThreadPool.<br>
	 * <b>Should only be called once. </b>Calling it more than once should throw an {@link IllegalStateException}.
	 *
	 * @return this
	 */
	default @NotNull Task submit() {
		return submit(EMPTY_SYNCLOCK_ARRAY, EMPTY_BARRIER_ARRAY);
	}
	
	/**
	 * Starts the Execution of this Task.
	 * Eg. on Java-based Tasks, it will submit all Entry points as {@link Runnable Runnables} to a ThreadPool.<br>
	 * <b>Should only be called once. </b>Calling it more than once should throw an {@link IllegalStateException}.
	 *
	 * @return this
	 */
	default @NotNull Task submit(@NotNull Barrier... barriers) {
		return submit(EMPTY_SYNCLOCK_ARRAY, barriers);
	}
	
	/**
	 * Starts the Execution of this Task.
	 * Eg. on Java-based Tasks, it will submit all Entry points as {@link Runnable Runnables} to a ThreadPool.<br>
	 * <b>Should only be called once. </b>Calling it more than once should throw an {@link IllegalStateException}.
	 *
	 * @return this
	 */
	@NotNull Task submit(@NotNull SyncLock[] locks, @NotNull Barrier... barriers);
	
	//getter state
	
	@NotNull TaskState getState();
	
	@Override
	default boolean isFinished() {
		return getState() == TaskState.FINISHED;
	}
}
