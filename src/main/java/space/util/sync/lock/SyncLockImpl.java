package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SyncLockImpl implements SyncLock {
	
	private boolean locked;
	private @NotNull List<Runnable> notifyUnlock = new ArrayList<>();
	
	@Override
	public synchronized boolean tryLock() {
		if (locked)
			return false;
		
		//success
		locked = true;
		return true;
	}
	
	@Override
	public synchronized void unlock() {
		if (!locked)
			throw new IllegalStateException();
		
		//success
		locked = false;
		List<Runnable> oldNotifyUnlock = this.notifyUnlock;
		this.notifyUnlock = new ArrayList<>();
		oldNotifyUnlock.forEach(Runnable::run);
	}
	
	@Override
	public synchronized void notifyUnlock(Runnable run) {
		if (locked)
			notifyUnlock.add(run);
		else
			run.run();
	}
}
