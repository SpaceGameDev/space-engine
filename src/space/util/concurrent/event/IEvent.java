package space.util.concurrent.event;

import space.util.concurrent.awaitable.IAwaitable;

import java.util.function.Consumer;

/**
 * An event accepting runnables. The order the Runnables are executed is undefined, they are NOT sorted.
 */
@Deprecated
public interface IEvent extends IAwaitable {
	
	/**
	 * adds a hook which is called when the {@link IEvent} is triggered
	 *
	 * @param func the Hook as a {@link Runnable}
	 */
	void addHook(Runnable func);
	
	/**
	 * adds a hook which is called when the {@link IEvent} is triggered.
	 * The consumed Object is the {@link IEvent} itself,
	 * allowing for a hook to be submitted to multiple {@link IEvent IEvents}
	 * while still being able to distinguish between them.
	 *
	 * @param func the Hook as a {@link Consumer} of ? extends {@link IEvent}
	 */
	void addHook(Consumer<IEvent> func);
	
	/**
	 * runs a specific Hook.
	 * if it is a {@link Runnable}, it will run() it.
	 * if it is a {@link Consumer}, it will accept(this) it. It is not ensured that the {@link Consumer} is of type IEvent.
	 *
	 * @param obj the Object to "run"
	 */
	@SuppressWarnings("unchecked")
	default void runEvent(Object obj) {
		if (obj instanceof Runnable)
			((Runnable) obj).run();
		else if (obj instanceof Consumer<?>)
			((Consumer) obj).accept(this);
		else
			throw new IllegalArgumentException();
	}
}
