package space.util.event;

import org.jetbrains.annotations.NotNull;
import space.util.event.typehandler.TypeHandler;
import space.util.task.Task;

import java.util.concurrent.Executor;

/**
 * An interface having {@link TaskEvent#create(TypeHandler)} functions for creating {@link Task ITasks}.
 */
@FunctionalInterface
public interface TaskEvent<FUNCTION> {
	
	@NotNull Task create(@NotNull TypeHandler<FUNCTION> handler);
	
	default @NotNull Task execute(@NotNull TypeHandler<FUNCTION> handler) {
		return execute(handler, Runnable::run);
	}
	
	default Task execute(@NotNull TypeHandler<FUNCTION> handler, @NotNull Executor executor) {
		Task task = create(handler);
		task.submit(executor);
		return task;
	}
}
