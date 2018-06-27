package space.util.concurrent.task.executor;

import org.jetbrains.annotations.NotNull;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.impl.RunnableTask;

public interface TaskExecutor {
	
	void executeTask(@NotNull Task task);
	
	@NotNull
	default Task execute(@NotNull Runnable command) {
		RunnableTask task = new RunnableTask(command);
		executeTask(task);
		return task;
	}
}
