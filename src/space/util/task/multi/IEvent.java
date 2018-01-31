package space.util.task.multi;

import space.util.concurrent.awaitable.IAwaitable;

/**
 * An {@link IEvent} is something you can {@link IAwaitable#await()} on like with an {@link IAwaitable},
 * but you can also use {@link IEvent#addHook(Object)} and {@link IEvent#removeHook(Object)} to add and remove Hooks,
 * called when the {@link IEvent} is triggered. The way it will be triggered is not defined.
 * {@link IEvent#isSignaled()} will also always return false, as {@link IEvent IEvents} should be usable multiple times.
 * It is expected that the Method {@link IEvent#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface IEvent<FUNCTION> extends IAwaitable {
	
	/**
	 * adds a Hook.
	 *
	 * @param hook the Hook to add
	 */
	void addHook(FUNCTION hook);
	
	/**
	 * Removes a Hook.<br>
	 * It is expected that the Method will be called very rarely and mustn't be optimized via hashing or other techniques.
	 *
	 * @param hook the Hook to be removed
	 */
	boolean removeHook(FUNCTION hook);
	
	/**
	 * will <b>USUALLY</b> return <code>false</code>, as {@link IEvent IEvents} may be triggered multiple times, and you should always wait for the "next" time.
	 * This is NOT mandatory, implementations may choose to override this and return some state (eg. implementations only triggering once).
	 */
	@Override
	default boolean isSignaled() {
		return false;
	}
}
