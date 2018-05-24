package space.util.concurrent.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.concurrent.awaitable.Awaitable;
import space.util.concurrent.task.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OneTimeEvent<T> implements RunnableEvent<T>, Awaitable {
	
	@Nullable
	public List<T> after = new ArrayList<>();
	public TypeHandler<T> type;
	
	//hook
	@Override
	public synchronized void addHook(@NotNull T func) {
		if (type != null) // -> isSignaled() == true && after == null
			type.accept(func);
		else
			after.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(@NotNull T hook) {
		return after != null && after.remove(hook);
	}
	
	//run
	@Override
	public synchronized void run(@NotNull TypeHandler<T> type) {
		if (after == null)
			throw new IllegalStateException("run already called!");
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
	public synchronized void await(long time, @NotNull TimeUnit unit) throws InterruptedException {
		if (type != null)
			wait(unit.toMillis(time));
	}
}
