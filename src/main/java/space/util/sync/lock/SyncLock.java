package space.util.sync.lock;

import space.util.task.TaskCreator;

/**
 * A Lock based upon {@link SyncLock}.
 */
public interface SyncLock {
	
	SyncLock[] EMPTY_SYNCLOCK_ARRAY = new SyncLock[0];
	
	/**
	 * Called before a {@link TaskCreator} will execute with this {@link SyncLock}.
	 * A Call to this Method AND it returning true will always result in a later call to {@link #unlock()}.
	 * <p>
	 * Return value:
	 * <b>If this Object does not represent a Lock it should always return true.</b>
	 * The return value determines whether the calling Task is allowed execution at this moment in time.
	 * If this Object may return false, it should as soon as there is a possibility of this Method returning true notify all notify hooks.
	 * On the Example of a Lock: If the Lock is currently locked, this Method will return false.
	 * As soon as the Lock is unlocked, all notify hooks have to be called as this Method may return true.
	 *
	 * @return true if the Task is allowed to execute. See Description.
	 */
	boolean tryLock();
	
	/**
	 * Called when the synchronization should end. Will always be called after {@link #tryLock()} and it returning true.
	 */
	void unlock();
	
	void notifyUnlock(Runnable run);
}
