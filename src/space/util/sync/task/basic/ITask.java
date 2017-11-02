package space.util.sync.task.basic;

import space.util.sync.event.IEvent;
import space.util.sync.task.TaskResult;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public interface ITask extends IEvent {
	
	//run
	
	/**
	 * submit task(s) to the executor. An implementation can choose to submit multiple {@link Runnable}s.
	 *
	 * @param executor to submit any tasks to
	 */
	void submit(Executor executor);
	
	//cancel
	boolean cancel(boolean mayInterrupt);
	
	//getState
	boolean executionStarted();
	
	boolean isDone();
	
	@Override
	default boolean isSignaled() {
		return isDone();
	}
	
	//result
	TaskResult getResult();
	
	Throwable getException();
	
	//throw
	
	/**
	 * if an Exception occured, rethrow it. If not, return.
	 *
	 * @throws ExecutionException rethrow of Exception
	 */
	void rethrowException() throws ExecutionException, CancellationException;
	
	/**
	 * Use for submitting a task which has to complete normally
	 *
	 * @throws ExecutionException   rethrows any exceptions
	 * @throws InterruptedException if interrupted while waiting
	 */
	default void awaitAndRethrow() throws ExecutionException, CancellationException, InterruptedException {
		await();
		rethrowException();
	}
	
	//await
	@Override
	void await() throws InterruptedException;
	
	@Override
	void await(long time, TimeUnit unit) throws InterruptedException;
}
