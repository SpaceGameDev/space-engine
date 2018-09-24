package space.util.task;

import org.jetbrains.annotations.NotNull;
import space.util.barrier.Barrier;
import space.util.future.Future;

public interface CallableTask<R> extends Task, Future<R> {
	
	@Override
	@NotNull CallableTask<R> submit();
	
	@Override
	@NotNull CallableTask<R> submit(Barrier... barriers);
}
