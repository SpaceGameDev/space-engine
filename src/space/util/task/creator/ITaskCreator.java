package space.util.task.creator;

import space.util.task.basic.ITask;

import java.util.concurrent.Executor;

/**
 * created a new {@link ITask}
 */
@FunctionalInterface
public interface ITaskCreator {
	
	ITask create();
	
	default ITask execute() {
		return execute(Runnable::run);
	}
	
	default ITask execute(Executor executor) {
		ITask task = create();
		task.submit(executor);
		return task;
	}
}
