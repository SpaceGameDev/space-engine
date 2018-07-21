package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.task.Task;
import space.util.task.TaskCreator;
import space.util.task.TaskExceptionHandler;

public class RunnableTaskCreator implements TaskCreator {
	
	public final Runnable run;
	
	public RunnableTaskCreator(Runnable run) {
		this.run = run;
	}
	
	@Override
	public @NotNull Task create(@Nullable TaskExceptionHandler exceptionHandler) {
		return new RunnableTask(run, exceptionHandler);
	}
}
