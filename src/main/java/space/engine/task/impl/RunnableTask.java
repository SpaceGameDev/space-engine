package space.engine.task.impl;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.lock.SyncLock;

public abstract class RunnableTask extends AbstractTask implements Runnable {
	
	public RunnableTask(@NotNull SyncLock[] locks, @NotNull Barrier[] barriers) {
		super(locks, barriers);
	}
	
	/**
	 * REQUIRES calling {@link #init(Barrier[])} later to start execution
	 */
	protected RunnableTask(SyncLock[] locks) {
		super(locks);
	}
	
	protected synchronized void submit() {
		submit1(this);
	}
	
	protected abstract void submit1(Runnable toRun);
	
	//execution
	public void run() {
		try {
			execute();
		} finally {
			executionFinished();
		}
	}
	
	protected abstract void execute();
}
