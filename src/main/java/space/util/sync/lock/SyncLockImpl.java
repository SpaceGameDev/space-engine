package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.lock.keylock.BlockingKeyLock;
import space.util.lock.keylock.BlockingKeyLockImpl;
import space.util.sync.InvalidTicketException;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SyncLockImpl implements SyncLock {
	
	/**
	 * only non-blocking methods are used
	 */
	private BlockingKeyLock<Object> lock = new BlockingKeyLockImpl<>();
	private Set<Runnable> tickets = Collections.newSetFromMap(new ConcurrentHashMap<>());
	
	@NotNull
	@Override
	public Object createTicket(@NotNull Runnable ticket) {
		if (!tickets.add(ticket))
			throw new InvalidTicketException(ticket);
		return ticket;
	}
	
	@Override
	public void removeTicket(@Nullable Object ticket) {
		if (!(ticket instanceof Runnable && tickets.remove(ticket)))
			throw new InvalidTicketException(ticket);
	}
	
	@Override
	public boolean syncStart(@NotNull Object ticket) {
		return lock.tryLock(ticket);
	}
	
	@Override
	public void syncEnd(@NotNull Object ticket) {
		lock.unlock(ticket);
		tickets.forEach(Runnable::run);
	}
}
