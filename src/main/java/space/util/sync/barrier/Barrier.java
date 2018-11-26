package space.util.sync.barrier;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An Object which can be {@link #await() awaited} upon. You can also {@link #addHook(Runnable) add a Hook} to be called when the Barrier {@link #isFinished() is finished}. <br>
 * <b>It cannot be triggered more than once or reset</b>.
 */
public interface Barrier {
	
	Barrier ALWAYS_TRIGGERED_BARRIER = new Barrier() {
		@Override
		public boolean isFinished() {
			return true;
		}
		
		@Override
		public void addHook(@NotNull Runnable run) {
			run.run();
		}
		
		@Override
		public void removeHook(@NotNull Runnable run) {
		
		}
		
		@Override
		public void await() {
		
		}
		
		@Override
		public void await(long time, TimeUnit unit) {
		
		}
	};
	Barrier[] EMPTY_BARRIER_ARRAY = new Barrier[0];
	
	//getter
	
	/**
	 * Gets the state if this {@link Barrier}, whether it is finished or not.<br>
	 * NOTE: The triggered state should be considered immediately stale, as it can change any Moment without notice.
	 *
	 * @return whether the {@link Barrier} is triggered or not
	 */
	boolean isFinished();
	
	//hooks
	
	/**
	 * Adds a hook to be called when the Event triggers
	 *
	 * @param run the hook as a {@link Runnable}
	 */
	void addHook(@NotNull Runnable run);
	
	/**
	 * Removes a previously added hook. This Method is not expected to be called very often.
	 *
	 * @param run a previously added hook to remove
	 * @implNote The removal algorithm can be slow, as it is not expected to be called very often.
	 */
	void removeHook(@NotNull Runnable run);
	
	//await
	
	/**
	 * waits until event is triggered
	 *
	 * @throws InterruptedException if an interrupt occurs
	 */
	void await() throws InterruptedException;
	
	/**
	 * waits until event is triggered with a timeout
	 *
	 * @throws InterruptedException if an interrupt occurs
	 * @throws TimeoutException     thrown if waiting takes longer than the specified timeout
	 */
	void await(long time, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	//static
	
	/**
	 * Awaits for all {@link Barrier Barriers} to be triggered, then triggers the returned {@link Barrier}. This Operation is non-blocking.
	 * If no Barriers are given, the Barrier is returned triggered.
	 *
	 * @param barriers the {@link Barrier Barriers} to await upon
	 * @return A {@link Barrier} which is triggered when all supplied {@link Barrier Barriers} have.
	 */
	static Barrier awaitAll(@NotNull Collection<? extends Barrier> barriers) {
		return awaitAll(barriers.toArray(new Barrier[0]));
	}
	
	/**
	 * Awaits for all {@link Barrier Barriers} to be triggered, then triggeres the returned {@link Barrier}. This Operation is non-blocking.
	 * If no Barriers are given, the Barrier is returned triggered.
	 *
	 * @param barriers the {@link Barrier Barriers} to await upon
	 * @return A {@link Barrier} which is triggered when all supplied {@link Barrier Barriers} have.
	 */
	static Barrier awaitAll(@NotNull Barrier... barriers) {
		if (barriers.length == 0)
			return ALWAYS_TRIGGERED_BARRIER;
		
		BarrierImpl ret = new BarrierImpl();
		awaitAll(ret::triggerNow, barriers);
		return ret;
	}
	
	/**
	 * Awaits for all {@link Barrier Barriers} to be triggered, then executes toRun. This Operation is non-blocking.
	 * If no Barriers are given, the Runnable is executed immediately.
	 *
	 * @param toRun    something to be executed when all {@link Barrier Barriers} are triggered
	 * @param barriers the {@link Barrier Barriers} to await upon
	 */
	static void awaitAll(@NotNull Runnable toRun, @NotNull Collection<@NotNull ? extends Barrier> barriers) {
		awaitAll(toRun, barriers.toArray(new Barrier[0]));
	}
	
	/**
	 * Awaits for all {@link Barrier Barriers} to be triggered, then executes toRun. This Operation is non-blocking.
	 * If no Barriers are given, the Runnable is executed immediately.
	 *
	 * @param toRun    something to be executed when all {@link Barrier Barriers} are triggered
	 * @param barriers the {@link Barrier Barriers} to await upon
	 */
	static void awaitAll(@NotNull Runnable toRun, @NotNull Barrier... barriers) {
		if (barriers.length == 0) {
			toRun.run();
			return;
		}
		
		final AtomicInteger cnt = new AtomicInteger(barriers.length);
		for (Barrier barrier : barriers) {
			barrier.addHook(() -> {
				if (cnt.decrementAndGet() == 0)
					toRun.run();
			});
		}
	}
}
