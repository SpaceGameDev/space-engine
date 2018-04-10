package space.util.concurrent.task.impl;

import space.util.concurrent.task.Task;

/**
 * A full implementation of {@link Task} accepting a {@link Runnable}
 */
public class RunnableTask extends AbstractRunnableTask {
	
	public Runnable run;
	
	public RunnableTask(Runnable run) {
		this.run = run;
	}
	
	@Override
	protected void run0() {
		run.run();
	}
}
