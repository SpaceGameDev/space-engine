package space.util.concurrent.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.concurrent.task.Task;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import static space.util.concurrent.task.TaskResult.*;

/**
 * An {@link Task} Implementation which implements it as a {@link Runnable}.
 * It implements everything except a {@link AbstractRunnableTask#run0()} Method to implement for execution.
 * If you want to submit a Runnable directly, refer to {@link RunnableTask}.
 * If you do not want it as a {@link Runnable}, refer to {@link AbstractTask}.
 */
public abstract class AbstractRunnableTask extends AbstractTask implements Runnable {
	
	@Nullable
	protected Thread executor;
	@Nullable
	protected Throwable exception;
	
	//run
	@Override
	public void run() {
		synchronized (this) {
			if (startExecution())
				return;
			executor = Thread.currentThread();
		}
		
		Throwable e = null;
		try {
			run0();
		} catch (Throwable e2) {
			e = e2;
		} finally {
			synchronized (this) {
				executor = null;
				exception = e;
				if (result == null)
					result = e != null ? EXCEPTION : DONE;
				else if (result == CANCELED)
					//swallow the interrupt if Task was canceled, as it came from the cancellation
					//noinspection ResultOfMethodCallIgnored
					Thread.currentThread().isInterrupted();
				else
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("The Result was in an invalid state ( valid states: null, CANCELED): " + this, e);
			}
			runHooks();
		}
	}
	
	/**
	 * implementation method of any potentially unsafe code
	 */
	protected abstract void run0();
	
	//cancel
	@Override
	protected void cancel0(boolean mayInterrupt) {
		if (mayInterrupt && executor != null)
			executor.interrupt();
	}
	
	@Override
	public void submit(@NotNull Executor executor) {
		executor.execute(this);
	}
	
	@Nullable
	@Override
	public Throwable getException() {
		return exception;
	}
	
	@Override
	public void rethrowException() throws ExecutionException, CancellationException {
		if (result == EXCEPTION)
			throw new ExecutionException(exception);
	}
}
