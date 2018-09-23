package space.util.task.impl;

import org.jetbrains.annotations.Nullable;
import space.util.task.TaskResult;

import java.util.concurrent.Executor;

import static space.util.task.TaskResult.*;
import static space.util.task.TaskState.*;

public abstract class RunnableTaskImpl extends AbstractTask implements Runnable {
	
	public static RunnableTaskImpl create(Executor exec, Runnable run) {
		return new RunnableTaskImpl() {
			
			@Override
			public void execute() {
				run.run();
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
		};
	}
	
	protected @Nullable Thread executor;
	protected volatile @Nullable TaskResult result;
	
	//change state
	@Override
	protected void submit0() {
		submit1(this);
	}
	
	protected abstract void submit1(Runnable toRun);
	
	//run
	public void run() {
		synchronized (this) {
			if (state != SUBMITTED)
				throw new IllegalStateException("Can only start running in State " + SUBMITTED + ", was in State " + state);
			state = RUNNING;
			executor = Thread.currentThread();
		}
		
		boolean hasException = false;
		try {
			execute();
		} catch (Throwable e) {
			hasException = true;
			throw e;
		} finally {
			synchronized (this) {
				if (state != RUNNING)
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Can only end running in State " + RUNNING + ", was in State " + state);
				state = FINISHED;
				executor = null;
				
				if (result == null)
					result = hasException ? EXCEPTION : SUCCESSFUL;
				else if (result == CANCELED)
					//swallow the interrupt if Task was canceled, as it came from the cancellation
					//noinspection ResultOfMethodCallIgnored
					Thread.currentThread().isInterrupted();
				else
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("The Result was in an invalid state at end of execution ( valid states: null, CANCELED): " + this);
				triggerNow();
			}
		}
	}
	
	protected abstract void execute();
	
	@Override
	public synchronized boolean cancel(boolean mayInterrupt) {
		if (result == TaskResult.CANCELED)
			return false;
		
		boolean allowSet = result == null;
		
		if (allowSet)
			result = TaskResult.CANCELED;
		if (mayInterrupt && executor != null)
			executor.interrupt();
		triggerNow();
		return allowSet;
	}
	
	@Override
	public @Nullable TaskResult getResult() {
		return result;
	}
}
