package space.util.concurrent.event;

import org.jetbrains.annotations.NotNull;
import space.util.concurrent.task.typehandler.TypeHandler;

/**
 * An {@link RunnableEvent} is something you can {@link Event#addHook(Object)} and {@link Event#removeHook(Object)} Hooks,
 * which called when the {@link Event} is triggered. <b>It is triggered by the {@link RunnableEvent#run(TypeHandler)} method in contrast to {@link Event}.</b>
 * It is expected that the Method {@link Event#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface RunnableEvent<FUNCTION> extends Event<FUNCTION> {
	
	void run(@NotNull TypeHandler<FUNCTION> type);
}
