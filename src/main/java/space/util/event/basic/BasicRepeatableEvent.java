package space.util.event.basic;

import org.jetbrains.annotations.NotNull;
import space.util.event.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple BasicEvent running hooks every time {@link BasicRepeatableEvent#run(TypeHandler)} is called.
 */
public class BasicRepeatableEvent<FUNCTION> implements BasicRunnableEvent<FUNCTION> {
	
	public List<FUNCTION> after = new ArrayList<>();
	
	//hook
	@Override
	public synchronized void addHook(@NotNull FUNCTION func) {
		after.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(@NotNull FUNCTION hook) {
		return after.remove(hook);
	}
	
	//run
	@Override
	public synchronized void run(@NotNull TypeHandler<FUNCTION> type) {
		notifyAll();
		after.forEach(type);
	}
	
	@Override
	public boolean isSignaled() {
		return false;
	}
	
	@Override
	public synchronized void await() throws InterruptedException {
		wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		wait(unit.toMillis(time));
	}
}
