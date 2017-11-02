package space.util.sync.awaitable;

import java.util.concurrent.TimeUnit;

public interface IAwaitable {
	
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
