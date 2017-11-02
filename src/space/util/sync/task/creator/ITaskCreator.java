package space.util.sync.task.creator;

import space.util.sync.task.basic.ITask;

import java.util.concurrent.Executor;

public interface ITaskCreator {
	
	ITask create();
	
	default ITask execute() {
		return execute(Runnable::run);
	}
	
	default ITask execute(Executor executor) {
		ITask task = create();
		task.submit(executor);
		return task;
	}
}
