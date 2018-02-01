package space.util.concurrent.task.creator;

import space.util.concurrent.task.ITask;
import space.util.concurrent.task.typehandler.ITypeHandler;

import java.util.concurrent.Executor;

/**
 * An interface having {@link ITaskCreator#create(ITypeHandler)} functions for creating {@link ITask ITasks}.
 */
@FunctionalInterface
public interface ITaskCreator<FUNCTION> {
	
	ITask create(ITypeHandler<FUNCTION> handler);
	
	default ITask execute(ITypeHandler<FUNCTION> handler) {
		return execute(Runnable::run, handler);
	}
	
	default ITask execute(Executor executor, ITypeHandler<FUNCTION> handler) {
		ITask task = create(handler);
		task.submit(executor);
		return task;
	}
}
