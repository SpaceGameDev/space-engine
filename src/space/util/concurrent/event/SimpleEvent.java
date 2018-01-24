package space.util.concurrent.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * A simple Event not storing any state and notifies every time signal is called
 */
public class SimpleEvent implements IEventRunnable {
	
	public List<Object> after = new ArrayList<>();
	
	@Override
	public synchronized void addHook(Runnable func) {
		after.add(func);
	}
	
	@Override
	public synchronized void addHook(Consumer<IEvent> func) {
		after.add(func);
	}
	
	@Override
	public synchronized void signal() {
		notifyAll();
		after.forEach(this::runEvent);
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
