package space.util.concurrent.awaitable;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * if {@link OneToOneSignalable#signal()} is getting called X-times, X Threads will leave the await()-Method
 */
public class OneToOneSignalable implements ToString, ISignalable {
	
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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("stack", this.stack);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
