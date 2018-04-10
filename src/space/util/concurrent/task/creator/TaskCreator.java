package space.util.concurrent.task.creator;

import space.util.concurrent.task.Task;
import space.util.concurrent.task.typehandler.TypeHandler;

import java.util.concurrent.Executor;

/**
 * An interface having {@link TaskCreator#create(TypeHandler)} functions for creating {@link Task ITasks}.
 */
@FunctionalInterface
public interface TaskCreator<FUNCTION> {
	
	Task create(TypeHandler<FUNCTION> handler);
	
	default Task execute(TypeHandler<FUNCTION> handler) {
		return execute(Runnable::run, handler);
	}
	
	default Task execute(Executor executor, TypeHandler<FUNCTION> handler) {
		Task task = create(handler);
		task.submit(executor);
		return task;
	}
}
