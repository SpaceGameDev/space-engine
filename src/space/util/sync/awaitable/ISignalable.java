package space.util.sync.awaitable;

public interface ISignalable extends IAwaitable, Runnable {
	
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
