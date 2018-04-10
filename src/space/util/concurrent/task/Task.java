package space.util.concurrent.task;

import space.util.concurrent.awaitable.Awaitable;
import space.util.concurrent.event.Event;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * A {@link Task} is something which is created to be executed by some thread in a protected environment,
 * signaling back it's execution, completion and error states. It also allows for Hooks to be added and to be awaited on.
 */
public interface Task extends Event<Consumer<Task>>, Awaitable {
	
	//run
	
	/**
	 * submit task(s) to the executor. An implementation can choose to submit multiple {@link Runnable}s.
	 *
	 * @param executor to submit any tasks to
	 */
	void submit(Executor executor);
	
	/**
	 * cancels the execution of the task
	 *
	 * @param mayInterrupt if it should set the interrupt flags on all currently executing threads
	 * @return true if the task was canceled early enough to not have finished, meant for informational purpose
	 */
	boolean cancel(boolean mayInterrupt);
	
	//getState
	
	/**
	 * check whether the execution of this Task has started
	 *
	 * @return true if it has started
	 */
	boolean executionStarted();
	
	/**
	 * check whether the execution of this thread is done
	 *
	 * @return true if execution is done
	 */
	boolean isDone();
	
	@Override
	default boolean isSignaled() {
		return isDone();
	}
	
	//result
	
	/**
	 * waits until the {@link Task} is complete
	 */
	@Override
	void await() throws InterruptedException;
	
	/**
	 * waits until the {@link Task} is complete with a timeout
	 */
	@Override
	void await(long time, TimeUnit unit) throws InterruptedException;
	
	//throw
	
	/**
	 * gets the {@link TaskResult} of this {@link Task}
	 *
	 * @return the {@link TaskResult} of the {@link Task} or null, if not already finished
	 */
	TaskResult getResult();
	
	/**
	 * gets any {@link Exception} thrown by the Task
	 *
	 * @return the {@link Exception} thrown or null
	 */
	Throwable getException();
	
	//await
	
	/**
	 * if an Exception occured, rethrow it. If not, return.
	 *
	 * @throws ExecutionException rethrow of Exception
	 */
	void rethrowException() throws ExecutionException, CancellationException;
	
	/**
	 * submits the task, waits for the execution to finish and rethrows any exceptions
	 *
	 * @throws ExecutionException   rethrown exceptions
	 * @throws InterruptedException if interrupted while waiting
	 */
	default void awaitAndRethrow() throws ExecutionException, CancellationException, InterruptedException {
		await();
		rethrowException();
	}
}
