package space.util.task.impl;

import space.util.Global;

import static space.util.task.TaskState.*;

public abstract class RunnableTask extends AbstractTask implements Runnable {
	
	protected synchronized void submit0() {
		submit1(this);
	}
	
	protected synchronized void submit1(Runnable toRun) {
		Global.GLOBAL_EXECUTOR.execute(toRun);
	}
	
	//execution
	public void run() {
		synchronized (this) {
			if (state != SUBMITTED)
				throw new IllegalStateException("Can only start running in State " + SUBMITTED + ", was in State " + state);
			state = RUNNING;
		}
		
		try {
			execute();
		} finally {
			executionFinished();
		}
	}
	
	protected abstract void execute();
}
