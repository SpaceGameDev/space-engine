package space.util.event.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.event.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasicOneTimeEvent<T> implements BasicRunnableEvent<T> {
	
	@Nullable
	public List<T> after = new ArrayList<>();
	@Nullable
	public TypeHandler<T> type;
	
	//hook
	@Override
	public synchronized void addHook(@NotNull T func) {
		if (type != null) // -> isSignaled() == true && after == null
			type.accept(func);
		else
			//noinspection ConstantConditions
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
