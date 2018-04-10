package space.util.concurrent.awaitable;

public interface Signalable extends Awaitable, Runnable {
	
	/**
	 * notifies Threads waiting. exact usage implementation specific
	 */
	void signal();
	
	/**
	 * notifies all Threads waiting
	 *
	 * @throws UnsupportedOperationException if unsupported
	 */
	default void signalAll() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * this executes the signal()-Method
	 */
	@Override
	default void run() {
		signal();
	}
}
