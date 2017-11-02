package space.util.sync.task.basic;

import space.util.sync.task.TaskResult;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static space.util.sync.task.TaskResult.*;

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
			task.addHook((o) -> {
				if (o instanceof ITask)
					call(((ITask) o));
				else
					throw new IllegalArgumentException();
			});
			size++;
		}
		callCnt = new AtomicInteger(size);
	}
	
	@Override
	public synchronized void submit(Executor executor) {
		if (!startExecution())
			return;
		for (ITask task : subTasks)
			task.submit(executor);
	}
	
	public void call(ITask task) {
		TaskResult res = task.getResult();
		if (res != DONE) {
			synchronized (this) {
				if (result == DONE)
					throw new IllegalStateException("MultiTask has result DONE, while a Task is still executing");
				
				if (res == CANCELED)
					result = CANCELED;
				else if (res == EXCEPTION) {
					if (result == null)
						result = EXCEPTION;
					addException(task.getException());
				} else
					throw new IllegalStateException("Invalid result State " + res);
			}
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
	
	public static class CollectiveExecutionException extends Exception {
		
		public CollectiveExecutionException() {
			this(null, null);
		}
		
		public CollectiveExecutionException(String message) {
			this(message, null);
		}
		
		public CollectiveExecutionException(Throwable cause) {
			this(null, cause);
		}
		
		public CollectiveExecutionException(String message, Throwable cause) {
			super(message, cause, true, false);
		}
	}
}
