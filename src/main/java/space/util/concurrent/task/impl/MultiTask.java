package space.util.concurrent.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.concurrent.task.CollectiveExecutionException;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.TaskResult;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static space.util.concurrent.task.TaskResult.*;

/**
 * A {@link Task} which can have multiple sub-{@link Task ITasks}, doing Cancellation, Exception handling / forwarding and Result calculations.
 */
public class MultiTask extends AbstractTask {
	
	public Iterable<? extends Task> subTasks;
	
	//state
	//initialized with init()
	@SuppressWarnings("NullableProblems")
	@NotNull
	protected AtomicInteger callCnt;
	
	//result
	@Nullable
	protected CollectiveExecutionException exception;
	
	public MultiTask() {
	}
	
	public MultiTask(Iterable<Task> subTasks) {
		init(subTasks);
	}
	
	public void init(Iterable<? extends Task> subTasks) {
		this.subTasks = subTasks;
		
		int size = 0;
		for (Task task : subTasks) {
			task.addHook(this::callbackTaskDone);
			size++;
		}
		callCnt = new AtomicInteger(size);
	}
	
	@Override
	public synchronized void submit(@NotNull Executor executor) {
		if (startExecution())
			return;
		for (Task task : subTasks)
			task.submit(executor);
	}
	
	protected void callbackTaskDone(Task task) {
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
					
					if (result == null)
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
	
	protected void addException(@Nullable Throwable throwable) {
		if (throwable == null)
			return;
		if (exception == null)
			exception = new CollectiveExecutionException();
		exception.addSuppressed(throwable);
	}
	
	//cancel
	@Override
	public void cancel0(boolean mayInterrupt) {
		for (Task event : subTasks)
			event.cancel(mayInterrupt);
	}
	
	//state
	@Nullable
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
