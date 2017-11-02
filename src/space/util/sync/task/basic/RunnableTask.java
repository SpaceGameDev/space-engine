package space.util.sync.task.basic;

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
