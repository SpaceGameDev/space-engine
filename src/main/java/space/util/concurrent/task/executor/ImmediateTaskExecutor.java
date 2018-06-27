package space.util.concurrent.task.executor;

import org.jetbrains.annotations.NotNull;
import space.util.concurrent.task.Task;

/**
 * This executes the Task immediately on the Thread calling it.<br>
 * Made to have a Reference-Implementation for testing.
 */
public class ImmediateTaskExecutor implements TaskExecutor {
	
	@Override
	public void executeTask(@NotNull Task task) {
		task.submit(Runnable::run);
	}
}
