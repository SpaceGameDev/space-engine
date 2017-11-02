package space.util.sync.task;

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
