package space.util.concurrent.event;

import space.util.concurrent.awaitable.BooleanSignalable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BooleanEvent extends BooleanSignalable implements IEventRunnable {
	
	public List<Object> after = new ArrayList<>();
	
	@Override
	public synchronized void addHook(Runnable func) {
		if (after != null)
			after.add(func);
		else
			func.run();
	}
	
	@Override
	public synchronized void addHook(Consumer<IEvent> func) {
		if (after != null)
			after.add(func);
		else
			func.accept(this);
	}
	
	@Override
	protected synchronized void doNotify() {
		super.doNotify();
		after.forEach(this::runEvent);
		after = null;
	}
}
