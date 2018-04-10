package space.util.concurrent.awaitable;

/**
 * An {@link Awaitable} can be reset (eg. can reset it's signal status to false), it should implement this class.
 */
public interface Resetable extends Signalable {
	
	/**
	 * resets an {@link Awaitable}. The signal status <b>may be</b> false afterwards (implementation specific)
	 */
	void reset();
	
	/**
	 * resets an {@link Awaitable}. The signal status <b>is</b> false afterwards (implementation specific)
	 *
	 * @throws UnsupportedOperationException if unsupported
	 */
	default void resetAll() {
		throw new UnsupportedOperationException();
	}
}
