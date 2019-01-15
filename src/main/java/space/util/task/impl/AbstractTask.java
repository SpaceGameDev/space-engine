package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;
import space.util.sync.barrier.BarrierImpl;
import space.util.sync.lock.SyncLock;

import java.util.Arrays;
import java.util.Comparator;

import static space.util.task.impl.AbstractTask.TaskState.*;

/**
 * AbstractTask implements {@link Barrier} waiting and {@link SyncLock} locking.
 * Implement the {@link #submit()} Method in order to start executing and call {@link #executionFinished()} when you are done doing so.
 * If you DON'T require any initialization before execution use the {@link #AbstractTask(SyncLock[], Barrier[])} Constructor
 * but also delegate to the {@link #AbstractTask(SyncLock[])} while keeping it protected.
 * However if some initialization is required before the Task is executed, call the {@link #AbstractTask(SyncLock[])} Constructor, initialize what is needed
 * and call {@link #init(Barrier[])} afterwards. Failing to call {@link #init(Barrier[])} will cause this Task to never execute.
 */
public abstract class AbstractTask extends BarrierImpl {
	
	protected volatile @NotNull TaskState state = CREATED;
	protected final SyncLock[] locks;
	
	public AbstractTask(@NotNull SyncLock[] locks, @NotNull Barrier[] barriers) {
		this(locks);
		init(barriers);
	}
	
	/**
	 * REQUIRES calling {@link #init(Barrier[])} later to start execution
	 */
	protected AbstractTask(SyncLock[] originalLocks) {
		SyncLock[] locks = originalLocks.clone();
		Arrays.sort(locks, Comparator.comparingInt(Object::hashCode));
		this.locks = locks;
	}
	
	/**
	 * Starts the Execution of this Task
	 *
	 * @param barriers the Barriers to await upon
	 */
	protected void init(@NotNull Barrier[] barriers) {
		if (state != CREATED)
			throw new IllegalStateException("Can only init() in State " + CREATED + ", was in State " + state);
		
		if (barriers.length == 0) {
			state = ACQUIRING_LOCKS;
			locksTryAcquire();
		} else {
			state = AWAITING_BARRIERS;
			
			Barrier.awaitAll(() -> {
				synchronized (AbstractTask.this) {
					if (state != AWAITING_BARRIERS)
						throw new IllegalStateException("Can only have Barrier callback in State " + AWAITING_BARRIERS + ", was in State " + state);
					state = ACQUIRING_LOCKS;
					locksTryAcquire();
				}
			}, barriers);
		}
	}
	
	protected void locksTryAcquire() {
		locksTryAcquire(-1);
	}
	
	//locks
	protected boolean locksTryAcquire(int exceptLock) {
		int i;
		synchronized (this) {
			if (state != ACQUIRING_LOCKS)
				throw new IllegalStateException("Can only try to acquire locks in State " + ACQUIRING_LOCKS + ", was in State " + state);
			
			boolean success = true;
			for (i = 0; i < locks.length; i++) {
				if (i != exceptLock && !locks[i].tryLockNow()) {
					success = false;
					break;
				}
			}
			
			if (success) {
				state = EXECUTING;
				submit();
				return true;
			}
		}
		
		//failure
		final int fi = i;
		locksUnlock(fi, exceptLock);
		locks[fi].tryLockLater(() -> locksTryAcquire(fi));
		return false;
	}
	
	/**
	 * DON'T synchronize when calling this method!
	 */
	protected void locksUnlock() {
		locksUnlock(locks.length, -1);
	}
	
	/**
	 * DON'T synchronize when calling this method!
	 */
	protected void locksUnlock(int maxExclusive) {
		locksUnlock(maxExclusive, -1);
	}
	
	/**
	 * DON'T synchronize when calling this method!
	 */
	protected void locksUnlock(int maxExclusive, int exceptLock) {
		if (maxExclusive == 0)
			return;
		
		Runnable[] notifyCallback = new Runnable[maxExclusive];
		for (int i = maxExclusive - 1; i >= 0; i--)
			if (i != exceptLock)
				notifyCallback[i] = locks[i].unlock();
		for (int i = maxExclusive - 1; i >= 0; i--)
			if (notifyCallback[i] != null)
				notifyCallback[i].run();
	}
	
	//execution
	
	/**
	 * Submit your tasks here. Must call {@link #executionFinished()} after it is done executing
	 */
	protected abstract void submit();
	
	//end
	protected void executionFinished() {
		synchronized (this) {
			if (state != EXECUTING)
				throw new IllegalStateException("Can only finish execution in State " + EXECUTING + ", was in State " + state);
			state = TaskState.FINISHED;
		}
		
		locksUnlock();
		triggerNow();
	}
	
	public enum TaskState {
		
		CREATED,
		AWAITING_BARRIERS,
		ACQUIRING_LOCKS,
		EXECUTING,
		FINISHED
		
	}
}
