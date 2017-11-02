package space.util.sync.awaitable;

import space.util.baseobject.BaseObject;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;
import space.util.string.toStringHelperOld.objects.TSHObjects.TSHObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * if signal() is getting called X-times, X Threads will leave the await()-Method
 */
public class OneToOneSignalable implements BaseObject, ISignalable {
	
	static {
		BaseObject.initClass(OneToOneSignalable.class, OneToOneSignalable::new, d -> new OneToOneSignalable());
	}
	
	public int stack;
	
	@Override
	public synchronized void signal() {
		stack++;
		doNotify();
	}
	
	@Override
	public synchronized void signalAll() {
		stack = Integer.MAX_VALUE;
		doNotifyAll();
	}
	
	protected void doNotify() {
		notify();
	}
	
	protected void doNotifyAll() {
		notifyAll();
	}
	
	@Override
	public synchronized boolean isSignaled() {
		return stack > 0;
	}
	
	@Override
	public synchronized void await() throws InterruptedException {
		while (!isSignaled())
			wait();
		stack--;
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		while (!isSignaled())
			wait(unit.toMillis(time));
		stack--;
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("stack", this.stack);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
