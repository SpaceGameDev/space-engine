package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.task.Task;
import space.util.task.TaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * An abstract Implementation of {@link Task}, providing basic functionality without Implementing how the execution, submitting, canceling (partially) and {@link Exception} handling works.
 * It should be noted that any implementation should NOT put the execution in a synchronized block, as it inhibits any interaction with the {@link AbstractTask},
 * and should instead synchronize the start and the end of the execution with result evaluation, NOT the execution itself.
 * If you want that already implemented, refer to {@link AbstractRunnableTask}.
 */
public abstract class AbstractTask implements Task, ToString {
	
	public static final int INITIAL_EVENT_ARRAYLIST_CAPACITY = 1;
	
	/**
	 * will be created lazily when {@link AbstractTask#addHook(Consumer)} is called the first time
	 * capacity of ArrayList: 1 - 3 - 9 - ...
	 */
	@Nullable
	public List<Consumer<Task>> events;
	
	//these are volatile to allow getter of these two values to be non-synchronized
	//state
	protected volatile boolean executionStarted;
	protected volatile boolean hooksRan;
	
	//result
	@Nullable
	protected volatile TaskResult result;
	
	//state changing methods
	
	/**
	 * starts the execution
	 *
	 * @return true if execution should be aborted, as it may already have been canceled
	 */
	protected synchronized boolean startExecution() {
		if (executionStarted)
			if (result == TaskResult.CANCELED)
				return true;
			else
				throw new IllegalStateException("Already executed! " + this);
		executionStarted = true;
		return false;
	}
	
	@Override
	public synchronized boolean cancel(boolean mayInterrupt) {
		if (result == TaskResult.CANCELED)
			return false;
		
		boolean allowSet = result == null;
		
		executionStarted = true;
		if (allowSet)
			result = TaskResult.CANCELED;
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
	public synchronized void addHook(@NotNull Consumer<Task> func) {
		if (hooksRan) {
			func.accept(this);
			return;
		}
		
		if (events == null)
			events = new ArrayList<>(INITIAL_EVENT_ARRAYLIST_CAPACITY);
		events.add(func);
	}
	
	@Override
	public synchronized boolean removeHook(@NotNull Consumer<Task> hook) {
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
			for (Consumer<Task> func : events)
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
	@SuppressWarnings({"NullableProblems", "ConstantConditions"})
	public TaskResult getResult() {
		return result;
	}
	
	//toString
	@Override
	@NotNull
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("executionStarted", this.executionStarted);
		tsh.add("result", this.result);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
