package space.util.sync.awaitable;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * The {@link LockLikeSignalable} allows you to "lock" with the signal()-Method and "unlock" with the reset()-Method.
 * Only when the amount of "locks" is zero, it is signaled and Threads in await()-Methods return
 */
public class LockLikeSignalable implements ToString, IResetable {
	
	public int holderCnt;
	
	@Override
	public synchronized void signal() {
		holderCnt++;
	}
	
	@Override
	public synchronized void reset() {
		holderCnt--;
		doNotify();
	}
	
	protected void doNotify() {
		notifyAll();
	}
	
	@Override
	public synchronized boolean isSignaled() {
		return holderCnt <= 0;
	}
	
	@Override
	public synchronized void await() throws InterruptedException {
		while (!isSignaled())
			wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		while (!isSignaled())
			wait(unit.toMillis(time));
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("holderCnt", this.holderCnt);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
