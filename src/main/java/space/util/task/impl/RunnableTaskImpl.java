package space.util.task.impl;

import java.util.concurrent.Executor;

import static space.util.task.TaskState.*;

public abstract class RunnableTaskImpl extends AbstractTask implements Runnable {
	
	//static
	public static RunnableTaskImpl create(Executor exec, Runnable run) {
		return new RunnableTaskImpl() {
			
			@Override
			public void execute() {
				run.run();
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
		};
	}
	
	//submit
	@Override
	protected void submit0() {
		submit1(this);
	}
	
	protected abstract void submit1(Runnable toRun);
	
	/**
	 * <p>Whether the Task wants to be executed. Defaults to true.</p>
	 * Should be implemented if the Task can be canceled to reduce unnecessary overhead.
	 *
	 * @return true if the Task wants to be executed.
	 */
	public boolean shouldExecute() {
		return true;
	}
	
	//execution
	public void run() {
		synchronized (this) {
			if (state != SUBMITTED)
				throw new IllegalStateException("Can only start running in State " + SUBMITTED + ", was in State " + state);
			state = RUNNING;
		}
		
		try {
			if (shouldExecute())
				execute();
		} finally {
			synchronized (this) {
				if (state != RUNNING)
					//noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Can only end running in State " + RUNNING + ", was in State " + state);
				state = FINISHED;
				triggerNow();
			}
		}
	}
	
	protected abstract void execute();
}
