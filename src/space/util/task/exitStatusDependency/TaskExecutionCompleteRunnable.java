package space.util.task.exitStatusDependency;

import space.util.task.ITask;

/**
 * allows for submitting a {@link Runnable} while also having a requiring a State of preceding {@link ITask ITasks}
 */
public class TaskExecutionCompleteRunnable implements ITaskExecutionCompleteRunnable {
	
	public int requiredResultState;
	public Runnable run;
	
	public TaskExecutionCompleteRunnable() {
	}
	
	public TaskExecutionCompleteRunnable(int requiredResultState, Runnable run) {
		this.requiredResultState = requiredResultState;
		this.run = run;
	}
	
	@Override
	public int requiredResultState() {
		return requiredResultState;
	}
	
	@Override
	public void run() {
		run.run();
	}
}
