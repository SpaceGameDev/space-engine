package space.util.concurrent.event;

/**
 * An {@link IEvent} is something you can {@link IEvent#addHook(Object)} and {@link IEvent#removeHook(Object)} Hooks,
 * which called when the {@link IEvent} is triggered. <b>The way it will be triggered is not defined.</b>
 * It is meant for fast singlethreaded event triggering, if you need Multithreaded Execution
 * It is expected that the Method {@link IEvent#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface IEvent<FUNCTION> {
	
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
}
