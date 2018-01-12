package space.util.task.basic;

import space.util.concurrent.event.IEvent;
import space.util.task.ITaskExecutionCompleteRunnable;
import space.util.task.TaskResult;
import space.util.task.basic.runnable.AbstractRunnableTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static space.util.task.TaskResult.CANCELED;

/**
 * An abstract Implementation of {@link ITask}, providing basic functionality without Implementing how the execution, submittion, canceling and {@link Exception} handling works.
 * If you want that already implemented, refer to {@link AbstractRunnableTask}.
 */
public abstract class AbstractTask implements ITask {
	
	public List<Object> events;
	
	//state
	protected volatile boolean executionStarted;
	protected boolean hooksRan;
	
	//result
	protected volatile TaskResult result;
	
	/**
	 * starts the execution
	 *
	 * @return true if execution should be aborted, as it may already have been started
	 */
	protected synchronized boolean startExecution() {
		if (executionStarted)
			if (result == CANCELED)
				return true;
			else
				throw new IllegalStateException("already executed! " + this);
		executionStarted = true;
		return false;
	}
	
	@Override
	public synchronized boolean cancel(boolean mayInterrupt) {
		boolean allowSet = result == null;
		
		executionStarted = true;
		if (allowSet)
			result = CANCELED;
		cancel0(mayInterrupt);
		runHooks();
		return allowSet;
	}
	
	/**
	 * implementation of the interrupt of the execution
	 *
	 * @param mayInterrupt if it should interrupt
	 */
	protected abstract void cancel0(boolean mayInterrupt);
	
	//event
	@Override
	public synchronized void addHook(Runnable func) {
		addHook0(func);
	}
	
	@Override
	public void addHook(Consumer<? extends IEvent> func) {
		addHook0(func);
	}
	
	/**
	 * adds a Hook without any checks
	 *
	 * @param func the Hook
	 */
	public synchronized void addHook0(Object func) {
		if (hooksRan) {
			runHook(func);
			return;
		}
		
		//capacity of ArrayList: 1 - 3 - 9 - ...
		if (events == null)
			events = new ArrayList<>(1);
		events.add(func);
	}
	
	/**
	 * runs all the Hooks
	 */
	protected synchronized void runHooks() {
		if (hooksRan)
			return;
		hooksRan = true;
		
		notifyAll();
		if (events != null)
			for (Object run : events)
				runHook(run);
	}
	
	/**
	 * run one Hook, respecting the required state from {@link ITaskExecutionCompleteRunnable}
	 *
	 * @param run the Hook
	 */
	protected void runHook(Object run) {
		if (!(run instanceof ITaskExecutionCompleteRunnable) || result.insideMask(((ITaskExecutionCompleteRunnable) run).requiredResultState()))
			runEvent(run);
	}
	
	//await
	@Override
	public synchronized void await() throws InterruptedException {
		if (result == null)
			wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		if (result == null)
			wait(unit.toMillis(time));
	}
	
	//state
	@Override
	public boolean executionStarted() {
		return executionStarted;
	}
	
	@Override
	public boolean isDone() {
		return result != null;
	}
	
	//result
	@Override
	public TaskResult getResult() {
		return result;
	}
}
