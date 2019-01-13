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
			BooleanSupplier callback;
			while (true) {
				//lock and get callback
				synchronized (this) {
					if (locked || modId != this.modId)
						return;
					if (notifyUnlock.isEmpty()) {
						//no callback found to accept lock
						return;
					}
					callback = notifyUnlock.remove(0);
					locked = true;
				}
				
				//call callback
				if (callback.getAsBoolean()) {
					//accepted lock
					return;
				}
				
				//unlock
				synchronized (this) {
					locked = false;
				}
				
				//possible to better prevent lifelocks though performance advantage negligible; only at extreme transaction counts (> 100,000)
//				Thread.yield();
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
