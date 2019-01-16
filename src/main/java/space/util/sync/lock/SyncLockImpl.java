package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;
import space.util.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class SyncLockImpl implements SyncLock {
	
	public static int SYNCLOCK_CALLBACK_TRIES = 2;
	
	private boolean locked;
	private int modId;
	private @NotNull List<BooleanSupplier> notifyUnlock = new ArrayList<>();
	
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public SyncLockImpl() {
		//calls hashCode() to generate identity hashcode and disable 'Biased locking' on Hotspot.
		//https://srvaroa.github.io/jvm/java/openjdk/biased-locking/2017/01/30/hashCode.html
		hashCode();
	}
	
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
		
		return () -> unlockRunCallbackOnce(modId);
	}
	
	private void unlockRunCallbackTry(final int modid) {
		for (int i = 0; i < SYNCLOCK_CALLBACK_TRIES; i++) {
			if (unlockRunCallbackOnce(modId))
				return;
			
			//possible to better prevent lifelocks though performance advantage negligible; only at extreme transaction counts (> 100,000)
//			Thread.yield();
		}
		
		//out of tries -> enqueue and try again later
		Global.GLOBAL_EXECUTOR.execute(() -> unlockRunCallbackTry(modid));
	}
	
	private boolean unlockRunCallbackOnce(int modId) {
		BooleanSupplier callback;
		
		//lock and get callback
		synchronized (this) {
			if (locked || modId != this.modId)
				return true;
			if (notifyUnlock.isEmpty()) {
				//no callback found to accept lock
				return true;
			}
			callback = notifyUnlock.remove(0);
			locked = true;
		}
		
		//call callback
		if (callback.getAsBoolean()) {
			//accepted lock
			return true;
		}
		
		//unlock
		synchronized (this) {
			locked = false;
		}
		return false;
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
