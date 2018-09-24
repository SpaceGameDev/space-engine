package space.util.barrier;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An Object which can be {@link #await() awaited} upon. You can also {@link #addHook(Runnable) add a Hook} to be called when the Barrier {@link #isFinished() is finished}. <br>
 * <b>It cannot be triggered more than once or reset</b>.
 */
public interface Barrier {
	
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
	 */
	void await() throws InterruptedException;
	
	/**
	 * waits until event is triggered with a timeout
	 */
	void await(long time, TimeUnit unit) throws InterruptedException;
	
	
	//static
	
	/**
	 * Awaits for all {@link Barrier Barriers} to be triggered, then executes toRun. This Operation is non-blocking.
	 *
	 * @param toRun    something to be executed when all {@link Barrier Barriers} are triggered
	 * @param barriers the {@link Barrier Barriers} to await upon
	 */
	static void awaitAll(@NotNull Runnable toRun, @NotNull Barrier... barriers) {
		final AtomicInteger cnt = new AtomicInteger(barriers.length);
		for (Barrier barrier : barriers) {
			barrier.addHook(() -> {
				if (cnt.decrementAndGet() == 0)
					toRun.run();
			});
		}
	}
}
