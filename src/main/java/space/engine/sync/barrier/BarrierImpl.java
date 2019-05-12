package space.engine.sync.barrier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A basic Implementation of {@link Barrier}. The {@link Barrier} is triggered by calling {@link #triggerNow()}.
 */
public class BarrierImpl implements Barrier {
	
	public @Nullable List<Runnable> hookList;
	
	public BarrierImpl() {
		this(false);
	}
	
	public BarrierImpl(boolean initialTriggerState) {
		hookList = initialTriggerState ? null : new ArrayList<>(0);
	}
	
	//trigger
	public void triggerNow() {
		List<Runnable> hookList;
		synchronized (this) {
			if (this.hookList == null)
				throw new IllegalStateException("Barrier already triggered!");
			
			hookList = this.hookList;
			this.hookList = null;
			this.notifyAll();
		}
		
		hookList.forEach(Runnable::run);
	}
	
	//impl
	@Override
	public synchronized boolean isFinished() {
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
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException, TimeoutException {
		long sleepTime = unit.toNanos(time);
		long deadline = System.nanoTime() + sleepTime;
		
		while (hookList != null) {
			this.wait(sleepTime / 1000000, (int) (sleepTime % 1000000));
			sleepTime = deadline - System.nanoTime();
			if (sleepTime <= 0)
				throw new TimeoutException();
		}
	}
	
	@Override
	public synchronized String toString() {
		return hookList == null ? "finished" : "waiting";
	}
}
