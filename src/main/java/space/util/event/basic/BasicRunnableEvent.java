package space.util.event.basic;

import org.jetbrains.annotations.NotNull;
import space.util.awaitable.Awaitable;
import space.util.event.typehandler.TypeHandler;

/**
 * An {@link BasicRunnableEvent} is something you can {@link BasicEvent#addHook(Object)} and {@link BasicEvent#removeHook(Object)} Hooks,
 * which called when the {@link BasicEvent} is triggered. <b>It is triggered by the {@link BasicRunnableEvent#run(TypeHandler)} method in contrast to {@link BasicEvent}.</b>
 * It is expected that the Method {@link BasicEvent#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface BasicRunnableEvent<FUNCTION> extends BasicEvent<FUNCTION>, Awaitable {
	
	void run(@NotNull TypeHandler<FUNCTION> type);
}
