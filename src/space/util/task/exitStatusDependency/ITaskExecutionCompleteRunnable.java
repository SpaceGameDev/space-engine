package space.util.task.exitStatusDependency;

public interface ITaskExecutionCompleteRunnable extends Runnable {
	
	int requiredResultState();
}
