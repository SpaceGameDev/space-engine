package space.util.concurrent.event;

import space.util.concurrent.awaitable.CounterSignalable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CounterEvent extends CounterSignalable implements IEventRunnable {
	
	public List<Object> after = new ArrayList<>();
	
	public CounterEvent(int cnt) {
		super(cnt);
	}
	
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
