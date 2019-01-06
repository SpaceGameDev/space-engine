package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;
import space.util.sync.barrier.BarrierImpl;
import space.util.sync.lock.SyncLock;

import static space.util.task.impl.AbstractTask.TaskState.*;

public abstract class AbstractTask extends BarrierImpl {
	
	protected volatile @NotNull TaskState state;
	protected SyncLock[] locks;
	
	public AbstractTask(@NotNull Barrier... barriers) {
		this(SyncLock.EMPTY_SYNCLOCK_ARRAY, barriers);
	}
	
	public AbstractTask(@NotNull SyncLock[] locks, @NotNull Barrier... barriers) {
		this.locks = locks;
		
		if (barriers.length == 0) {
			state = SUBMITTED;
			locksTryAcquire();
		} else {
			state = AWAITING_EVENTS;
			
			Barrier.awaitAll(() -> {
				synchronized (AbstractTask.this) {
					if (state != AWAITING_EVENTS)
						throw new IllegalStateException("Can only have Barrier callback in State " + AWAITING_EVENTS + ", was in State " + state);
					state = SUBMITTED;
					locksTryAcquire();
				}
			}, barriers);
		}
	}
	
	//locks
	protected synchronized void locksTryAcquire() {
		int i = 0;
		for (; i < locks.length; i++) {
			if (!locks[i].tryLock()) {
				locksUnlock(i - 1);
				locks[i].notifyUnlock(this::locksTryAcquire);
			}
		}
		submit0();
	}
	
	protected synchronized void locksUnlock() {
		locksUnlock(locks.length);
	}
	
	protected synchronized void locksUnlock(int from) {
		for (int i = from - 1; i >= 0; i--)
			locks[i].unlock();
	}
	
	//execution
	
	/**
	 * Submit your tasks here. Must call {@link #executionFinished()} after it is done executing
	 */
	protected abstract void submit0();
	
	//end
	protected synchronized void executionFinished() {
		if (!(state == SUBMITTED || state == RUNNING))
			throw new IllegalStateException("Can only finish excecution in State " + SUBMITTED + " or " + RUNNING + ", was in State " + state);
		state = TaskState.FINISHED;
		
		locksUnlock();
		triggerNow();
	}
	
	public enum TaskState {
		
		CREATED,
		AWAITING_EVENTS,
		SUBMITTED,
		RUNNING,
		FINISHED
		
	}
}
