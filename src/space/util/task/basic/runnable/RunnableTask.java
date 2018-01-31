package space.util.task.basic.runnable;

import space.util.task.ITask;

/**
 * A full implementation of {@link ITask} accepting a {@link Runnable}
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
