package space.util.task.creator;

import space.util.task.basic.ITask;
import space.util.task.basic.runnable.AbstractRunnableTask;

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
