package space.util.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.event.typehandler.TypeHandler;
import space.util.task.Task;
import space.util.task.TaskExceptionHandler;

import java.util.concurrent.Executor;

/**
 * An interface having {@link EventCreator#create(TypeHandler, TaskExceptionHandler)} functions for creating {@link Task ITasks}.
 * Any {@link TaskExceptionHandler} can be <code>null</code>, in that case use the default {@link TaskExceptionHandler} from {@link Task#getDefaultExceptionHandler()}
 */
@FunctionalInterface
public interface EventCreator<FUNCTION> {
	
	//create
	default @NotNull Task create(@NotNull TypeHandler<FUNCTION> handler) {
		return create(handler, null);
	}
	
	@NotNull Task create(@NotNull TypeHandler<FUNCTION> handler, @Nullable TaskExceptionHandler exceptionHandler);
	
	//execute
	default @NotNull Task execute(@NotNull TypeHandler<FUNCTION> handler) {
		return execute(handler, null, Runnable::run);
	}
	
	default @NotNull Task execute(@NotNull TypeHandler<FUNCTION> handler, @Nullable TaskExceptionHandler exceptionHandler) {
		return execute(handler, exceptionHandler, Runnable::run);
	}
	
	default Task execute(@NotNull TypeHandler<FUNCTION> handler, @NotNull Executor executor) {
		return execute(handler, null, executor);
	}
	
	default Task execute(@NotNull TypeHandler<FUNCTION> handler, @Nullable TaskExceptionHandler exceptionHandler, @NotNull Executor executor) {
		Task task = create(handler, exceptionHandler);
		task.submit(executor);
		return task;
	}
}
