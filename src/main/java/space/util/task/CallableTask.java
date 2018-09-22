package space.util.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.barrier.Barrier;

public interface CallableTask<R> extends Task {
	
	@Override
	@NotNull CallableTask<R> submit();
	
	@Override
	@NotNull CallableTask<R> submit(Barrier... barriers);
	
	R get() throws InterruptedException;
	
	@Nullable R tryGet();
}
