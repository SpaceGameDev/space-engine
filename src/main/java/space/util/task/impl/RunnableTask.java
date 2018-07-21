package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;
import space.util.task.Task;
import space.util.task.TaskExceptionHandler;

/**
 * A full implementation of {@link Task} accepting a {@link Runnable}
 */
public class RunnableTask extends AbstractRunnableTask {
	
	@NotNull
	public final Runnable run;
	
	public RunnableTask(@NotNull Runnable run) {
		this(run, null);
	}
	
	public RunnableTask(@NotNull Runnable run, @Nullable TaskExceptionHandler exceptionHandler) {
		super(exceptionHandler);
		this.run = run;
	}
	
	@Override
	protected void run0() {
		run.run();
	}
	
	//toString
	@Override
	@NotNull
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("executionStarted", this.executionStarted);
		tsh.add("result", this.result);
		tsh.add("exceptionHandler", this.exceptionHandler);
		tsh.add("run", this.run);
		return tsh.build();
	}
}
