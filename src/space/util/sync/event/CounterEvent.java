package space.util.sync.event;

import space.util.sync.awaitable.CounterSignalable;

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
		after.add(func);
	}
	
	@Override
	public void addHook(Consumer<?> func) {
		after.add(func);
	}
	
	@Override
	protected void doNotify() {
		super.doNotify();
		after.forEach(this::runEvent);
	}
}
