package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.Global;
import space.util.sync.barrier.Barrier;
import space.util.sync.lock.SyncLock;

import static space.util.task.impl.AbstractTask.TaskState.*;

public abstract class RunnableTask extends AbstractTask implements Runnable {
	
	public RunnableTask(@NotNull Barrier... barriers) {
		super(barriers);
	}
	
	public RunnableTask(@NotNull SyncLock[] locks, @NotNull Barrier... barriers) {
		super(locks, barriers);
	}
	
	protected synchronized void submit0() {
		submit1(this);
	}
	
	protected synchronized void submit1(Runnable toRun) {
		Global.GLOBAL_EXECUTOR.execute(toRun);
	}
	
	//execution
	public void run() {
		synchronized (this) {
			if (state != SUBMITTED)
				throw new IllegalStateException("Can only start running in State " + SUBMITTED + ", was in State " + state);
			state = RUNNING;
		}
		
		try {
			execute();
		} finally {
			executionFinished();
		}
	}
	
	protected abstract void execute();
}
