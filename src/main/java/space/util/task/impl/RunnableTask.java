package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.task.Task;

/**
 * A full implementation of {@link Task} accepting a {@link Runnable}
 */
public class RunnableTask extends AbstractRunnableTask {
	
	@NotNull
	public Runnable run;
	
	public RunnableTask(@NotNull Runnable run) {
		this.run = run;
	}
	
	@Override
	protected void run0() {
		run.run();
	}
}
