package space.util.concurrent.awaitable;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * The {@link LockLikeSignalable} allows you to "lock" with the {@link LockLikeSignalable#signal()} and "unlock" with the {@link LockLikeSignalable#reset()}.
 * Only when the amount of "locks" turns zero, it is signaled
 */
public class LockLikeSignalable implements ToString, Resetable {
	
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
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("holderCnt", this.holderCnt);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
