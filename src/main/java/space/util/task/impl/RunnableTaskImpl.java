package space.util.task.impl;

import org.jetbrains.annotations.Nullable;
import space.util.task.TaskResult;
import space.util.task.TaskState;

import java.util.Objects;
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
	
	/**
	 * NotNull if state == {@link TaskState#RUNNING}
	 */
	protected @Nullable Thread executor;
	
	/**
	 * NotNull if state == {@link TaskState#FINISHED}
	 */
	protected volatile @Nullable TaskResult result;
	
	protected boolean canceledEarly = false;
	
	//change state
	@Override
	protected void submit0() {
		submit1(this);
	}
	
	protected abstract void submit1(Runnable toRun);
	
	//run
	public void run() {
		synchronized (this) {
			if (state == FINISHED && (result == CANCELED || result == CRASH))
				return;
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
				executor = null;
				
				if (state == RUNNING) {
					state = FINISHED;
					result = hasException ? CRASH : SUCCESSFUL;
				} else if (state == FINISHED) {
					if (result != CANCELED && result != CRASH) {
						//noinspection ThrowFromFinallyBlock
						throw new IllegalStateException("Can only end running in State " + RUNNING + " when Result is " + CANCELED + " or " + CRASH + ", Result was " + result);
					}
					//swallow the interrupt if Task was canceled, as it came from the cancellation
					//noinspection ResultOfMethodCallIgnored
					Thread.interrupted();
				} else {
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Can only end running in State " + RUNNING + " or " + FINISHED + ", was in State " + state);
				}
				triggerNow();
			}
		}
	}
	
	protected abstract void execute();
	
	@Override
	public synchronized boolean cancel(boolean mayInterrupt) {
		if (state == FINISHED)
			return canceledEarly;
		
		canceledEarly = (state != RUNNING);
		state = FINISHED;
		result = CANCELED;
		
		if (canceledEarly) {
			triggerNow();
			return canceledEarly;
		} else {
			if (mayInterrupt)
				Objects.requireNonNull(executor).interrupt();
			//triggerNow() is called by executing Thread
			return canceledEarly;
		}
	}
	
	@Override
	public @Nullable TaskResult getResult() {
		return result;
	}
}
