package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;

/**
 * A Lock based upon {@link SyncLock}.
 */
public interface SyncLock {
	
	/**
	 * Creates a Ticket for this SyncObject. Supply a Runnable to be called when is {@link SyncLock} becomes available.
	 * It is expected that the notify {@link Runnable} is only used once on each {@link SyncLock}. This allows the Runnable to be reused as the Ticket.
	 *
	 * @param notify the notify hook as a {@link Runnable}
	 * @return A Ticket for sync use
	 */
	@NotNull Object createTicket(@NotNull Runnable notify);
	
	/**
	 * Removes a previously added notify hook.
	 *
	 * @param ticket a previously returned ticket
	 */
	void removeTicket(@NotNull Object ticket);
	
	/**
	 * Called before a {@link space.util.task.Task} will execute with this {@link SyncLock}.
	 * A Call to this Method AND it returning true will always result in a later call to {@link #syncEnd(Object)}.
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
	boolean syncStart(@NotNull Object ticket);
	
	/**
	 * Called when the synchronization should end. Will always be called after {@link #syncStart(Object)} and it returning true.
	 */
	void syncEnd(@NotNull Object ticket);
	
	/**
	 * Will {@link #syncEnd(Object)} and {@link #removeTicket(Object)} in one function.
	 *
	 * @param ticket the ticket
	 */
	default void syncEndAndRemove(@NotNull Object ticket) {
		syncEnd(ticket);
		removeTicket(ticket);
	}
}
