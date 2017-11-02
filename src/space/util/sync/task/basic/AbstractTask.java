package space.util.sync.task.basic;

import space.util.sync.task.ITaskExecutionCompleteRunnable;
import space.util.sync.task.TaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static space.util.sync.task.TaskResult.CANCELED;

public abstract class AbstractTask implements ITask {
	
	public List<Object> events;
	
	//state
	protected volatile boolean executionStarted;
	protected boolean hooksRan;
	
	//result
	protected volatile TaskResult result;
	
	//state
	protected synchronized boolean startExecution() {
		if (executionStarted)
			if (result == CANCELED)
				return false;
			else
				throw new IllegalStateException("already executed! " + this);
		executionStarted = true;
		return true;
	}
	
	//cancel
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
	
	protected abstract void cancel0(boolean mayInterrupt);
	
	//event
	@Override
	public synchronized void addHook(Runnable func) {
		addHook0(func);
	}
	
	@Override
	public void addHook(Consumer<?> func) {
		addHook0(func);
	}
	
	public synchronized void addHook0(Object func) {
		if (hooksRan) {
			runHook(func);
			return;
		}
		
		if (events == null)
			events = new ArrayList<>();
		events.add(func);
	}
	
	protected synchronized void runHooks() {
		if (hooksRan)
			return;
		hooksRan = true;
		
		notifyAll();
		if (events != null)
			for (Object run : events)
				runHook(run);
	}
	
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
