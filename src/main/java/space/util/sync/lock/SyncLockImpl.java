package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class SyncLockImpl implements SyncLock {
	
	private boolean locked;
	private int modId;
	private @NotNull List<BooleanSupplier> notifyUnlock = new ArrayList<>();
	
	@Override
	public synchronized boolean tryLockNow() {
		if (locked)
			return false;
		
		//success
		locked = true;
		modId++;
		return true;
	}
	
	@Override
	public Runnable unlock() {
		final int modId;
		synchronized (this) {
			locked = false;
			modId = this.modId;
		}
		
		return () -> {
			synchronized (this) {
				if (locked || modId != this.modId)
					return;
				locked = true;
			}
			
			BooleanSupplier callback;
			while (true) {
				synchronized (this) {
					if (notifyUnlock.isEmpty()) {
						//no callback found to accept lock
						locked = false;
						return;
					}
					callback = notifyUnlock.remove(0);
				}
				
				if (callback.getAsBoolean()) {
					//accepted lock
					return;
				}
			}
		};
	}
	
	@Override
	public void tryLockLater(BooleanSupplier callback) {
		synchronized (this) {
			if (locked) {
				//locked -> add callback
				notifyUnlock.add(callback);
				return;
			}
			
			//not locked -> lock and call callback
			locked = true;
		}
		
		if (!callback.getAsBoolean()) {
			//not accepted lock
			unlock().run();
		}
	}
}
