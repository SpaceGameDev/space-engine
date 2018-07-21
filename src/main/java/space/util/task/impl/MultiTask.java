package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.task.Task;
import space.util.task.TaskResult;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static space.util.task.TaskResult.*;

/**
 * A {@link Task} which can have multiple sub-{@link Task ITasks}, doing Cancellation and Result calculations.
 * If a custom way of submitting task is required, override {@link #submit(Executor)}.
 */
public class MultiTask extends AbstractTask {
	
	//suppressing, as fields are initialized in init()
	@SuppressWarnings("NullableProblems")
	@NotNull
	public Collection<? extends Task> subTasks;
	@SuppressWarnings("NullableProblems")
	@NotNull
	protected AtomicInteger callCnt;
	
	/**
	 * this split into constructor and {@link #init(Collection)} is required by {@link space.util.event.dependency.DependencyEventBuilderMultithread}
	 */
	protected MultiTask() {
	}
	
	public MultiTask(@NotNull Collection<? extends Task> subTasks) {
		init(subTasks);
	}
	
	/**
	 * Method is required for ease of use in eg. {@link space.util.event.dependency.DependencyEventBuilderMultithread}
	 */
	protected void init(@NotNull Collection<? extends Task> subTasks) {
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
	
	@Override
	@NotNull
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("executionStarted", this.executionStarted);
		tsh.add("result", this.result);
		tsh.add("subTasks", this.subTasks);
		tsh.add("callCnt", this.callCnt);
		return tsh.build();
	}
}
