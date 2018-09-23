package space.util.barrier;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A basic Implementation of {@link Barrier}. The {@link Barrier} is triggered by calling {@link #triggerNow()}.
 */
public class BarrierImpl implements Barrier {
	
	public List<Runnable> hookList;
	
	public BarrierImpl() {
		this(false);
	}
	
	public BarrierImpl(boolean initialTriggerState) {
		hookList = initialTriggerState ? null : new ArrayList<>(0);
	}
	
	//trigger
	public synchronized void triggerNow() {
		List<Runnable> hookList = this.hookList;
		this.hookList = null;
		
		this.notifyAll();
		this.hookList.forEach(Runnable::run);
	}
	
	//impl
	@Override
	public synchronized boolean isTriggered() {
		return hookList == null;
	}
	
	@Override
	public synchronized void addHook(@NotNull Runnable run) {
		if (hookList == null)
			run.run();
		else
			hookList.add(run);
	}
	
	@Override
	public synchronized void removeHook(@NotNull Runnable run) {
		if (hookList != null)
			hookList.remove(run);
	}
	
	@Override
	public synchronized void await() throws InterruptedException {
		while (hookList != null)
			this.wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		long sleepTime = unit.toNanos(time);
		long deadline = System.nanoTime() + sleepTime;
		
		while (hookList != null) {
			this.wait(sleepTime / 1000000, (int) (sleepTime % 1000000));
			sleepTime = deadline - System.nanoTime();
			if (sleepTime <= 0)
				return;
		}
	}
}
