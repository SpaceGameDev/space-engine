package space.util.task.basic.runnable;

/**
 * A full implementation of {@link space.util.task.basic.ITask} accepting a {@link Runnable}
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
