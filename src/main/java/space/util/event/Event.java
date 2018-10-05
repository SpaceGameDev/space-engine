package space.util.event;

import org.jetbrains.annotations.NotNull;

/**
 * The {@link Event} Object is used to {@link Event#addHook(Object)} and {@link Event#removeHook(Object)} Hooks,
 * which are called when the {@link Event} is triggered. <b>The way it will be triggered is not defined.</b>
 *
 * @implNote It is expected that the Method {@link Event#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface Event<FUNCTION> {
	
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
