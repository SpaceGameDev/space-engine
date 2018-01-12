package space.util.concurrent.event;

import space.util.concurrent.awaitable.BooleanSignalable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BooleanEvent extends BooleanSignalable implements IEventRunnable {
	
	public List<Object> after = new ArrayList<>();
	
	@Override
	public synchronized void addHook(Runnable func) {
		after.add(func);
	}
	
	@Override
	public synchronized void addHook(Consumer<? extends IEvent> func) {
		after.add(func);
	}
	
	@Override
	protected void doNotify() {
		super.doNotify();
		after.forEach(this::runEvent);
	}
}
