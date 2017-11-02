package space.util.sync.event;

import space.util.sync.awaitable.IAwaitable;

import java.util.function.Consumer;

/**
 * An event accepting runnables. The order the Runnables are executed is undefined, they are NOT sorted.
 */
public interface IEvent extends IAwaitable {
	
	void addHook(Runnable func);
	
	void addHook(Consumer<?> func);
	
	/**
	 * runs a specific Event.
	 * if it is a {@link Runnable}, it will run() it.
	 * if it is a {@link Consumer}, it will accept(this) it. Make sure that the {@link Consumer} is of type IEvent.
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
