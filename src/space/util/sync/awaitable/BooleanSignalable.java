package space.util.sync.awaitable;

import space.util.baseobject.BaseObject;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;
import space.util.string.toStringHelperOld.objects.TSHObjects.TSHObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * as soon as signal is being called, it will notify
 */
public class BooleanSignalable implements BaseObject, IResetable {
	
	static {
		BaseObject.initClass(BooleanSignalable.class, BooleanSignalable::new);
	}
	
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
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("signaled", this.signaled);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
