package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;
import space.util.sync.barrier.BarrierImpl;
import space.util.sync.lock.SyncLock;
import space.util.task.Task;
import space.util.task.TaskState;

import static space.util.task.TaskState.*;

public abstract class AbstractTask extends BarrierImpl implements Task {
	
	protected volatile @NotNull TaskState state = CREATED;
	protected SyncLock[] locks;
	
	//submit
	@Override
	public synchronized @NotNull Task submit(@NotNull SyncLock[] locks, @NotNull Barrier... barriers) {
		if (state != CREATED)
			throw new IllegalStateException("Can only submit() in State " + CREATED + ", was in State " + state);
		this.locks = locks;
		
		if (barriers.length == 0) {
			state = SUBMITTED;
			locksAcquire();
		} else {
			state = AWAITING_EVENTS;
			
			Barrier.awaitAll(() -> {
				synchronized (AbstractTask.this) {
					if (state != AWAITING_EVENTS)
						throw new IllegalStateException("Can only have Barrier callback in State " + AWAITING_EVENTS + ", was in State " + state);
					state = SUBMITTED;
					locksAcquire();
				}
			}, barriers);
		}
		return this;
	}
	
	//locks
	protected synchronized void locksAcquire() {
		int i = 0;
		for (; i < locks.length; i++) {
			if (!locks[i].tryLock()) {
				locksUnlock(i - 1);
				locks[i].notifyUnlock(this::locksAcquire);
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
	 * submit your tasks here. Must call {@link #executionFinished()} after it is done executing
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
	
	//getter
	@NotNull
	@Override
	public TaskState getState() {
		return state;
	}
}
