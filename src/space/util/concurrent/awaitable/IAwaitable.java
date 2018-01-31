package space.util.concurrent.awaitable;

import java.util.concurrent.TimeUnit;

public interface IAwaitable {
	
	/**
	 * returns true if this {@link IAwaitable} is already triggered.
	 * This returning true also indicates that the {@link IAwaitable#await()} Methods will return instantly.
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
