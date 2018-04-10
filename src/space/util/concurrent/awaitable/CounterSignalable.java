package space.util.concurrent.awaitable;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * counts how many {@link CounterSignalable#signal()}-calls have been made and if it was called often enough, it will notify
 */
public class CounterSignalable implements ToString, Signalable {
	
	public int cnt;
	
	public CounterSignalable() {
	}
	
	public CounterSignalable(int cnt) {
		this.cnt = cnt;
	}
	
	@Override
	public synchronized void signal() {
		if (--cnt == 0)
			doNotify();
	}
	
	@Override
	public synchronized void signalAll() {
		cnt = 0;
		doNotify();
	}
	
	protected void doNotify() {
		notifyAll();
	}
	
	@Override
	public synchronized boolean isSignaled() {
		return cnt == 0;
	}
	
	@Override
	public synchronized void await() throws InterruptedException {
		if (cnt != 0)
			wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		if (cnt != 0)
			wait(unit.toMillis(time));
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("cnt", this.cnt);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
