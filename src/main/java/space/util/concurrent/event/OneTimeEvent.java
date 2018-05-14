package space.util.concurrent.event;

import space.util.concurrent.awaitable.Awaitable;
import space.util.concurrent.task.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OneTimeEvent<T> implements RunnableEvent<T>, Awaitable {
	
	public List<T> after = new ArrayList<>();
	public TypeHandler<T> type;
	
	//hook
	@Override
	public synchronized void addHook(T func) {
		if (type != null) // -> isSignaled() == true && after == null
			type.accept(func);
		else
			after.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(T hook) {
		return after != null && after.remove(hook);
	}
	
	//run
	@Override
	public synchronized void run(TypeHandler<T> type) {
		this.type = type;
		notifyAll();
		after.forEach(type);
		after = null;
	}
	
	@Override
	public boolean isSignaled() {
		return type != null;
	}
	
	//await
	@Override
	public synchronized void await() throws InterruptedException {
		if (type != null)
			wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		if (type != null)
			wait(unit.toMillis(time));
	}
}
