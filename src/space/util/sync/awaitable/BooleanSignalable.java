package space.util.sync.awaitable;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * as soon as signal is being called, it will notify
 */
public class BooleanSignalable implements ToString, IResetable {
	
	public boolean signaled;
	
	@Override
	public synchronized void signal() {
		signaled = true;
		doNotify();
	}
	
	protected void doNotify() {
		notifyAll();
	}
	
	@Override
	public synchronized void reset() {
		signaled = false;
	}
	
	@Override
	public synchronized boolean isSignaled() {
		return signaled;
	}
	
	@Override
	public synchronized void await() throws InterruptedException {
		if (!signaled)
			wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		if (!signaled)
			wait(unit.toMillis(time));
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("signaled", this.signaled);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
