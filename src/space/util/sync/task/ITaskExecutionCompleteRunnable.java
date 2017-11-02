package space.util.sync.task;

public interface ITaskExecutionCompleteRunnable extends Runnable {
	
	int requiredResultState();
}
