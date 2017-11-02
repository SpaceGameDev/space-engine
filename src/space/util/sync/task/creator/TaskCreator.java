package space.util.sync.task.creator;

import space.util.sync.task.basic.AbstractRunnableTask;
import space.util.sync.task.basic.ITask;

public class TaskCreator implements ITaskCreator {
	
	public Runnable run;
	
	public TaskCreator() {
	}
	
	public TaskCreator(Runnable run) {
		this.run = run;
	}
	
	@Override
	public ITask create() {
		return new AbstractRunnableTask() {
			@Override
			protected void run0() {
				run.run();
			}
		};
	}
}
