package space.util.task;

import space.util.task.impl.RunnableTask;

import java.util.concurrent.Executor;

public class Tasks {
	
	private Tasks() {
	}
	
	public static TaskCreator<? extends RunnableTask> create(Runnable run) {
		return (locks, barriers) -> new RunnableTask(locks, barriers) {
			@Override
			public void execute() {
				run.run();
			}
		};
	}
	
	public static TaskCreator<? extends RunnableTask> create(Executor exec, Runnable run) {
		return (locks, barriers) -> new RunnableTask(locks, barriers) {
			@Override
			protected synchronized void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
			
			@Override
			protected void execute() {
				run.run();
			}
		};
	}
}
