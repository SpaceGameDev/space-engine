package space.util.concurrent.task.creator;

import org.jetbrains.annotations.NotNull;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.typehandler.TypeHandler;

import java.util.concurrent.Executor;

/**
 * An interface having {@link TaskCreator#create(TypeHandler)} functions for creating {@link Task ITasks}.
 */
@FunctionalInterface
public interface TaskCreator<FUNCTION> {
	
	@NotNull Task create(@NotNull TypeHandler<FUNCTION> handler);
	
	default @NotNull Task execute(@NotNull TypeHandler<FUNCTION> handler) {
		return execute(Runnable::run, handler);
	}
	
	default @NotNull Task execute(@NotNull Executor executor, @NotNull TypeHandler<FUNCTION> handler) {
		Task task = create(handler);
		task.submit(executor);
		return task;
	}
}
