package space.util.awaitable;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.concurrent.TimeUnit;

/**
 * if {@link BooleanSignalable#signal()} is called, it will turn into signaled state and stay there
 */
public class BooleanSignalable implements ToString, Resetable {
	
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
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("signaled", this.signaled);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
