package space.util.sync.awaitable;

import space.util.baseobject.BaseObject;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * counts how many signal()-calls have been made and if it was called often enough, it will notify
 */
public class CounterSignalable implements BaseObject, ISignalable {
	
	static {
		BaseObject.initClass(CounterSignalable.class, CounterSignalable::new, d -> new CounterSignalable(d.cnt));
	}
	
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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("cnt", this.cnt);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
