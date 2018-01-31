package space.util.task.basic;

import space.util.task.ITask;
import space.util.task.TaskResult;
import space.util.task.basic.runnable.AbstractRunnableTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static space.util.task.TaskResult.CANCELED;

/**
 * An abstract Implementation of {@link ITask}, providing basic functionality without Implementing how the execution, submitting, canceling (partially) and {@link Exception} handling works.
 * It should be noted that any implementation should NOT put the execution in a synchronized block, as it inhibits any interaction with the {@link AbstractTask},
 * and should instead synchronize the start and the end of the execution with result evaluation, NOT the execution itself.
 * If you want that already implemented, refer to {@link AbstractRunnableTask}.
 */
public abstract class AbstractTask implements ITask {
	
	public static final int INITIAL_EVENT_ARRAYLIST_CAPACITY = 1;
	
	/**
	 * will be created lazily when {@link AbstractTask#addHook(Consumer)} is called the first time
	 * capacity of ArrayList: 1 - 3 - 9 - ...
	 */
	public List<Consumer<ITask>> events;
	
	//these are volatile to allow getter of these two values to be non-synchronized
	//state
	protected volatile boolean executionStarted;
	protected boolean hooksRan;
	
	//result
	protected volatile TaskResult result;
	
	//state changing methods
	
	/**
	 * starts the execution
	 *
	 * @return true if execution should be aborted, as it may already have been canceled
	 */
	protected synchronized boolean startExecution() {
		if (executionStarted)
			if (result == CANCELED)
				return true;
			else
				throw new IllegalStateException("Already executed! " + this);
		executionStarted = true;
		return false;
	}
	
	@Override
	public synchronized boolean cancel(boolean mayInterrupt) {
		if (result == CANCELED)
			return false;
		
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
	public synchronized void addHook(Consumer<ITask> func) {
		if (hooksRan) {
			func.accept(this);
			return;
		}
		
		if (events == null)
			events = new ArrayList<>(INITIAL_EVENT_ARRAYLIST_CAPACITY);
		events.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(Consumer<ITask> hook) {
		return events != null && events.remove(hook);
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
			for (Consumer<ITask> func : events)
				func.accept(this);
	}
	
	//await
	@Override
	public synchronized void await() throws InterruptedException {
		while (result == null)
			wait();
	}
	
	@Override
	public synchronized void await(long time, TimeUnit unit) throws InterruptedException {
		while (result == null)
			wait(unit.toMillis(time));
	}
	
	//state and result getter
	@Override
	public boolean executionStarted() {
		return executionStarted;
	}
	
	@Override
	public boolean isDone() {
		return result != null;
	}
	
	@Override
	public TaskResult getResult() {
		return result;
	}
}
