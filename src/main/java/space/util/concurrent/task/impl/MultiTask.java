package space.util.concurrent.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.TaskResult;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static space.util.concurrent.task.TaskResult.*;

/**
 * A {@link Task} which can have multiple sub-{@link Task ITasks}, doing Cancellation, Exception handling / forwarding and Result calculations.
 */
public class MultiTask extends AbstractTask {
	
	public Iterable<? extends Task> subTasks;
	
	//state
	@NotNull
	protected AtomicInteger callCnt;
	
	public MultiTask(Iterable<Task> subTasks) {
		this.subTasks = subTasks;
		
		int size = 0;
		for (Task task : subTasks) {
			task.addHook(this::callbackSubtaskDone);
			size++;
		}
		callCnt = new AtomicInteger(size);
	}
	
	@Override
	public synchronized void submit(@NotNull Executor executor) {
		if (startExecution())
			return;
		for (Task task : subTasks)
			task.submit(executor);
	}
	
	protected void callbackSubtaskDone(Task task) {
		TaskResult res = task.getResult();
		switch (res) {
			case DONE:
				break;
			case CANCELED:
				synchronized (this) {
					if (result == DONE)
						throw new IllegalStateException("MultiTask has result DONE, while a Task is still executing");
					//result == null, CANCELED, EXCEPTION
					result = CANCELED;
				}
				break;
			case EXCEPTION:
				synchronized (this) {
					if (result == DONE)
						throw new IllegalStateException("MultiTask has result DONE, while a Task is still executing");
					if (result == null /* || result == EXCEPTION*/)
						result = EXCEPTION;
					//if(result == CANCELED)
					//	do nothing
				}
				break;
			default:
				throw new IllegalStateException("Invalid result State " + res);
		}
		
		if (callCnt.decrementAndGet() == 0) {
			synchronized (this) {
				if (result == DONE)
					throw new IllegalStateException("MultiTask has result DONE, while a Task is still executing");
				if (result == null)
					result = DONE;
				//if(result == CANCELED || result == EXCEPTION)
				//	do nothing
				runHooks();
			}
		}
	}
	
	//cancel
	@Override
	public void cancel0(boolean mayInterrupt) {
		for (Task event : subTasks)
			event.cancel(mayInterrupt);
	}
}
