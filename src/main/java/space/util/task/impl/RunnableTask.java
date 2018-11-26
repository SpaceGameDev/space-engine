package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.Global;
import space.util.sync.barrier.Barrier;
import space.util.sync.barrier.BarrierImpl;
import space.util.task.Task;
import space.util.task.TaskState;

import static space.util.task.TaskState.*;

public abstract class RunnableTask extends BarrierImpl implements Runnable, Task {
	
	protected volatile @NotNull TaskState state = CREATED;
	
	//submit
	@NotNull
	@Override
	public synchronized Task submit() {
		if (state != CREATED)
			throw new IllegalStateException("Can only submit() in State " + CREATED + ", was in State " + state);
		state = SUBMITTED;
		
		submit0();
		return this;
	}
	
	@NotNull
	@Override
	public synchronized Task submit(@NotNull Barrier... barriers) {
		if (state != CREATED)
			throw new IllegalStateException("Can only submit() in State " + CREATED + ", was in State " + state);
		state = AWAITING_EVENTS;
		
		Barrier.awaitAll(() -> {
			if (state != AWAITING_EVENTS)
				throw new IllegalStateException("Can only have Barrier callback in State " + AWAITING_EVENTS + ", was in State " + state);
			state = SUBMITTED;
			submit0();
		}, barriers);
		return this;
	}
	
	protected synchronized void submit0() {
		submit1(this);
	}
	
	protected synchronized void submit1(Runnable toRun) {
		Global.GLOBAL_EXECUTOR.execute(toRun);
	}
	
	/**
	 * <p>Whether the Task wants to be executed. Defaults to true.</p>
	 * Should be implemented if the Task can be canceled to reduce unnecessary overhead.
	 *
	 * @return true if the Task wants to be executed.
	 */
	protected boolean shouldExecute() {
		return true;
	}
	
	//execution
	public void run() {
		synchronized (this) {
			if (state != SUBMITTED)
				throw new IllegalStateException("Can only start running in State " + SUBMITTED + ", was in State " + state);
			state = RUNNING;
		}
		
		try {
			if (shouldExecute())
				execute();
		} finally {
			synchronized (this) {
				if (state != RUNNING)
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Can only end running in State " + RUNNING + ", was in State " + state);
				state = FINISHED;
				triggerNow();
			}
		}
	}
	
	protected abstract void execute();
	
	@NotNull
	@Override
	public TaskState getState() {
		return state;
	}
}
