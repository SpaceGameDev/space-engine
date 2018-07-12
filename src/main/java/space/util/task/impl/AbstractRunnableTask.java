package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.task.Task;
import space.util.task.TaskExceptionHandler;

import java.util.concurrent.Executor;

import static space.util.task.TaskResult.*;

/**
 * An {@link Task} Implementation which implements it as a {@link Runnable}.
 * It implements everything except a {@link AbstractRunnableTask#run0()} Method to implement for execution.
 * If you want to submit a Runnable directly, refer to {@link RunnableTask}.
 * If you do not want it as a {@link Runnable}, refer to {@link AbstractTask}.
 */
public abstract class AbstractRunnableTask extends AbstractTask implements Runnable {
	
	@Nullable
	protected TaskExceptionHandler exceptionHandler;
	@Nullable
	protected Thread executor;
	
	public AbstractRunnableTask() {
		this(null);
	}
	
	public AbstractRunnableTask(@Nullable TaskExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	//run
	@Override
	public void run() {
		synchronized (this) {
			if (startExecution())
				return;
			executor = Thread.currentThread();
		}
		
		boolean hasException = false;
		try {
			run0();
		} catch (Throwable e) {
			hasException = true;
			handleException(e);
		} finally {
			synchronized (this) {
				executor = null;
				if (result == null)
					result = hasException ? EXCEPTION : DONE;
				else if (result == CANCELED)
					//swallow the interrupt if Task was canceled, as it came from the cancellation
					//noinspection ResultOfMethodCallIgnored
					Thread.currentThread().isInterrupted();
				else
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("The Result was in an invalid state at end of execution ( valid states: null, CANCELED): " + this);
			}
			runHooks();
		}
	}
	
	/**
	 * Implementation method of any potentially unsafe code
	 */
	protected abstract void run0();
	
	/**
	 * Handles any thrown exception via the {@link #exceptionHandler}. May be overridden.
	 * Called by the Thread executing the {@link Task} and throwing the {@link Throwable}.
	 *
	 * @param e any {@link Throwable} occurred uncaught during execution
	 */
	protected void handleException(Throwable e) {
		(exceptionHandler != null ? exceptionHandler : Task.getDefaultExceptionHandler().apply(this)).uncaughtException(this, Thread.currentThread(), e);
	}
	
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
}
