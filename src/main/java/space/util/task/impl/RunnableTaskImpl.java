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
	 * <ul>
	 * <li>NotNull if state == {@link TaskState#RUNNING}</li>
	 * </ul>
	 */
	protected @Nullable Thread executor;
	
	/**
	 * <ul>
	 * <li>NotNull if state == {@link TaskState#FINISHED}</li>
	 * <li>Null or {@link TaskResult#CANCELED} (after {@link #cancel(boolean)}) otherwise</li>
	 * </ul>
	 */
	protected volatile @Nullable TaskResult result;
	
	/**
	 * true if {@link #cancel(boolean)} was called before this got executed ({@link #state} == {@link TaskState#RUNNING} | {@link TaskState#FINISHED})
	 */
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
					//finish this Task normally
					result = hasException ? CRASH : SUCCESSFUL;
					state = FINISHED;
					triggerNow();
				} else if (state == FINISHED) {
					if (result != CANCELED && result != CRASH) {
						//noinspection ThrowFromFinallyBlock
						throw new IllegalStateException("Can only end running in State " + RUNNING + " when Result is " + CANCELED + " or " + CRASH + ", Result was " + result);
					}
					//swallow the interrupt if Task was canceled, as it came from the cancellation
					//noinspection ResultOfMethodCallIgnored
					Thread.interrupted();
					
					//finish this Task after a cancel while still being executed / entering state == RUNNING
					state = FINISHED;
					triggerNow();
				} else {
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Can only end running in State " + RUNNING + " or " + FINISHED + ", was in State " + state);
				}
			}
		}
	}
	
	protected abstract void execute();
	
	@Override
	public synchronized boolean cancel(boolean mayInterrupt) {
		if (result == CANCELED)
			return canceledEarly;
		
		canceledEarly = (state != RUNNING);
		result = CANCELED;
		
		if (canceledEarly) {
			//we finish this Task
			state = FINISHED;
			triggerNow();
			return canceledEarly;
		} else {
			//the Thread currently executing finishes this Task
			if (mayInterrupt)
				Objects.requireNonNull(executor).interrupt();
			return canceledEarly;
		}
	}
	
	@Override
	public @Nullable TaskResult getResult() {
		return state == TaskState.FINISHED ? result : null;
	}
}
