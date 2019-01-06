package space.util.task.impl;

import org.jetbrains.annotations.Nullable;
import space.util.sync.future.Future;
import space.util.sync.future.FutureNotFinishedException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class FutureTask<R> extends RunnableTask implements Future<R> {
	
	protected R ret;
	
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
		if (state != TaskState.FINISHED)
			throw new FutureNotFinishedException(this);
		return ret;
	}
}
