package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.barrier.Barrier;
import space.util.future.FutureNotFinishedException;
import space.util.task.CallableTask;
import space.util.task.TaskState;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
	
	//delegate
	@Override
	public @NotNull CallableTask<R> submit() {
		super.submit();
		return this;
	}
	
	@Override
	public synchronized @NotNull CallableTask<R> submit(Barrier... barriers) {
		super.submit(barriers);
		return this;
	}
	
	//execute
	@Override
	protected void execute() {
		ret = execute0();
	}
	
	protected abstract R execute0();
	
	//get
	@Override
	public synchronized R awaitGet() throws InterruptedException {
		await();
		return ret;
	}
	
	@Override
	public R awaitGet(long time, TimeUnit unit) throws InterruptedException, TimeoutException {
		await(time, unit);
		return ret;
	}
	
	@Nullable
	@Override
	public synchronized R assertGet() throws FutureNotFinishedException {
		if (getState() != TaskState.FINISHED)
			throw new FutureNotFinishedException(this);
		return ret;
	}
}
