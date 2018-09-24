package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.barrier.Barrier;
import space.util.future.FutureNotFinishedException;
import space.util.task.CallableTask;
import space.util.task.TaskState;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

public abstract class CallableTaskImpl<R> extends RunnableTaskImpl implements CallableTask<R> {
	
	public static <R> CallableTaskImpl<R> create(Executor exec, Supplier<R> function) {
		return new CallableTaskImpl<>() {
			@Override
			protected R execute0() {
				return function.get();
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				exec.execute(toRun);
			}
		};
	}
	
	protected R ret;
	
	@Override
	public @NotNull CallableTask<R> submit() {
		super.submit();
		return this;
	}
	
	@Override
	public synchronized @NotNull CallableTask<R> submit(Barrier... barriers) {
		super.submit();
		return this;
	}
	
	@Override
	protected void execute() {
		ret = execute0();
	}
	
	protected abstract R execute0();
	
	@Override
	public synchronized R get() throws InterruptedException {
		await();
		return ret;
	}
	
	@Nullable
	@Override
	public synchronized R tryGet() throws FutureNotFinishedException {
		if (getState() != TaskState.FINISHED)
			throw new FutureNotFinishedException();
		return ret;
	}
}
