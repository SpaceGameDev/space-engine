package space.util.awaitable;

import java.util.concurrent.TimeUnit;

public interface Awaitable {
	
	/**
	 * returns true if this {@link Awaitable} is already triggered.
	 * This returning true also indicates that the {@link Awaitable#await()} Methods will return instantly.
	 */
	boolean isSignaled();
	
	/**
	 * waits until event is triggered
	 */
	void await() throws InterruptedException;
	
	/**
	 * waits until event is triggered with a timeout
	 */
	void await(long time, TimeUnit unit) throws InterruptedException;
}
