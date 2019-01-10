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
	public void unlock() {
		Runnable[] oldNotifyUnlock;
		synchronized (this) {
			if (!locked)
				throw new IllegalStateException();
			
			//success
			locked = false;
			oldNotifyUnlock = notifyUnlock.toArray(new Runnable[0]);
			notifyUnlock.clear();
		}
		
		for (Runnable run : oldNotifyUnlock) {
			run.run();
		}
	}
	
	@Override
	public void notifyUnlock(Runnable run) {
		synchronized (this) {
			if (locked) {
				notifyUnlock.add(run);
				return;
			}
		}
		
		run.run();
	}
}
