package space.util.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

/**
 * An interface having {@link TaskCreator#create(TaskExceptionHandler)} functions for creating {@link Task ITasks}.
 * Any {@link TaskExceptionHandler} can be <code>null</code>, in that case use the default {@link TaskExceptionHandler} from {@link Task#getDefaultExceptionHandler()}
 */
@FunctionalInterface
public interface TaskCreator {
	
	//create
	default @NotNull Task create() {
		return create(null);
	}
	
	@NotNull Task create(@Nullable TaskExceptionHandler exceptionHandler);
	
	//execute
	default @NotNull Task execute() {
		return execute(null, Runnable::run);
	}
	
	default @NotNull Task execute(@Nullable TaskExceptionHandler exceptionHandler) {
		return execute(exceptionHandler, Runnable::run);
	}
	
	default Task execute(@NotNull Executor executor) {
		return execute(null, executor);
	}
	
	default Task execute(@Nullable TaskExceptionHandler exceptionHandler, @NotNull Executor executor) {
		Task task = create(exceptionHandler);
		task.submit(executor);
		return task;
	}
}
