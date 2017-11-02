package space.util.sync.awaitable;

/**
 * An {@link IAwaitable} can be reset (eg. can reset it's signal status to false), it should implement this class.
 */
public interface IResetable extends ISignalable {
	
	/**
	 * resets an {@link IAwaitable}. The signal status <b>may be</b> false afterwards (implementation specific)
	 */
	void reset();
	
	/**
	 * resets an {@link IAwaitable}. The signal status <b>is</b> false afterwards (implementation specific)
	 *
	 * @throws UnsupportedOperationException if unsupported
	 */
	default void resetAll() {
		throw new UnsupportedOperationException();
	}
}
