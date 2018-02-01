package space.util.concurrent.task.impl;

import space.util.concurrent.task.CollectiveExecutionException;
import space.util.concurrent.task.ITask;
import space.util.concurrent.task.TaskResult;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static space.util.concurrent.task.TaskResult.*;

/**
 * A {@link ITask} which can have multiple sub-{@link ITask ITasks}, doing Cancellation, Exception handling / forwarding and Result calculations.
 */
public class MultiTask extends AbstractTask {
	
	public Iterable<? extends ITask> subTasks;
	
	//state
	protected AtomicInteger callCnt;
	
	//result
	protected CollectiveExecutionException exception;
	
	public MultiTask() {
	}
	
	public MultiTask(Iterable<ITask> subTasks) {
		init(subTasks);
	}
	
	public void init(Iterable<? extends ITask> subTasks) {
		this.subTasks = subTasks;
		
		int size = 0;
		for (ITask task : subTasks) {
			task.addHook(this::call);
			size++;
		}
		callCnt = new AtomicInteger(size);
	}
	
	@Override
	public synchronized void submit(Executor executor) {
		if (startExecution())
			return;
		for (ITask task : subTasks)
			task.submit(executor);
	}
	
	public void call(ITask task) {
		TaskResult res = task.getResult();
		switch (res) {
			case DONE:
				break;
			case CANCELED:
				synchronized (this) {
					if (result == DONE)
						throw new IllegalStateException("MultiTask has result DONE, while a Task is still executing");
					result = CANCELED;
				}
				break;
			case EXCEPTION:
				synchronized (this) {
					if (result == DONE)
						throw new IllegalStateException("MultiTask has result DONE, while a Task is still executing");
					
					if (result.mask < EXCEPTION.mask)
						result = EXCEPTION;
					addException(task.getException());
				}
				break;
			default:
				throw new IllegalStateException("Invalid result State " + res);
		}
		
		if (callCnt.decrementAndGet() == 0) {
			synchronized (this) {
				if (result == null)
					result = DONE;
				runHooks();
			}
		}
	}
	
	public void addException(Throwable throwable) {
		if (exception == null)
			exception = new CollectiveExecutionException();
		exception.addSuppressed(throwable);
	}
	
	//cancel
	@Override
	public void cancel0(boolean mayInterrupt) {
		for (ITask event : subTasks)
			event.cancel(mayInterrupt);
	}
	
	//state
	@Override
	public Throwable getException() {
		return exception;
	}
	
	@Override
	public void rethrowException() throws ExecutionException, CancellationException {
		if (exception != null)
			throw new ExecutionException(exception);
	}
}
