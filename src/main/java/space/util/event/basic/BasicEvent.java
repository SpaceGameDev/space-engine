package space.util.event.basic;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link BasicEvent} is something you can {@link BasicEvent#addHook(Object)} and {@link BasicEvent#removeHook(Object)} Hooks,
 * which called when the {@link BasicEvent} is triggered. <b>The way it will be triggered is not defined.</b>
 * It is meant for fast singlethreaded event triggering, if you want to calculate something use a Task on a ThreadPool launched by this to do so.
 * It is expected that the Method {@link BasicEvent#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface BasicEvent<FUNCTION> {
	
	/**
	 * adds a Hook.
	 *
	 * @param hook the Hook to add
	 */
	void addHook(@NotNull FUNCTION hook);
	
	/**
	 * Removes a Hook.<br>
	 * It is expected that the Method will be called very rarely and mustn't be optimized via hashing or other techniques.
	 *
	 * @param hook the Hook to be removed
	 */
	boolean removeHook(@NotNull FUNCTION hook);
}
