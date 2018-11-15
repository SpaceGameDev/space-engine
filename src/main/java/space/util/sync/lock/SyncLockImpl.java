package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.lock.keylock.BlockingKeyLock;
import space.util.lock.keylock.BlockingKeyLockImpl;
import space.util.sync.InvalidTicketException;

import java.util.HashSet;
import java.util.Set;

public class SyncLockImpl implements SyncLock {
	
	/**
	 * only non-blocking methods are used
	 */
	private BlockingKeyLock<Object> lock = new BlockingKeyLockImpl<>();
	private Set<Runnable> tickets = new HashSet<>();
	
	@NotNull
	@Override
	public Object createTicket(@Nullable Runnable ticket) {
		if (ticket == null)
			return new Object();
		
		synchronized (this) {
			tickets.add(ticket);
		}
		return ticket;
	}
	
	@Override
	public void removeTicket(@Nullable Object ticket) {
		if (ticket instanceof Runnable) {
			boolean success;
			synchronized (this) {
				success = tickets.remove(ticket);
			}
			if (!success)
				throw new InvalidTicketException(ticket);
		}
	}
	
	@Override
	public synchronized boolean syncStart(@NotNull Object ticket) {
		return lock.tryLock(ticket);
	}
	
	@Override
	public synchronized void syncEnd(@NotNull Object ticket) {
		lock.unlock(ticket);
		tickets.forEach(Runnable::run);
	}
}
